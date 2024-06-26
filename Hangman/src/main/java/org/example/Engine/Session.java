package org.example.Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Session {

    private static final Scanner scanner = new Scanner(System.in);
    private String category;
    private String word;
    private int attempt;
    private String asterisk;
    private ArrayList<Character> arrayOfChars;
    private final HashSet<Character> alreadyEnteredChars;

    public static String getMessageFromUser() {
        return scanner.nextLine();
    }

    Session() {
        attempt = 8;
        alreadyEnteredChars = new HashSet<>();
    }

    public HashSet<Character> getAlreadyEnteredChars() {
        return alreadyEnteredChars;
    }

    public String getAsterisk() {
        return asterisk;
    }

    public String getCategory() {
        return category;
    }

    public String getWord() {
        return word;
    }

    public int getAttempt() {
        return attempt;
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

    public ArrayList<Character> getArrayOfChars() {
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
}
