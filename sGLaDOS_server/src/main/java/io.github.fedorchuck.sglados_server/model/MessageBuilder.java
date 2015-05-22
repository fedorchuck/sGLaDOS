package io.github.fedorchuck.sglados_server.model;

import io.github.fedorchuck.sglados_server.helperUnits.ThrowThrowable;

/**
 * Created by v on 22.05.2015.
 */
public class MessageBuilder {
    private String nameOfTable;
    private String address;
    private String name;
    private String data;
    private String message;


    public String getNameOfTable() {
        return nameOfTable;
    }

    public String getAddress() {
        return address;
    }

    public String getName() { return name; }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public MessageBuilder setNameOfTable(final String nameOfTable) {
        this.nameOfTable = nameOfTable;
        return this;
    }

    public MessageBuilder setAddress(final String address) {
        this.address = address;
        return this;
    }

    public MessageBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public MessageBuilder setData(final String data) {
        this.data = data;
        return this;
    }

    public MessageBuilder setMessage(final String message) {
        this.message = message;
        return this;
    }

    public Message newBuild() {
        if (nameOfTable == null)    { NullPointerException("nameOfTable");  return null; }
        if (address == null)        { NullPointerException("address");      return null; }
        if (name == null)           { NullPointerException("name");         return null; }
        if (data == null)           { NullPointerException("data");         return null; }
        if (message == null)        { NullPointerException("message");      return null; }
        else return new Message(this);

    }

    private void NullPointerException(final String field)
    {
        ThrowThrowable throwThrowable = new ThrowThrowable();
        String errorMessage = "at least one of the fields are null (" + field + ")";
        throwThrowable.throwError(this.getClass(), new NullPointerException(), errorMessage);
    }
}
