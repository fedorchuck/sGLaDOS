package io.github.fedorchuck.sglados_server.communication;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by v on 16.05.2015.
 * <p>
 * WARNING! YOU MUST USE ACTIVEMQ SERVER<p>
 * > You can use this link if you want
 * download ActiveMQ receivingQueue.
 * https://app.box.com/s/t4d0rjvtaruodckjsfhn
 * [OK]<p>
 * > If you want see queues and topics on the
 * website - use http://localhost:8161/
 * login, password - admin.
 * [OK]<p>
 */
@Singleton
public class ActiveMQ implements ICommunication, AutoCloseable {
    public static final ActiveMQ instance = new ActiveMQ();
    private String host;
    private String queue;
    private Destination receivingQueue;     // LISTENER
    private Destination sendingQueue;       // PUBLISHER
    private UUID id;
    private Session session;
    private javax.jms.Connection connection;
    private MessageConsumer consumer;
    private MessageProducer producer;
    private Thread thread;
    private boolean canRun = true;
    @Inject
    private ActiveMQ() {
    }

    public void setCanRun(boolean canRun) {
        this.canRun = canRun;
    }

    public boolean getCanRun() {
        return canRun;
    }

    public void setHost(String nameOfHost) {
        host = nameOfHost;
    }

    public String getHost() {
        return host;
    }

    public void setQueue(String nameQueue) {
        queue = nameQueue;
    }

    public String getQueue() {
        return queue;
    }

    /**
     * Send the message in created channel.
     * Don't forget changed and checked sendingQueue before sent the message.
     */
    @Override
    public synchronized boolean send(String dataToSend) {
        if (dataToSend == null) return false;
        try {
            producer = session.createProducer(sendingQueue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage message = session.createTextMessage(dataToSend);
            message.setJMSMessageID(id.toString());
            message.setJMSCorrelationID(id.toString());//"F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4"

            producer.send(message);

            producer.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Receive a message from chose channel.
     */
    @Override
    public synchronized void receiver() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    List<String> an = new ArrayList<String>();

                    consumer = session.createConsumer(receivingQueue);

                    while (canRun) {
                        // Wait for a message
                        Message message = consumer.receive(300);

                        if (message instanceof TextMessage) {
                            TextMessage textMessage = (TextMessage) message;
                            String text = textMessage.getText();
                            an.add(text);

                            receavedMessage(text);

                            message.clearBody();
                        } else an.add("" + message);
                    }
                } catch (JMSException e) {
                    e.getStackTrace();
                    e.getMessage();
                }
            }
        };
        thread.start();
    }

    /**
     * Create the channel with names of host and queue. Create only once.
     */
    @Override
    public void createChannel(String host, UUID id, String queue) throws InterruptedException {
        this.host = host;
        this.queue = queue;
        this.id = id;

        //start(host, queue);
        sendingQueue = start(host, queue + ".Client_" + id);//send to
        receivingQueue = start(host, queue + ".Server");    //take from
    }

    @Override
    public void createReceivingChanel(String host, String queue) {
        this.host = host;
        this.queue = queue;

        this.receivingQueue = start(host, queue + ".Server");    //take from
    }

    /**
     * Close the all channel
     */
    @Override
    public void close() throws JMSException, InterruptedException {
        canRun = false;
        Thread.sleep(1000);
        thread.stop();

        try {
            consumer.close();
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("tried to close closed or not open channel.");
            e.getStackTrace();
            e.getMessage();
        }
    }

    @Override
    public boolean checkAvailable(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * "jdbc:postgresql://localhost:5432/"
     * */
    @Override
    public boolean checkAvailable(String url) {

        String line0[] = url.split("/");
        StringBuilder tmp = new StringBuilder();
        for (String line : line0)
            tmp.append(line);
        String line1[] = tmp.toString().split(":");
        return checkAvailable(line1[1],Integer.parseInt(line1[2].toString()));
    }

    /**
     * Take the destination
     */
    private Destination start(String host, String queue) {
        Destination destination = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(host);

            connection = connectionFactory.createConnection();
            connection.start();
            //connection.setExceptionListener(this);

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queue);
            return destination;
        } catch (JMSException e) {
            e.getStackTrace();
            e.getMessage();
            return null;
        }
    }

    /**
     * @deprecated
     * */
    private void receavedMessage(String text) {
        //TODO: connect PostgreSQL
        System.out.println(text);

        System.out.println("*******************");
    }

    /**
     * It is times construction. used it for tests.
     * @deprecated
     * */
    public static String example()
    {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date).toString();//
        return time;
    }




}
