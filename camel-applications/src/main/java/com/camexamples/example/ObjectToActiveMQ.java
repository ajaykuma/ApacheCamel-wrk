//Sending an object to ActiveMQ queue
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;

public class ObjectToActiveMQ {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
        //creating a connection to activeMQ server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //or
//ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
//factory.setTrustedPackages(new ArrayList(Arrays.asList("org.apache.activemq.test,java.util.Date,
        //org.apache.camel.test".split(","))));
//adding jms component to camel context
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //define endpoints
                from("direct:start")
                        .to("activemq:queue:my_queue");
            }
        });

        context.start();
        ProducerTemplate producerTemplate =  context.createProducerTemplate();
        producerTemplate.sendBody("direct:start",new Date()); //date will be sent to endpoint
    }
}
