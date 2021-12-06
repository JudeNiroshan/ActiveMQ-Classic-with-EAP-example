/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.logging.Logger;

import static org.jboss.as.quickstarts.Constants.QUEUE_NAME;
import static org.jboss.as.quickstarts.Constants.TMP_QUEUE_NAME;

@MessageDriven(name = TMP_QUEUE_NAME, activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = TMP_QUEUE_NAME),
})
public class ProducerExampleMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(ProducerExampleMDB.class.toString());

    @Resource(mappedName = "java:/AMQXAConnectionFactory")
    private ConnectionFactory connectionFactory;

    public void onMessage(Message rcvMessage) {
        LOGGER.info("[PRODUCER] Message is about to send...");
        try {
            if (rcvMessage instanceof TextMessage) {
                TextMessage msg = (TextMessage) rcvMessage;

                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
                LOGGER.info("[PRODUCER] session created");

                Destination destination = session.createQueue(QUEUE_NAME);
                MessageProducer producer = session.createProducer(destination);

                TextMessage textMessage = session.createTextMessage(String.valueOf(msg));
                producer.send(textMessage);
                producer.close();

                LOGGER.info("[PRODUCER] Message sent to ActiveMQ!");

            } else {
                LOGGER.warning("[PRODUCER] Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
