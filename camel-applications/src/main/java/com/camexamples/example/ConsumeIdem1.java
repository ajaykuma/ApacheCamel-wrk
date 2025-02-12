//Consume from ActiveMQ queue
package com.camexamples.example;
import org.apache.camel.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ShutdownRunningTask;
//import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;



public class ConsumeIdem1 {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
 /*       //creating a connection to activeMQ server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");

        //adding jms component to camel context
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory)); */
        context.getShutdownStrategy().setTimeout(60);
        
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            //define endpoints
            	from("file://input_box/?fileName=yarn-hdu-resourcemanager-um2.log&charset=utf-8&noop=true")
            	    .routeId("myCustomRouteId1")
            	    //.shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                	.split() //optional we can give .split(body().tokenize("\n"))
                	.tokenize("\n")
                	//.streaming()
                	//.parallelProcessing()
                	.to("log:processing?level=INFO")
                .to("seda:a")
                .end()
                .log("Done processing file: ${header.CamelFileName}");
            from("seda:a")
			.idempotentConsumer(header("CamelFileName"), MemoryIdempotentRepository.memoryIdempotentRepository(200))
			.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("This file is being processed the first time -- "
								+ exchange.getIn().getHeader("CamelFileName"));

					}
				})
			.to("seda:b");
            }
        });
        context.start();
        Thread.sleep(1 * 60 * 1000);
        //ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //String message = consumerTemplate.receiveBody("seda:a",String.class);
        //System.out.println("---------------" + java.time.LocalDateTime.now() + " " + message);
        context.stop();
    }
   }

