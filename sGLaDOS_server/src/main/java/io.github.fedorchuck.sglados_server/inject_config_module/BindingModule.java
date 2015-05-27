package io.github.fedorchuck.sglados_server.inject_config_module;

import com.google.inject.AbstractModule;
import io.github.fedorchuck.sglados_server.communication.ActiveMQ;
import io.github.fedorchuck.sglados_server.communication.ICommunication;
import io.github.fedorchuck.sglados_server.config.IConfigRetriever;
import io.github.fedorchuck.sglados_server.config.JsonServerConfigRetriever;
import io.github.fedorchuck.sglados_server.dataBase.IStorage;
import io.github.fedorchuck.sglados_server.dataBase.Postgre;

/**
 * Created by v on 26.05.2015.
 */
public class BindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IConfigRetriever.class).to(JsonServerConfigRetriever.class);
        bind(IStorage.class).to(Postgre.class);
        bind(ICommunication.class).to(ActiveMQ.class);

    }

}