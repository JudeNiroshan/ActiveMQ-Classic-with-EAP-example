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
package org.jboss.as.quickstarts.servlet;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.jboss.as.quickstarts.Constants.TMP_QUEUE_NAME;

@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/" + TMP_QUEUE_NAME,
                        interfaceName = "javax.jms.Queue",
                        destinationName = TMP_QUEUE_NAME
                )
        }
)

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = -8314035702649252239L;

    @Resource(mappedName = "java:/AMQXAConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/queue/" + TMP_QUEUE_NAME)
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and " +
                "<strong>EJB 3.2 Message-Driven Bean</strong> in JBoss EAP.</h1>");
        try {
            final Destination destination = queue;
            final String text = "This is a test message.";

            out.write("<p>Sending message to <em>" + destination + "</em></p>" +
                    "<h2>The following message will be sent to the destination:</h2>");

            try {
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.SESSION_TRANSACTED);
                MessageProducer producer = session.createProducer(destination);

                final TextMessage message = session.createTextMessage(text);
                producer.send(message);
                Thread.sleep(100);

                producer.close();
                session.close();
            } catch (JMSException | InterruptedException e) {
                e.printStackTrace();
            }
            out.write("Message : " + text + "</br>" +
                    "<p><i>Go to your JBoss EAP server console or server log to see the result of messages " +
                    "processing.</i></p>");
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
