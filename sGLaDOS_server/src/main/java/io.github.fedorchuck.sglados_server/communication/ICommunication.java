package io.github.fedorchuck.sglados_server.communication;

import javax.jms.JMSException;
import java.util.UUID;

/**
 * Created by v on 27.05.2015.
 */
public interface ICommunication extends AutoCloseable
{
    boolean send(String dataToSend);
    void receiver();
    void createChannel(String host, UUID id, String queue) throws InterruptedException;

    void createReceivingChanel(String host, String queue);

    void close() throws JMSException, InterruptedException;
    boolean checkAvailable(String host, int port);
    boolean checkAvailable(String url);

    /**
     * Subscribe on this event to receive each message from server.
     */
//    void addMessageReceivedListener(IMessageReceivedListener handler);
//    void removeMessageReceivedListener(IMessageReceivedListener handler);
//
//    void addConnectionLostListener(IConnectionStateListener handler);
//    void removeConnectionLostListener(IConnectionStateListener handler);
//
//    void addConnectionRestoredListener(IConnectionStateListener handler);
//    void removeConnectionRestoredListener(IConnectionStateListener handler);


//    static class Inst
//    {
//        private static volatile ICommunication instance;
//
//        public static ICommunication getInstance()
//        {
//            if(instance==null)
//                synchronized (Inst.class)
//                {
//                    if (instance == null)
//                        instance = new ActiveMqCommunication();
//                }
//            return instance;
//        }
//    }
}