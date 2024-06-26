package org.example.View;

public class Message {

    public static void printWelcomeMessage() {
        System.out.printf("""
                                
                Welcome to the Hangman!
                        
                Выбери категорию (Введи число или название категории):
                %s
                """, Draw.getLineOfCategoriesDynamically());

    }

    public static void printChosenCategory(String category, int attempt) {
        if (attempt == 8) {
            System.out.printf("Ты выбрал(а) категорию %s.\n%n", category);
            printAttempt(attempt);
        } else printAttempt(attempt);
    }

    public static void printAttempt(int attempt) {
        if (attempt == 8) {
            System.out.printf("У тебя будет %d попыток!\n%n", attempt);
        } else System.out.printf("У тебя осталось %d попыток!\n%n", attempt);
    }

    public static void printAsterisk(String line) {

        System.out.println("Твое слово: " + line);
    }

    public static void printEnterLetter(){
        System.out.println("Введи букву: ");
    }


    public static void printLostMessage() {
        System.out.println("Ты проиграл! Попробуешь еще раз?");
    }

    public static void printGetAnotherTry() {
        System.out.println("""
                Попробуешь еще раз?
                (y/n):
                
                """);
    }
}
