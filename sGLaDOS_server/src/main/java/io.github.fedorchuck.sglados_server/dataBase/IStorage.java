package io.github.fedorchuck.sglados_server.dataBase;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by v on 25.05.2015.
 */
public interface IStorage {
    void connectionOpen() throws SQLException;
    void saveResponse();//to dataBase
    void removeFirstMonitor(int count);
    Connection getConnection();


    boolean createTable(String sql);

    void connectionClose();
    int getLastId(String tableName);
    boolean psqlAvailable(String host, int port);
}
