//sending file/file-content to ActiveMQ Queue with transformation
package com.camexamples.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;

public class FileToActiveMQ2 {
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
                from("file://input_box/?fileName=test1.txt&charset=utf-8&noop=true")
                        //try running the same example with uncommenting below line ie split,tokenize and streaming
                        //.split().tokenize("\n").streaming()
                        //to transform before data reaches destination
                        /*we could also use inline transform for this*/
                        .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                        //Note processor has a process method that has Exchange object using which we can do a get
                            String message = exchange.getIn().getBody(String.class);
                            message = message.toUpperCase() + " <--msges have to be altered";
                                //setting back the object as exchange object for next endpoint to receive it
                                //exchange.getOut().setBody(message); /*since getOut is deprecated*/
                            exchange.getMessage().setBody(message);

                            }
                        }) //method that takes processor as an argument
                        .to("activemq:queue:my_queue2");


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