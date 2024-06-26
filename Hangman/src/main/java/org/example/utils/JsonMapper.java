package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;

//Мапит данные на DTO
public final class JsonMapper {

    private JsonMapper() {
    }

    public static <T> T getFromJSON(Class<T> classObj, String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        try {
            t = objectMapper.readValue(new File(fileName), classObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}

