package org.example.DTO;


import org.example.utils.JsonMapper;
import java.util.ArrayList;
import java.util.Map;

public class WordsDTO {

    private static WordsDTO INSTANCE;

    private Map<String, ArrayList<String>> categories;

    private WordsDTO() {
    }

    public static WordsDTO getInstance(String path) {
        if (INSTANCE == null) {
            INSTANCE = JsonMapper.getFromJSON(WordsDTO.class, path);
        }
        return INSTANCE;
    }

    public Map<String, ArrayList<String>> getCategories() {
        return categories;
    }
}
