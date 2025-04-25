package ru.sterkhov_kirill.NauJava.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;
import ru.sterkhov_kirill.NauJava.entity.ReportEntity;
import ru.sterkhov_kirill.NauJava.enums.ReportStatus;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;
import ru.sterkhov_kirill.NauJava.repository.ReportRepository;
import ru.sterkhov_kirill.NauJava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public String getContent(Long id) {
        ReportEntity report = getReport(id);
        if (report.getStatus() != ReportStatus.COMPLETED) {
            return report.getStatus() == ReportStatus.CREATED ?
                    "Отчёт на этапе создания" : "При создании отчёта произошла ошибка";
        }

        return getReport(id).getContent();
    }

    public Long createReport() {
        ReportEntity report = new ReportEntity();

        return reportRepository.save(report).getId();
    }

    public void generateReport(Long id) {
        ReportEntity report = getReport(id);

        CompletableFuture<String> content = CompletableFuture.supplyAsync(() -> {
            long totalStartTime = System.currentTimeMillis();
            AtomicLong count = new AtomicLong();
            AtomicReference<List<BookEntity>> books = new AtomicReference<>();
            AtomicLong countUsersElapsed =  new AtomicLong();
            AtomicLong listBooksElapsed =  new AtomicLong();

            Thread countUsersThread = new Thread(() -> {
                long countUsersStartTime = System.currentTimeMillis();
                count.set(userRepository.count());
                countUsersElapsed.set(System.currentTimeMillis() - countUsersStartTime);
            });

            Thread listBooksThread = new Thread(() -> {
                long listBooksStartTime = System.currentTimeMillis();
                books.set(new ArrayList<>());
                bookRepository.findAll().forEach(book -> books.get().add(book));
                listBooksElapsed.set(System.currentTimeMillis() - listBooksStartTime);
            });

            countUsersThread.start();
            listBooksThread.start();

            try {
                countUsersThread.join();
                listBooksThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return buildContent(
                    count,
                    books,
                    countUsersElapsed,
                    listBooksElapsed,
                    System.currentTimeMillis() - totalStartTime
            );
        });

        content.thenAccept(result -> {
            report.setContent(result);
            report.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);
        });

        content.exceptionally(ex -> {
            report.setStatus(ReportStatus.FAILED);
            reportRepository.save(report);

            return "Произошла ошибка при формированнии отчёта: " + ex.getMessage();
        });
    }

    private ReportEntity getReport(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отчёт с идентификатором %d не найден".formatted(id)));
    }

    private String buildContent(AtomicLong countUsers,
                                AtomicReference<List<BookEntity>> books,
                                AtomicLong countUsersElapsed,
                                AtomicLong listBooksElapsed,
                                long totalElapsed) {
        return """
            <html lang="ru">
                <head><title>Отчёт</title></head>
                <body>
                    <h1>Статистика приложения</h1>
                    <p>Количество пользователей: %d (время получения: %d мс)</p>
                    <h2>Список книг</h2>
                    <table border="1">
                        <tr><th>ID</th><th>Название</th><th>Автор</th><th>ISBN</th><th>Доступно</th><th>Всего</th></tr>
                        %s
                    </table>
                    <p>Время получения списка книг: %d мс</p>
                    <p>Общее время формирования отчёта: %d мс</p>
                </body>
            </html>
            """.formatted(
                    countUsers.get(),
                    countUsersElapsed.get(),
                    books.get()
                            .stream()
                            .map(book ->
                                    "<tr><th>%d</th><th>%s</th><th>%s</th><th>%s</th><th>%d</th><th>%d</th></tr>".formatted(
                                        book.getId() != null ? book.getId() : -1L,
                                        book.getTitle() != null ? book.getTitle() : "Не указано",
                                        book.getAuthor() != null ? book.getAuthor() : "Не указан",
                                        book.getIsbn() != null ? book.getIsbn() : "Не указан",
                                        book.getAvailableCopies(),
                                        book.getTotalCopies()
                                    )
                            )
                            .collect(Collectors.joining()),
                    listBooksElapsed.get(),
                    totalElapsed
        );
    }
}
