package io.github.fedorchuck.sglados_server.commands.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.fedorchuck.sglados_server.utils.SerializationUtil;

/**
 * Created by v on 26.05.2015.
 */
public class CommandParameter {
    private JsonNode json;
    private String stringJson; // for toString() method

    public CommandParameter(JsonNode json) {
        this.json = json;
        this.stringJson = SerializationUtil.serialize(json);
    }

    public CommandParameter(String json) {
        this.json = SerializationUtil.deserialize(json, JsonNode.class);
        this.stringJson = json;
    }

    public boolean isStringValue(){
        return json.isTextual();
    }
    public boolean isIntValue(){
        return json.isInt();
    }
    public boolean isObjectValue(){
        return json.isObject();
    }
    public boolean isArrayValue() {
        return json.isArray();
    }

    public String getStringValue() {
        if(!isStringValue()) {
            //(json + " isn't text value")
            //throw new CommandArgumentException();
        }
        return json.textValue();
    }

    public int getIntValue() {
        if (!isIntValue()) {
            //(json + " isn't integer value");
            //throw new CommandArgumentException();
        }
        return json.intValue();
    }

    public ObjectNode getJSObject() {
        checkType(isObjectValue(), "Object");
        return (ObjectNode) json;
    }

    public double getDoubleValue() {
        checkType(json.isDouble(), "Double");
        return json.doubleValue();
    }

    public ArrayNode getJSArrayValue() {
        checkType(isArrayValue(), "Array");
        return (ArrayNode) json;
    }
    public Boolean getJSBoolValue() {
        checkType(json.isBoolean(), "Boolean");
        return Boolean.parseBoolean(json.toString());
    }

    private void checkType(boolean isCorrect, String type) {
        if (!isCorrect) {
            //(json + " must be object value");
            //throw new CommandArgumentException(type + " expected");
        }
    }

    public <T> T getObject(Class<T> type) {
        return SerializationUtil.deserialize(json, type);
        //return type.cast(getJSObject().toBean(type));
    }

    @Override
    public String toString() {
        return stringJson;
    }
}
