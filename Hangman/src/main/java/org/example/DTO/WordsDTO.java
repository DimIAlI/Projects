package org.example.DTO;


import org.example.utils.JsonMapper;

import java.util.ArrayList;
import java.util.Map;

//Класс содержит данные, полученные из JSON - слова
public class WordsDTO {

    private Map<String, ArrayList<String>> categories;

    public Map<String, ArrayList<String>> getCategories() {
        return categories;
    }

    private static WordsDTO INSTANCE;

    private WordsDTO() {
    }


    public static WordsDTO getInstance(String path) {
        if (INSTANCE == null) {
            INSTANCE = JsonMapper.getFromJSON(WordsDTO.class, path);
        }
        return INSTANCE;

    }

}
