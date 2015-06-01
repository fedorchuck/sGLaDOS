package io.github.fedorchuck.sglados_server.config;

import io.github.fedorchuck.sglados_server.dataBase.Postgre;

/**
 * Created by v on 26.05.2015.
 */
public class DataBaseConfig {
    public DataBaseConfig() {}

    public DataBaseConfig(String db_connection,
                          String db_user,
                          String db_password) {
        Postgre db = new Postgre();
        //TODO: added checkings
        db.setDbConnection(db_connection);
        db.setDbUser(db_user);
        db.setDbPassword(db_password);
    }

}
