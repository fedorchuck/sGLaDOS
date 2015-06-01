package io.github.fedorchuck.sglados_server.config;


/**
 * Created by v on 26.05.2015.
 */
public class DataBaseConfig {
    private String connection;
    private String userName;
    private String password;

    public DataBaseConfig() {}

    public DataBaseConfig(String connection,
                          String userName,
                          String password) {
        this.connection = connection;
        this.userName = userName;
        this.password = password;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
