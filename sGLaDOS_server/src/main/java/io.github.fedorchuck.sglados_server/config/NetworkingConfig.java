package io.github.fedorchuck.sglados_server.config;

/**
 * Created by v on 25.05.2015.
 */
public class NetworkingConfig {
    private String url;
    private String queueName;
    private int reconnectionInterval;

    public NetworkingConfig() {}

    public NetworkingConfig(String url,
                            String queueName,
                            int reconnectionInterval)
    {
        if ("".equals(url)) url = null;
        if ("".equals(queueName)) queueName = null;
        this.url = url;
        this.queueName = queueName;
        this.reconnectionInterval = reconnectionInterval;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
    }

    public String getQueueName()
    {
        return queueName;
    }

    public int getReconnectionInterval() {
        return reconnectionInterval;
    }

    public void setReconnectionInterval(int reconnectionInterval) {
        this.reconnectionInterval = reconnectionInterval;
    }

}
