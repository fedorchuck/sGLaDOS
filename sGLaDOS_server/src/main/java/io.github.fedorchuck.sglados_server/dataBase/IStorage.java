package io.github.fedorchuck.sglados_server.dataBase;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by v on 25.05.2015.
 */
public interface IStorage {
    void setConnection(String dbConnection);
    Connection getConnection();
    void setUser(String dbUser);
    void setPassword(String dbPassword);

    void connectionOpen();
    void saveResponse();//to dataBase


    boolean createTable(String sql);

    void connectionClose();
    int getLastId(String tableName);

    boolean psqlAvailable(String host, int port);
    boolean psqlAvailable(String url);
}
