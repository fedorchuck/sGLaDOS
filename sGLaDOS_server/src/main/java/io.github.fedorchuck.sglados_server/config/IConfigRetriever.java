package io.github.fedorchuck.sglados_server.config;

/**
 * Created by v on 25.05.2015.
 * Interface to extracting and saving {@code ServerConfig} to different
 * sources (JSON file, simple file etc)
 */
public interface IConfigRetriever {
    ServerConfig getConfig();
    void saveConfig(ServerConfig config);
}
