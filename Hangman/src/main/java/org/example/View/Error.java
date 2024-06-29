package org.example.View;

public class Error {

    public static void printSelectionError() {
        System.err.println("Ты ввел несуществующую категорию, попробуй снова!\n");
    }

    public static void printLetterAlreadyEnteredError(int attempt) {
        System.err.printf("Эта буква уже вводилась! У тебя все еще %d попыток!\n%n", attempt);
    }

    public static void printLetterExistingError(int attempt) {
        System.err.print("Такой буквы здесь нет! ");

        if (attempt == 0) {
            System.err.println("У тебя не осталось попыток!");
        } else {
            System.err.printf("У тебя осталось %d попыток!\n%n", attempt);
        }
        Draw.doDrawHangman(attempt);
    }

    public static void printLetterQuantityError() {
        System.err.println("Слишком много символов, попробуй остановиться на каком-то одном!");
    }

    public static void printNotALetterError() {
        System.err.println("Попробуй вводить буквы :)");
    }

    public static void printNotValidLetterError() {
        System.err.println("Кажется ты ошибся, нужно ввести \"y\" или \"n\" :)");
    }

    public static void printStringIsEmptyError() {
        System.err.println("Похоже, что ты ничего не ввел, попроуй снова!");
    }

    public static void printNotACyrillicError() {
        System.err.println("Похоже, что ты пытаешься использовать не тот алфавит :)");
    }
}

