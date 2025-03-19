package ru.sterkhov_kirill.NauJava.processor;

import org.springframework.stereotype.Component;
import ru.sterkhov_kirill.NauJava.entity.Book;
import ru.sterkhov_kirill.NauJava.service.BookService;

@Component
public class BookProcessor {

    private final BookService bookService;

    public BookProcessor(BookService bookService) {
        this.bookService = bookService;
    }

    public void process(String input) {
        String[] words = input.split(" ");

        try {
            switch (words[0].toLowerCase()) {
                case "create":
                    bookService.createBook(Long.parseLong(words[1]), words[2], words[3]);
                    System.out.println("Книга успешно создана");
                    break;
                case "get":
                    Book book = bookService.findBookById(Long.parseLong(words[1]));
                    if (book == null) {
                        System.out.println("Некорректный ввод");
                        break;
                    }
                    System.out.println(String.format("Найдена книга \"%s\", автор %s", book.getTitle(), book.getAuthor()));
                    break;
                case "update":
                    if (words[1].equalsIgnoreCase("title")) {
                        bookService.updateTitle(Long.parseLong(words[2]), words[3]);
                        System.out.println("Название обновлено");
                    } else if (words[1].equalsIgnoreCase("author")) {
                        bookService.updateAuthor(Long.parseLong(words[2]), words[3]);
                        System.out.println("Автор обновлён");
                    } else {
                        System.out.println("Некорректный ввод");
                    }
                    break;
                case "delete":
                    bookService.deleteBookById(Long.parseLong(words[1]));
                    System.out.println("Книга с идентификатором %d удалена".formatted(Long.parseLong(words[1])));
                    break;
                default:
                    System.out.println("Некорректный ввод");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Некорректный ввод");
        }
    }
}
