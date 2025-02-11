//splitter and content-based router
//Content Based routing- Route the message based on the token it contains.
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
import java.util.Date;

public class FileToActiveMQ4 {
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
                from("file://input_box/?fileName=test2.txt&charset=utf-8&noop=true")
                    .log("Starting to process file: ${header.CamelFileName}")
                	.split().tokenize("\n")
                .to("direct:test");
                from("direct:test")
                	.setHeader("myHeader", constant("10Feb2025"))
                    .log("Routing as per content in ${file:name}")
                .to("direct:test1");
                from("direct:test1")
                	.choice().
                        when(body().contains("queue1-content"))
                    .to("activemq:queue:Queue1").
                        when(body().contains("queue2-content"))
                    .to("activemq:queue:Queue2")
                        .when(body().contains("queue3-content"))
                    .to("activemq:queue:Queue3").
                    otherwise()
                .to("activemq:queue:my_queue_extras");
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

