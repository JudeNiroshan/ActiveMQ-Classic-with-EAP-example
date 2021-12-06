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
        @ActivationConfigProperty(propertyName = "destination", propertyValue = QUEUE_NAME),
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/TestQueue"),
//        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
//        @ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
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
