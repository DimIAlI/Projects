package org.example.Model;

import org.example.DTO.WordsDTO;
import org.example.View.Error;
import org.example.View.Message;

import java.util.*;


public class Model {

    private static final Map<String, ArrayList<String>> COLLECTION_OF_WORDS;
    private static final String[] CATEGORIES_OF_WORDS;
    private static final Model INSTANCE = new Model();
    private static final Scanner SCANNER = new Scanner(System.in);
    private Session session;

    static {
        WordsDTO WordsInstance = WordsDTO.getInstance("src/main/resources/Words.JSON");
        COLLECTION_OF_WORDS = WordsInstance.getCategories();
        CATEGORIES_OF_WORDS = COLLECTION_OF_WORDS.keySet().toArray(new String[0]);
    }

    private Model() {
        this.session = new Session();
    }

    public static String[] getCategoriesOfWords() {
        return CATEGORIES_OF_WORDS;
    }

    public static StringBuilder getCategoriesOfWords(String[] categories) {

        StringBuilder categoriesForFormat = new StringBuilder();
        int index = 1;
        for (String category : categories) {
            categoriesForFormat.append(index).append(". ").append(category).append("\n");
            index++;
        }
        return categoriesForFormat;
    }

    private static String getMessageFromUser() {
        String line;

        do {
            line = SCANNER.nextLine();
            if (line.isEmpty()) {
                Error.printStringIsEmptyError();
            }
        } while (line.isEmpty());
        return line;
    }

    public static void run() {

        boolean isRunning = true;

        Message.printWelcomeMessage();

        while (isRunning) {

            INSTANCE.chooseCategory();
            INSTANCE.startGame();
            isRunning = INSTANCE.endOfTheGame();

        }
    }

    private void chooseCategory() {
        String messageFromUser;
        Message.printCategories();

        while (true) {

            messageFromUser = getMessageFromUser();

            if (doCheckValidityOfCategory(messageFromUser)) {
                break;
            }
            Error.printSelectionError();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message.printChosenCategory(session.category, session.attempt);
        generateAsterisk();
    }

    private boolean doCheckValidityOfCategory(String message) {
        try {
            int number = Integer.parseInt(message);
            if (number >= 1 && number <= CATEGORIES_OF_WORDS.length) {
                session.category = CATEGORIES_OF_WORDS[number - 1];
                return true;
            }
        } catch (NumberFormatException e) {
            String validMessage = makeValidCase(message);
            if (COLLECTION_OF_WORDS.containsKey(validMessage)) {
                session.category = validMessage;
                return true;
            }
        }
        return false;
    }

    private String makeValidCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    private void generateAsterisk() {

        chooseRandomWord(session.category);
        initAsterisk(session.word);
    }

    public void chooseRandomWord(String category) {
        ArrayList<String> words = COLLECTION_OF_WORDS.get(category);

        session.word = words.get(new Random().nextInt(words.size()));

    }

    private void initAsterisk(String word) {
        session.asterisk = "*".repeat(word.length());
    }

    private void startGame() {
        String messageFromUser;
        while (true) {

            Message.printAsterisk(session.asterisk);
            Message.printEnterLetter();

            do {
                messageFromUser = getMessageFromUser();

                if (doCheckValidityLetter(messageFromUser)) {

                    if (doCheckEnteredLetter(messageFromUser)) {

                        Error.printLetterAlreadyEnteredError(session.attempt);

                    } else {

                        int[] matches = doCheckLetterMatch(messageFromUser, session.word);
                        makeAsterisk(messageFromUser, matches);

                        if (doCheckWinner(session.asterisk, session.word)) {
                            Message.printWinner(session.asterisk);
                            return;

                        }

                    }
                }
            } while (!doCheckEnteredLetter(messageFromUser));

            if (session.attempt == 0) {
                Message.printLostMessage(session.word);
                return;

            }
        }
    }

    private boolean doCheckWinner(String asterisk, String word) {
        return asterisk.equals(word);
    }

    private boolean endOfTheGame() {

        Message.printGetAnotherTry();

        while (true) {
            if (doCheckRestart(getMessageFromUser())) {
                session = new Session();
                return true;
            }
            return false;
        }
    }

    private boolean doCheckRestart(String messageFromUser) {

        while (!doCheckValidityLetter(messageFromUser)) {

            switch (messageFromUser.toUpperCase()) {

                case "Y":
                    return true;
                case "N":
                    return false;
                default: {
                    while (messageFromUser.equalsIgnoreCase("y") || messageFromUser.equalsIgnoreCase("n")) {
                        Error.printNotValidLetterError();
                        messageFromUser = getMessageFromUser();
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private int[] doCheckLetterMatch(String message, String word) {
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

    private void makeAsterisk(String letter, int... position) {

        makeLetterTyped(letter);

        if (position.length == 0) {
            session.attempt = session.attempt - 1;
            Error.printLetterExistingError(session.attempt);

        } else {
            String currentAsterisk = session.asterisk;
            StringBuilder asteriskBuilder = new StringBuilder(currentAsterisk);
            for (int i = 0; i < position.length; i++) {
                asteriskBuilder.setCharAt(position[i], letter.charAt(0));
            }
            session.asterisk = asteriskBuilder.toString();
        }
    }

    private void makeLetterTyped(String letter) {

        session.alreadyEnteredChars.add(letter.charAt(0));

    }

    private boolean doCheckValidityLetter(String messageFromUser) {
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


    private boolean doCheckEnteredLetter(String letter) {
        HashSet<Character> alreadyEnteredChars = session.alreadyEnteredChars;
        return alreadyEnteredChars.contains(letter.charAt(0));
    }


    private class Session {

        private String category;
        private String word;
        private int attempt;
        private String asterisk;
        private ArrayList<Character> arrayOfChars;
        private final HashSet<Character> alreadyEnteredChars;


        private Session() {
            attempt = 8;
            alreadyEnteredChars = new HashSet<>();

        }
    }

    //Исправить ошибку с UpperCase
//е=ё
    //разобраться с upper/lower
    //поправить конец игры
    //добавить категорию случайно
}



