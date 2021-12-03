package org.jboss.as.quickstarts.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

import static org.jboss.as.quickstarts.Constants.QUEUE_NAME;

@MessageDriven(name = "FooQueueListenerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//    @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/TestQueue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = QUEUE_NAME),
//        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
//
////    @ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
//        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "4"),
//        @ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue = "600"),
//        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})


public class FooQueueListenerMDB implements MessageListener {

    private final Logger LOGGER = Logger.getLogger(FooQueueListenerMDB.class.toString());

    public void onMessage(Message rcvMessage) {
        try {
            if (rcvMessage instanceof TextMessage) {
                TextMessage msg = (TextMessage) rcvMessage;
                LOGGER.info("[CONSUMER] Received msg: " + msg.getText());
            } else {
                LOGGER.warning("[CONSUMER] Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            System.out.println("[CONSUMER] something went wrong:" + e.getMessage());
        }
    }
}
