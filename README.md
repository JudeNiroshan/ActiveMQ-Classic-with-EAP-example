# ActiveMQ classic with Red Hat Middleware EAP Demo
Apache activeMQ classic setup with Red Hat Middleware EAP 7.x

## What is demonstrated here? 🤔
2 queues have been used to demonstrate the producer and consumer 
workflows. Intentionally used 2 queues just to show the configurations.

![overview](./docs/overview.png)

## Red Hat Middleware EAP configurations ⚙️

You need to use activemq-rar.rar adapter in order to connect 
EAP with a JMS implementation such as activemq classic (not artemis).

This adapter can be downloaded [here](https://maven.repository.redhat.com/ga/org/apache/activemq/activemq-rar/).
Place it in your standalone/deployments folder.

Edit the standalone.xml file as described [here](https://access.redhat.com/documentation/en-us/red_hat_amq/6.3/html/integrating_with_jboss_enterprise_application_platform/deployrar-installrar). 

## How to run 🏃

1. Configure active mq classic version 5.16.3 locally
2. Start active mq classic broker
3. Clone this repository to your machine
4. Run `mvn clean install` in the cloned project directory
5. Copy the executable to EAP deployments directory
6. Start the EAP and visit the browser link -> http://localhost:8080/helloworld-mdb-37/
