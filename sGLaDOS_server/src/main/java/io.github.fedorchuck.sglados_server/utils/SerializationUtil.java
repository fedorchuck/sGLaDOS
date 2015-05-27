package io.github.fedorchuck.sglados_server.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by v on 26.05.2015.
 */
public class SerializationUtil {
    private static ObjectMapper mapper = new ObjectMapper();
    private static ObjectMapper indentedMapper = new ObjectMapper();
    private static ThrowThrowable throwThrowable;

    public SerializationUtil() {
        ThrowThrowable th = new ThrowThrowable();
        throwThrowable = th;
    }

    public static String serialize(Object obj)
    {
        if(obj==null) // obj can't be null
        {
            return "null";
        }
        try {
            return  mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            String message = String.format("error serializing object of type %s with value: %s",
                    obj.getClass().getName(),obj.toString());
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            return null;
        }
    }

    public String indentedSerialize (Object obj) {
        String string = "";
        if(obj==null)
        try {
            indentedMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
            string = indentedMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            final String message = String.format("error serializing object of type %s with value: %s",
                    obj.getClass().getName(),obj.toString());
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            return null;
        }
        return string;
    }


    public static <T> T deserialize(String str, Class<T> clss) {
        if (isNullOrEmpty(str))
            throwThrowable.throwError("SerializationUtil", new NullPointerException(), "str");
        try {
            return mapper.readValue(str, clss);
        } catch (IOException ex) {
            final String message = String.format("error deserializing object of type %s with value: %s",
                    clss.getName(),str);
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            throw null;
        }
    }

    public static <T> T deserialize(JsonNode json, Class<T> type) {
        if(json==null)
            throwThrowable.throwError("SerializationUtil", new NullPointerException(), "No input JSON");

        return mapper.convertValue(json, type);
    }

    public static void saveToFile(Object obj, File file){

        if(obj==null) // obj can't be null
            return;
        try {
            indentedMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
            indentedMapper.writeValue(file, obj);
        } catch (JsonProcessingException ex) {
            final String message = String.format("error serializing object of type %s with value: %s",
                    obj.getClass().getName(),obj.toString());
            throw new SerializationException(message,ex);
        } catch (IOException ex){
            final String message = String.format("error serializing object of type %s with value: %s",
                    obj.getClass().getName(),obj.toString());
            throw new SerializationException(message, ex);
        }
    }

    public static <T> T readFromFile(File file, Class<T> type) {
        try {
            return mapper.readValue(file, type);
        } catch (JsonGenerationException ex) {
            final String message = String.format("error deserializing object of type %s from file: %s",
                    type.getName(), file.getName());
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            return null;
        } catch (JsonMappingException ex) {
            final String message = String.format("error deserializing object of type %s from file: %s",
                    type.getName(), file.getName());
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            return null;
        } catch (IOException ex) {
            final String message = String.format("error deserializing object of type %s from file: %s",
                    type.getName(), file.getName());
            throwThrowable.throwError("SerializationUtil", new SerializationException(message), message);
            return null;
        }
    }
}
