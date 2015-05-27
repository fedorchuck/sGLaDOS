package io.github.fedorchuck.sglados_server.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

/**
 * Created by v on 25.05.2015.
 */
public class ServerConfig {
    public static final String DEFAULT_AGENT_CONFIG_FILE_NAME = "server_sglados_config.json";

    @JsonProperty("agentId")
    private UUID agentId;
    @JsonProperty("networking")
    private NetworkingConfig networking = new NetworkingConfig();
    @JsonProperty("records_count")
    private int recordsCount;

    /**
     * Reload config from the specified path early. Usually is called after setPath(path) method.
     */
    public void setAgentId(UUID agentId) {
        this.agentId = agentId;
    }

    public UUID getAgentId() {
        return agentId;
    }

    public NetworkingConfig getNetworking() {
        return networking;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public int getRecordsCount() {
        return recordsCount;
    }





}
