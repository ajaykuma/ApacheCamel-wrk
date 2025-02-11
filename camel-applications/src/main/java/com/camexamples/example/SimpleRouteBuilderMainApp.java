package com.camexamples.example;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class SimpleRouteBuilderMainApp {

    public static void main(String[] args) {
        SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
        CamelContext context = new DefaultCamelContext();

//configure jms component
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        try {
            context.addRoutes(routeBuilder);
            context.start();
            Thread.sleep(5 * 60 * 1000);
            context.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
