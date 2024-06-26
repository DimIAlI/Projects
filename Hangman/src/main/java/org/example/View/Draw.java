package org.example.View;


import org.example.DTO.ImageDTO;
import org.example.Engine.Model;


public class Draw {

    private static final String[] IMAGES;


    static {
        ImageDTO imageInstance = ImageDTO.getInstance("src/main/resources/Hangman.JSON");
        IMAGES = imageInstance.getImages();
    }


    public static void doDrawHangman(int attempt) {
        System.out.println(IMAGES[attempt]);

    }


    static StringBuilder getLineOfCategoriesDynamically() {

        String[] categories = Model.getCollectionOfWords();

        StringBuilder categoriesForFormat = new StringBuilder();
        int index = 1;
        for (String category : categories) {
            categoriesForFormat.append(index).append(". ").append(category).append("\n");
            index++;
        }
        return categoriesForFormat;
    }


}
