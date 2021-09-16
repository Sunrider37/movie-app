package com.example.movies.utils;

import com.example.movies.model.Type;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TypeSerialize extends JsonSerializer<Type> {

    @Override
    public void serialize(Type value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(value.getName());
    }
}
