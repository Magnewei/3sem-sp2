package app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExampleService {
    public static JsonNode getJsonFromString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);

        } catch (Exception e) {
            throw new RuntimeException("Error" + e.getMessage());
        }
    }

    public static String getJsonFromClass(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);


        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization of class failed." + e.getMessage());
        }
    }
}
