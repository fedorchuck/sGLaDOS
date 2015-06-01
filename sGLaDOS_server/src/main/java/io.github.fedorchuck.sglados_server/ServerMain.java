package io.github.fedorchuck.sglados_server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.fedorchuck.sglados_server.dataBase.Postgre;
import io.github.fedorchuck.sglados_server.inject_config_module.CommandsBindingModule;

import java.sql.SQLException;

/**
 * Created by v on 25.05.2015.
 */
public class ServerMain {
    public static void main(String[] args){

        Injector injector = Guice.createInjector(new CommandsBindingModule());
        ServerExecutor serverExecutor = injector.getInstance(ServerExecutor.class);
        serverExecutor.execute(args);

    }
}
