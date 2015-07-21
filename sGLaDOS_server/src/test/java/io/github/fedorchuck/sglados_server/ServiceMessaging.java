package io.github.fedorchuck.sglados_server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.github.fedorchuck.sglados_server.communication.ActiveMQ;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by v on 16.05.2015.
 */
public class ServiceMessaging {
    /**
     * This method demonstrated functional service messaging.
     * @deprecated
     **/
    @Ignore
    @Test(timeout = 5000)
    //public static void main(String[] args) throws Exception
    public void testActiveMQ() throws Exception
    {
        if (ActiveMQ.instance.checkAvailable("localhost", 61616)) {
            System.out.println(ActiveMQ.instance.example());
            ActiveMQ.instance.createChannel(
                    "tcp://localhost:61616",
                    UUID.fromString("F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4"),
                    "io.github.fedorchuck.sglados");//"tcp://192.168.19.37:61616"

            int i = 0;
            while (i < 9) {
                ActiveMQ.instance.send(ActiveMQ.instance.example() + ". Iteration " + Integer.toString(i));
                Thread.sleep(300);
                i++;
            }

            ActiveMQ.instance.receiver();
            System.in.read();

            ActiveMQ.instance.close();

            Assert.assertTrue(true);
        }
        else Assert.assertTrue(true);
    }

}
