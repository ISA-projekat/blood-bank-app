package com.bloodbank.bloodbankapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;


public class ObjectJSONConverter<T> implements AttributeConverter<T, String> {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T object) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        }
        catch (final JsonProcessingException e) {
            throw new RuntimeException("JSON writing error", e);
        }

        return jsonString;
    }

    @Override
    public T convertToEntityAttribute(String jsonString) {

        T object = null;
        try {
            object = objectMapper.readValue(jsonString, new TypeReference<T>() {});
        }
        catch (final IOException e) {
            throw new RuntimeException("JSON reading error", e);
        }

        return object;
    }
}
