package com.camexamples.example;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class LoadBalanceMQ {

    public static void main(String[] args) throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //make sure the listed queues are created in ActiveMQ
                from("file:inbox?noop=true").split().tokenize("\n").loadBalance().roundRobin()
                        .to("activemq:queue:test1").to("activemq:queue:test2").to("activemq:queue:test3");

            }
        });

        // Start the context
        ctx.start();
        Thread.sleep(5 * 60 * 1000);
        ctx.stop();

    }
}