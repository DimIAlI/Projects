package org.example.Engine;

import org.example.DTO.WordsDTO;
import org.example.View.Error;
import org.example.View.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;


public class Model {

    private static final Map<String, ArrayList<String>> COLLECTION_OF_WORDS;
    private static final String[] CATEGORIES_OF_WORDS;
    private static Session session = new Session();


    static {
        WordsDTO WordsInstance = WordsDTO.getInstance("src/main/resources/Words.JSON");
        COLLECTION_OF_WORDS = WordsInstance.getCategories();
        CATEGORIES_OF_WORDS = COLLECTION_OF_WORDS.keySet().toArray(new String[0]);
    }

    public static void run() {
        String messageFromUser;

        do {
            Message.printWelcomeMessage();
            messageFromUser = Session.getMessageFromUser();

            if (!Model.doCheckValidityCategory(messageFromUser)) {
                Error.printSelectionError();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!Model.doCheckValidityCategory(messageFromUser));

        Message.printChosenCategory(session.getCategory(), session.getAttempt());
        chooseRandomWord(session.getCategory());

        initAsterisk(session.getWord());


        while (session.getAttempt() != 0) {


            Message.printAsterisk(session.getAsterisk());
            Message.printEnterLetter();

            //первый цикл ввода и валидации значения
            do {
                messageFromUser = Session.getMessageFromUser();

                if (doCheckValidityLetter(messageFromUser)) {

                    if (doCheckEnteredLetter(messageFromUser)) {

                        Error.printLetterAlreadyEnteredError(session.getAttempt());

                    } else {

                        int[] matches = doCheckLetterMatch(messageFromUser, session.getWord());
                        makeAsterisk(messageFromUser, matches);
                    }
                }
            } while (!doCheckEnteredLetter(messageFromUser));

            if (session.getAttempt() == 0) {

                endOfTheGame();

            }
        }
    }

    private static void endOfTheGame() {

        Message.printLostMessage();

        Message.printGetAnotherTry();

        while (true) {
            if (doCheckRestart(Session.getMessageFromUser())) {
                run();
            }
            break;
        }
    }

    private static boolean doCheckRestart(String messageFromUser) {

  return true;  }


    private static int[] doCheckLetterMatch(String message, String word) {
        int counter = 0;
        int capacity = 0;


        char letter = message.charAt(0);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                capacity++;
            }
        }

        int[] matches = new int[capacity];

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                matches[counter] = i;
                counter++;
            }
        }

        return matches;
    }


    private static void makeAsterisk(String letter, int... position) {

        makeLetterTyped(letter);

        if (position.length == 0) {
            session.setAttempt(session.getAttempt() - 1);
            Error.printLetterExistingError(session.getAttempt());

        } else {
            String currentAsterisk = session.getAsterisk();
            StringBuilder asteriskBuilder = new StringBuilder(currentAsterisk);
            for (int i = 0; i < position.length; i++) {
                asteriskBuilder.setCharAt(position[i], letter.charAt(0));
            }
            session.setAsterisk(asteriskBuilder.toString());
        }
    }


    private static void makeLetterTyped(String letter) {

        session.getAlreadyEnteredChars().add(letter.charAt(0));

    }

    private static boolean doCheckValidityLetter(String messageFromUser) {
        if (messageFromUser.length() != 1) {
            Error.printLetterQuantityError();
            return false;
        }
        char letter = messageFromUser.charAt(0);
        if (!Character.isLetter(letter)) {
            Error.printNotALetterError();
            return false;
        }
        return true;
    }

    public static String[] getCollectionOfWords() {
        return CATEGORIES_OF_WORDS;
    }

    public static boolean doCheckValidityCategory(String message) {

        try {
            int number = Integer.parseInt(message);
            if (number < 1 || number > CATEGORIES_OF_WORDS.length) {
                return false;
            }
            session.setCategory(CATEGORIES_OF_WORDS[number - 1]);
            return true;
        } catch (NumberFormatException e) {
            message = makeValidCase(message);
            if (!COLLECTION_OF_WORDS.containsKey(message)) {
                return false;
            }
            session.setCategory(message);
            return true;
        }
    }

    private static String makeValidCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static Session getSession() {
        return session;
    }

    public static void chooseRandomWord(String category) {
        ArrayList<String> words = COLLECTION_OF_WORDS.get(category);

        String word = words.get(new Random().nextInt(words.size()));
        session.setWord(word);
    }


    private static boolean doCheckEnteredLetter(String letter) {
        HashSet<Character> alreadyEnteredChars = session.getAlreadyEnteredChars();
        return alreadyEnteredChars.contains(letter.charAt(0));
    }

    private static String initAsterisk(String word) {
        int length = word.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("*");
        }
        session.setAsterisk(builder.toString());
        return builder.toString();

    }

    private static boolean doCheckLetter(String letter) {
        return session.getArrayOfChars().contains(letter);
    }


// исправить отрисовку челика (изменить кол-во ошибок)
    //Исправить ошибку с UpperCase
}



