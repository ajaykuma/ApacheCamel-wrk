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
            //ActiveMQ
            //.from("activemq:queue:Queue6")
            from("file://input_box/?fileName=test2.txt&charset=utf-8&noop=true")
        	    //.routeId("myCustomRouteId1")
        	    //.autoStartup(false)
        	    //By default, Apache Camel starts up routes in a non-deterministic order.  
        	    //to control the startup order
        	    .startupOrder(1)
            	//.shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
            	//.split() //optional we can give .split(body().tokenize("\n"))
            	//.tokenize("\n")
            	//.streaming()
            	//.parallelProcessing()
            .to("log:processing?level=INFO")
            //.to("seda:end").log("Test");
            .to("seda:end").log("Test");
            //.to("seda:end").log("Test");
            //.to("seda:end").threads(5); //not recommended
            from("seda:end?multipleConsumers=true").routeId("myCustomRouteId1")
            .startupOrder(3)
            //.to("log:msgs?level=DEBUG")
            //.to("log:mylogs?showAll=true&multiline=true")
            .to("file://output-new2?fileExist=Append&charset=utf-8");
            from("seda:end?multipleConsumers=true").routeId("myCustomRouteId2")
            .startupOrder(2)
            //.to("log:msgs?level=DEBUG")
            //.to("log:mylogs?showAll=true&multiline=true")
            .to("file://output-new3?fileExist=Append&charset=utf-8");
            }
        });

        context.start();
        
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println("---------------" + java.time.LocalDateTime.now() + "\n " + message);
        Thread.sleep(1 * 60 * 1000);
        context.stop();
    }
}
