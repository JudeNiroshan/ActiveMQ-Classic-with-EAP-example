# ActiveMQ-Classic-with-EAP-example
Apache ActiveMQ classic setup with EAP 7.x

## How to run

1. Configure active mq classic version 5.16.3 locally
2. Start active mq classic broker
3. Clone this repository to your machine
4. Run `mvn clean install` in the cloned project directory
5. Copy the executable to EAP deployments directory
6. Start the EAP and visit the browser link -> http://localhost:8080/helloworld-mdb-37/hello
