package io.github.fedorchuck.sglados_server.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Created by v on 25.05.2015.
 */
public class ServerConfig {
    public static final String DEFAULT_AGENT_CONFIG_FILE_NAME = "server_sglados_config.json";

    @JsonProperty("serverId")
    private UUID serverId;
    @JsonProperty("networking")
    private NetworkingConfig networking = new NetworkingConfig();
    @JsonProperty("dataBase")
    private DataBaseConfig dataBase = new DataBaseConfig();

    /**
     * Reload config from the specified path early. Usually is called after setPath(path) method.
     */
    public void setServerId(UUID serverId) {
        this.serverId = serverId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public NetworkingConfig getNetworking() {
        return networking;
    }

    public DataBaseConfig getDataBase() { return dataBase; }

    //region Description
    /*public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public int getRecordsCount() {
        return recordsCount;
    }*/
    //endregion





}
