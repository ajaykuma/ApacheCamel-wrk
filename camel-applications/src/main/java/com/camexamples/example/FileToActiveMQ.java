package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class FileToActiveMQ {
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
                from("file:input_box?noop=True")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                //Note processor has a process method that has Exchange object using which we can do a get
                                String message = exchange.getIn().getBody(String.class);
                                message = message.toUpperCase() + " <--msg has be altered";
                                //setting back the object as exchange object for next endpoint to receive it
                                exchange.getOut().setBody(message);
                            }
                        }) //method that takes processor as an argument
                        .to("activemq:queue:my_queue");
            }
        });
        //while(true)
        //    context.start();
        // Start the context
        context.start();
        Thread.sleep(5 * 60 * 1000);
        context.stop();
    }
}
