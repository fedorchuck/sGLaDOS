package io.github.fedorchuck.sglados_server;

import com.google.inject.Inject;
import io.github.fedorchuck.sglados_server.communication.ICommunication;
import io.github.fedorchuck.sglados_server.config.IConfigRetriever;
import io.github.fedorchuck.sglados_server.dataBase.IStorage;
import io.github.fedorchuck.sglados_server.utils.InstanceCreator;
import io.github.fedorchuck.sglados_server.view.Console;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by v on 25.05.2015.
 */
public class ServerExecutor {
    private final ICommunication communication;
    private final IStorage storage;
    private final IConfigRetriever retriever;

    @Inject
    public ServerExecutor(ICommunication communication,
                          IStorage storage,
                          IConfigRetriever retriever)
    {
        /*bind(Db.class).annotatedWith(SystemDb.class).to(DbImplOfSomeSort.class);
        bind(StatsdClient.class).to(StatsdClientImplOfSomeSort.class);*/
        //BindingModule.configure();
        this.communication = communication;
        this.storage = storage;
        this.retriever = retriever;
    }

    public void execute(String args[])
    {
        System.out.println("start server sdlados.");
        //default starting without params
        if (args.length == 0) {
            try {
                if (retriever.getConfig().getServerId() == null) {
                    generateAgentID();
                }
                connectModules();

                //startActiveMQ(config);
                //TODO: create listening & sending queue
                //TODO: run console

            } finally {
                //TODO: close all.
                System.exit(0);
            }
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "-console":
                    Console.run(args);
                    break;
                case "-connector":
                    try {
                            System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        // TODO: all.close();
                    }
                    break;
                case "-genid":
                    generateAgentID();
                    break;
                default:
                    Console.run(args);
                    break;
            }
        }
        //more then 1 parameter
        //if (args.length > 1) Console.main(Arrays.copyOfRange(args, 1, args.length));
    }

    //I don't know. Maybe it isn't needed
    private static boolean logToConsole(String[] argv) {
        for (String arg : argv) {
            if (arg == "-consolelog") {
                return true;
            }
        }
        return false;
    }

    private void connectModules() {
        System.out.println(retriever.getConfig().getServerId());
        //System.out.println(retriever.getConfig().getDataBase().getConnection());
        storage.setConnection(retriever.getConfig().getDataBase().getConnection());
        storage.setUser(retriever.getConfig().getDataBase().getUserName());
        storage.setPassword(retriever.getConfig().getDataBase().getPassword());

        if (storage.psqlAvailable(retriever.getConfig().getDataBase().getConnection()))
                storage.connectionOpen();
        else throw new RuntimeException("fatal error. check config. problem with dataStorege module. ");

        if (communication.checkAvailable(retriever.getConfig().getNetworking().getUrl()))
                communication.createReceivingChanel(
                        retriever.getConfig().getNetworking().getUrl(),
                        retriever.getConfig().getNetworking().getQueueName()
                );
        else throw new RuntimeException("fatal error. check config. problem with communication module. ");

        System.out.println("ok.");
    }

    private static void generateAgentID() {
        UUID agentId = UUID.randomUUID();
        IConfigRetriever config = InstanceCreator.getInstance(IConfigRetriever.class);
        config.getConfig().setServerId(agentId);
        config.saveConfig(config.getConfig());
    }
}
