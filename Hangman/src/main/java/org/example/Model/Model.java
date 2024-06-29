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
        return SCANNER.nextLine();
    }

    public static void run() {

        boolean isRunning = true;

        Message.printWelcomeMessage();

        while (isRunning) {

            INSTANCE.chooseCategory();
            INSTANCE.startGame();
            isRunning = INSTANCE.isGameOver();

        }
        Message.printByeMessage();
    }

    private void chooseCategory() {
        String messageFromUser;
        Message.printCategories();

        boolean isValid;

        do {
            messageFromUser = getMessageFromUser();
            isValid = isCategoryValid(messageFromUser);
        }
        while (!isValid);

        chooseRandomWord(session.category);
        initAsterisk(session.word);

        Message.printChosenCategory(session.category, session.attempt);
    }

    private boolean isCategoryValid(String message) {
        if (isEmpty(message))
            return false;

        try {
            int number = Integer.parseInt(message);
            if (number < 1 || number > CATEGORIES_OF_WORDS.length) {
                Error.printSelectionError();
                return false;
            }
            session.category = CATEGORIES_OF_WORDS[number - 1];
            return true;
        } catch (NumberFormatException e) {
            message = makeValidCase(message);
            if (!COLLECTION_OF_WORDS.containsKey(message)) {
                Error.printSelectionError();
                return false;
            }
            session.category = message;
            return true;
        }
    }

    private boolean isEmpty(String message) {
        if (message.isEmpty()) {
            Error.printStringIsEmptyError();
            return true;
        }
        return false;
    }

    private String makeValidCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public void chooseRandomWord(String category) {

        if (category.equals("Случайная")) {
            int randomCategory = new Random().nextInt(1, CATEGORIES_OF_WORDS.length);
            category = CATEGORIES_OF_WORDS[randomCategory];
            session.category = category;
        }

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
            } while (isLetterInvalid(messageFromUser) || isLetterAlreadyEntered(messageFromUser) || !isCyrillicLetter(messageFromUser));


            int[] matches = doCheckLetterMatches(messageFromUser, session.word);

            makeAsterisk(messageFromUser, matches);

            if (hasWinner(session.asterisk, session.word)) {
                Message.printWinner(session.asterisk);
                break;
            }
            if (session.attempt == 0) {
                Message.printLostMessage(session.word);
                break;
            }
        }
    }

    private boolean isLetterInvalid(String messageFromUser) {

        if (isEmpty(messageFromUser)) {
            return true;
        }
        if (messageFromUser.length() != 1) {
            Error.printLetterQuantityError();
            return true;
        }
        char letter = messageFromUser.charAt(0);
        if (!Character.isLetter(letter)) {
            Error.printNotALetterError();
            return true;
        }
        return false;
    }

    private boolean isLetterAlreadyEntered(String letter) {

        if (session.alreadyEnteredChars.contains(letter.charAt(0))) {
            Error.printLetterAlreadyEnteredError(session.attempt);
            return true;
        }
        return false;
    }

    private boolean isCyrillicLetter(String message) {
        if (Character.UnicodeBlock.of(message.charAt(0)) != Character.UnicodeBlock.CYRILLIC) {
            Error.printNotACyrillicError();
            return false;
        }
        return true;
    }

    private int[] doCheckLetterMatches(String message, String word) {

        int capacity = 0;
        char letter = Character.toLowerCase(message.charAt(0));

        for (int i = 0; i < word.length(); i++) {
            if (letter == Character.toLowerCase(word.charAt(i))) {
                capacity++;
            }
        }
        int[] matches = new int[capacity];

        int counter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (letter == Character.toLowerCase(word.charAt(i))) {
                matches[counter] = i;
                counter++;
            }
        }
        makeLetterTyped(message);
        return matches;
    }

    private void makeLetterTyped(String letter) {
        session.alreadyEnteredChars.add(letter.toLowerCase().charAt(0));
        session.alreadyEnteredChars.add(letter.toUpperCase().charAt(0));
    }

    private void makeAsterisk(String letter, int[] positions) {

        if (positions.length == 0) {
            session.attempt -= 1;
            Error.printLetterExistingError(session.attempt);
        } else {
            String currentAsterisk = session.asterisk;
            StringBuilder asteriskBuilder = new StringBuilder(currentAsterisk);
            char charToReplace = letter.charAt(0);

            for (int position : positions) {
                asteriskBuilder.setCharAt(position, position == 0 ? Character.toUpperCase(charToReplace) : Character.toLowerCase(charToReplace));
            }
            session.asterisk = asteriskBuilder.toString();
        }
    }

    private boolean hasWinner(String asterisk, String word) {
        return asterisk.equals(word);
    }

    private boolean isGameOver() {

        Message.printGetAnotherTry();

        if (hasRestarted(getMessageFromUser())) {
            session = new Session();
            return true;
        }
        return false;

    }

    private boolean hasRestarted(String messageFromUser) {

        while (true) {

            while (isLetterInvalid(messageFromUser)) {
                messageFromUser = getMessageFromUser();
            }
            switch (messageFromUser.toLowerCase()) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    Error.printNotValidLetterError();
                    messageFromUser = getMessageFromUser();
            }
        }
    }

    private static class Session {

        private String category;
        private String word;
        private int attempt;
        private String asterisk;
        private final HashSet<Character> alreadyEnteredChars;


        private Session() {
            attempt = 8;
            alreadyEnteredChars = new HashSet<>();

        }
    }
}



