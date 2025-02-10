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

public class FileToActiveMQForLog {
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
                from("file://input_box/?fileName=yarn-hdu-resourcemanager-um2.log&charset=utf-8&noop=true")
                        //if just reading from folder
                        //from("file:input_box?noop=True")
                        .split().tokenize("\n")
                        .streaming()
                        .to("direct:test");
                from("direct:test")
                        .choice().
                        when(body().contains("INFO"))
                        .to("activemq:queue:Infomsgs").
                        when(body().contains("WARN"))
                        .to("activemq:queue:Warnmsgs")
                        .when(body().contains("ERROR"))
                        .to("activemq:queue:Errormsgs").
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

