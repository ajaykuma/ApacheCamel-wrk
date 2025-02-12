package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
//import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.language.Bean;
import org.springframework.context.annotation.Configuration;
//import javax.jms.ConnectionFactory;

public class ContentfromRest {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
        //creating a connection to activeMQ server
        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        //adding jms component to camel context
        //context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                
            	restConfiguration().component("servlet").port(8080);
            	rest("/api")
            	.get("/testing-REST").to("direct:test");
            	from("direct:test").transform().constant("Test completed");
            }
        });
        //starting the context & running infinitely
        //while(true)
        //    context.start();
        //
        // Start the context with sleep time
        context.start();
        Thread.sleep(5 * 60 * 1000);
        context.stop();
    }
}

