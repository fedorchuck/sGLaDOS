package io.github.fedorchuck.sglados_server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.github.fedorchuck.sglados_server.communication.ActiveMQ;
import org.junit.Test;

/**
 * Created by v on 16.05.2015.
 */
public class ServiceMessaging {
    /**
     This method demonstrated functional service messaging.
     **/
    @Test
    public static void main(String[] args) throws Exception
    {
        System.out.println(example());
        ActiveMQ.instance.createChannel(
                "tcp://localhost:61616",
                UUID.fromString("F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4"),
                "io.github.fedorchuck.sglados");//"tcp://192.168.19.37:61616"

        int i = 0;
        while(i<9)
        {
            ActiveMQ.instance.send(example() + ". Iteration " + Integer.toString(i));
            Thread.sleep(300);
            i++;
        }

        ActiveMQ.instance.receiver();
        System.in.read();

        ActiveMQ.instance.closeChannel();
    }

    //It is times construction. It is help to testing.
    private static String example()
    {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date).toString();//
        return time;
    }
}
