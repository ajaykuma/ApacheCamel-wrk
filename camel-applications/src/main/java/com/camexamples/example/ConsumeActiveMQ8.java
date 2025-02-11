//Consume from ActiveMQ queue
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
//import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;



public class ConsumeActiveMQ8 {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
        //creating a connection to activeMQ server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");

        //adding jms component to camel context
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            //define endpoints
            from("activemq:queue:Queue2")
            //.to("seda:end");
            .to("seda:end?concurrentConsumers=2").log("Test");
            from("seda:end")
            //.to("log:msgs?level=DEBUG")
            //.to("log:mylogs?showAll=true&multiline=true")
            .to("file:Output-new");
            }
        });

        context.start();
        
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println("---------------" + java.time.LocalDateTime.now() + " " + message);
        Thread.sleep(1 * 60 * 1000);
        context.stop();
    }
}
