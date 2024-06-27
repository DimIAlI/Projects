package org.example.DTO;


import org.example.utils.JsonMapper;



public class ImageDTO {

    private static ImageDTO INSTANCE;

    private String[] images;

    private ImageDTO() {
    }

    public static ImageDTO getInstance(String path) {
        if (INSTANCE == null) {
            INSTANCE = JsonMapper.getFromJSON(ImageDTO.class, path);
        }
        return INSTANCE;
    }

    public String[] getImages() {
        return images;
    }



}
