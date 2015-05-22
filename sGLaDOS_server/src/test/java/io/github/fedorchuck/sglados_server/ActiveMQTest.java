package io.github.fedorchuck.sglados_server;

import io.github.fedorchuck.sglados_server.communication.ActiveMQ;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by v on 20.05.2015.
 */
public class ActiveMQTest extends TestCase {
    @Test
    public void testCreateChannel() throws InterruptedException {
        ActiveMQ.instance.createChannel(
                "tcp://localhost:61616",
                UUID.fromString("F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4"),
                "io.github.fedorchuck.sglados");

        Assert.assertEquals(ActiveMQ.instance.getHost(), "tcp://localhost:61616");
        Assert.assertEquals(ActiveMQ.instance.getQueue(), "io.github.fedorchuck.sglados");
    }

    @Test
    public void testSend() throws Exception {
        Assert.assertEquals(ActiveMQ.instance.send("test"),true);
    }

    public void testCloseChannel() throws Exception {
        Assert.assertEquals(ActiveMQ.instance.closeChannel(),true);
    }
}