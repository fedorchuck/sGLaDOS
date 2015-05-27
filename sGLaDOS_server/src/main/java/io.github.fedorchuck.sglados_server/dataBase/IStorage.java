package io.github.fedorchuck.sglados_server.dataBase;


/**
 * Created by v on 25.05.2015.
 */
public interface IStorage {
    void saveResponse();//to dataBase
    void removeFirstMonitor(int count);
}
