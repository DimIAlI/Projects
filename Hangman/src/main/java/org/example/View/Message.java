package org.example.View;

import org.example.Model.Model;

public class Message {

    public static void printWelcomeMessage() {
        System.out.println("\nWelcome to the Hangman!\n");

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

    public static void printEnterLetter() {
        System.out.println("Введи букву: ");
    }

    public static void printLostMessage(String word) {
        System.out.printf("""
                Ты проиграл(а)!
                Загаданным словом было "%s"
                %n""", word);
    }

    public static void printGetAnotherTry() {
        System.out.println("""
                Попробуешь еще раз?
                (y/n):
                """);
    }

    public static void printWinner(String word) {
        System.out.printf("""       
                Ты угадал(а) слово: "%s"
                %n""", word);
    }

    public static void printCategories() {
        System.out.printf("""
                        Выбери категорию (Введи число или название категории):
                        %s
                        """, Model.getLineOfCategoriesDynamically());
    }
}
