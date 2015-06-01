package io.github.fedorchuck.sglados_server.config;

import com.google.inject.Singleton;
import io.github.fedorchuck.sglados_server.utils.SerializationUtil;
import org.apache.maven.settings.Server;
import org.slf4j.Logger;

import java.io.File;

/**
 * Created by v on 26.05.2015.
 * Concrete {@code IConfigRetriever implementation} that extracts
 * {@code ServerConfig} from JSON file
 */
@Singleton
public class JsonServerConfigRetriever implements IConfigRetriever {

    public static final String DEFAULT_AGENT_CONFIG_FILE_NAME = "server_sglados_config.json";
    private ServerConfig config = null;
    private String path = null;

    public JsonServerConfigRetriever() {
        this(DEFAULT_AGENT_CONFIG_FILE_NAME);
    }

    public JsonServerConfigRetriever(String path) {
        this.path = path;
    }

    @Override
    public ServerConfig getConfig() {
        if (config == null) {
            System.out.println("Loading config...");
            config = loadFromFile(path);
            if (config != null) {
                System.out.println("Config has been loaded");

                return config;
            } else {
                /*System.out.println("config:" + config);
                System.out.println("path:" + path);
                System.out.println(new File("").getAbsolutePath());*/
                //System.out.println("FATAL ERROR: config is empty, or it is not available.");
                throw new Error("FATAL ERROR: config is empty, or is not available.");
            }
        }
        else {
            return config;
        }
    }

    @Override
    public void saveConfig(ServerConfig config) {
        File configFile = new File(path);
        SerializationUtil.saveToFile(config, configFile);
        System.out.println("Server config was saved.");
    }

    private static ServerConfig loadFromFile(String path) {
        File configFile = new File(path);
        return SerializationUtil.readFromFile(configFile, ServerConfig.class);
    }
}
