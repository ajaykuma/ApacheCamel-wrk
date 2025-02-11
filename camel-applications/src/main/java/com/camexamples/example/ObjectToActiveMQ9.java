//Sending an object to ActiveMQ queue
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sun.rmi.runtime.Log;

import javax.jms.ConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;
//import java.io.File;
import java.util.Date;

public class ObjectToActiveMQ9 {
    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        //creating a connection to activeMQ server
        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        //adding jms component to camel context
        //context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        //Using MQ component
        //setting trusted packages
        
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        factory.setTrustAllPackages(true);
      
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                //define endpoints
                from("direct:start")
                        //try this .. >    .wireTap("file://testOutput")

                        .to("activemq:queue:my_queue1");
                                 }
        });
        context.start();
        //Thread.sleep(1 * 60 * 1000);
        ProducerTemplate producerTemplate =  context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", new Date()); //object will be sent to endpoint
        context.stop();
    }
}
