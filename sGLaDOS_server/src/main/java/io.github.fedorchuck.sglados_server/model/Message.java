package io.github.fedorchuck.sglados_server.model;

/**
 * Created by v on 22.05.2015.
 */
public class Message {
    private final String nameOfTable;
    private final String address;
    private final String name;
    private final String data;
    private final String message;

    Message(final MessageBuilder tableBuilder){
        this.nameOfTable = tableBuilder.getNameOfTable();
        this.address = tableBuilder.getAddress();
        this.name = tableBuilder.getName();
        this.data = tableBuilder.getData();
        this.message = tableBuilder.getMessage();
    }

    public String getNameOfTable() {
        return nameOfTable;
    }

    public String getAddress() {
        return address;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("name of table \t"); tmp.append(getNameOfTable());   tmp.append('\n');
        tmp.append("address \t\t");    tmp.append(getAddress());      tmp.append('\n');
        tmp.append("name \t\t\t");      tmp.append(getName());          tmp.append('\n');
        tmp.append("date \t");  tmp.append(getData());    tmp.append('\n');
        tmp.append("message \t");tmp.append(getMessage());  tmp.append('\n');
        return tmp.toString();
    }
}
