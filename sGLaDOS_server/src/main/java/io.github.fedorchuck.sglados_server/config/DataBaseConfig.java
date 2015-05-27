package io.github.fedorchuck.sglados_server.config;

/**
 * Created by v on 26.05.2015.
 */
public class DataBaseConfig {
    private String db_connection;
    private String db_user;
    private String db_password;

    public DataBaseConfig() {}

    public DataBaseConfig(String db_connection,
                          String db_user,
                          String db_password) {
        this.db_connection = db_connection;
        this.db_user = db_user;
        this.db_password = db_password;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_connection() {
        return db_connection;
    }

    public void setDb_connection(String db_connection) {
        this.db_connection = db_connection;
    }
}
