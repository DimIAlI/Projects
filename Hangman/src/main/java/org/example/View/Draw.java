package org.example.View;


import org.example.DTO.ImageDTO;
import org.example.Model.Model;


public class Draw {

    private static final String[] IMAGES;

    static {
        ImageDTO imageInstance = ImageDTO.getInstance("src/main/resources/Hangman.JSON");
        IMAGES = imageInstance.getImages();
    }

    public static void doDrawHangman(int attempt) {
        System.out.println(IMAGES[attempt]);

    }

}
