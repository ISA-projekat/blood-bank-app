package com.bloodbank.bloodbankapp.utils;

import com.bloodbank.bloodbankapp.model.DateRange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import javax.persistence.AttributeConverter;
import java.io.IOException;


public class DateRangeJSONConverter implements AttributeConverter<DateRange, String> {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DateRange object) {
        String jsonString = null;
        objectMapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("JSON writing error", e);
        }

        return jsonString;
    }

    @Override
    public DateRange convertToEntityAttribute(String jsonString) {
        DateRange object = null;
        objectMapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        try {
            object = objectMapper.readValue(jsonString, new TypeReference<DateRange>() {
            });
        } catch (final IOException e) {
            throw new RuntimeException("JSON reading error", e);
        }

        return object;
    }
}
