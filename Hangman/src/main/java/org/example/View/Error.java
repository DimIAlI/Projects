package org.example.View;

public class Error {

    public static void printSelectionError() {
        System.err.println("Ты ввел несуществующую категорию, попробуй снова!\n");
    }

    public static void printLetterAlreadyEnteredError(int attempt) {

        System.err.printf("Эта буква уже вводилась! У тебя все еще %d попыток!\n%n", attempt);
    }

    public static void printLetterExistingError(int attempt) {


            System.err.printf("Такой буквы здесь нет! У тебя осталось %d попыток!\n%n", attempt);
            Draw.doDrawHangman(attempt);
        }

        public static void printLetterQuantityError(){

            System.err.println("Слишком много букв, попробуй остановиться на какой-то одной!");
        }
        public static void printNotALetterError() {
            System.err.println("Попробуй вводить буквы :)");
        }
}

