package org.example.DTO;


import org.example.utils.JsonMapper;



//содержит данные из JSON - отображение человека
public class ImageDTO {

    private String[] images;

    public String[] getImages() {
        return images;
    }

    private static ImageDTO INSTANCE;

    private ImageDTO() {
    }

    public static ImageDTO getInstance(String path) {
        if (INSTANCE == null) {
            INSTANCE = JsonMapper.getFromJSON(ImageDTO.class, path);
        }
        return INSTANCE;
    }



}
