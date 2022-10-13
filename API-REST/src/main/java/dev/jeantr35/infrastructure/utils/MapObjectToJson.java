package dev.jeantr35.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapObjectToJson {

    public static String execute(Object o) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
