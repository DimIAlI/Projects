package org.example.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Session {

    private static final Scanner SCANNER = new Scanner(System.in);
    private String category;
    private String word;
    private int attempt;
    private String asterisk;
    private ArrayList<Character> arrayOfChars;
    private final HashSet<Character> alreadyEnteredChars;

    Session() {
        attempt = 8;
        alreadyEnteredChars = new HashSet<>();
    }

    static String getMessageFromUser() {
        return SCANNER.nextLine();
    }

    HashSet<Character> getAlreadyEnteredChars() {
        return alreadyEnteredChars;
    }

    String getAsterisk() {
        return asterisk;
    }

    String getCategory() {
        return category;
    }

    String getWord() {
        return word;
    }

    int getAttempt() {
        return attempt;
    }

    ArrayList<Character> getArrayOfChars() {
        if (arrayOfChars == null) {
            String hiddenWord = getWord();
            arrayOfChars = new ArrayList<>();

            char[] charArray = hiddenWord.toCharArray();

            for (char c : charArray) {
                arrayOfChars.add(c);
            }
        }
        return arrayOfChars;
    }

    void setCategory(String category) {
        this.category = category;
    }

    void setAsterisk(String asterisk) {
        this.asterisk = asterisk;
    }

    void setWord(String word) {
        this.word = word;
    }

    void setAttempt(int attempt) {
        this.attempt = attempt;
    }

}
