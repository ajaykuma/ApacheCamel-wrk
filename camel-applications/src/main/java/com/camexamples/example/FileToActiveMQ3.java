//splitting & load balancing
package com.camexamples.example;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FileToActiveMQ3 {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://inbox")
                        .split()
                        .tokenize("\n")
                        .streaming()
                        .loadBalance().roundRobin()
                .to("activemq:queue:Queue4")
                .to("activemq:queue:Queue5")
                .to("activemq:queue:Queue6");

            }
        });

        // Start the context
        context.start();
        Thread.sleep(5 * 60 * 1000);
        context.stop();

    }
}