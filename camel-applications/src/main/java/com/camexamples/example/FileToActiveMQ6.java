//Recipient List
//Deciding the name of the queue at run time depending on the content
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
//import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;

public class FileToActiveMQ6 {
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
                        //if just reading from folder
                        //from("file:input_box?noop=True")
                        .split().tokenize("\n").to("direct:test");
                from("direct:test")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String recipient = exchange.getIn().getBody().toString();
                                String recipientQueue="activemq:queue:"+recipient;
                                exchange.getIn().setHeader("queue", recipientQueue);
                            }
                        }).recipientList(header("queue"));
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

