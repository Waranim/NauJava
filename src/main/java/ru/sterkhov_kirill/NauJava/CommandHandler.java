package ru.sterkhov_kirill.NauJava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.sterkhov_kirill.NauJava.processor.BookProcessor;

import java.util.Scanner;

@Component
public class CommandHandler implements CommandLineRunner {

    private final BookProcessor bookProcessor;

    public CommandHandler(BookProcessor bookProcessor) {
        this.bookProcessor = bookProcessor;
    }


    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in))
        {
            System.out.println("Введите команду. 'exit' для выхода.");
            while (true)
            {
                System.out.print("> ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input.trim()))
                {
                    System.out.println("Выход из программы...");
                    break;
                }

                bookProcessor.process(input);
            }
        }
    }
}
