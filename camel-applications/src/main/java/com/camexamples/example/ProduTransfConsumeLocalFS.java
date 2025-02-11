package com.camexamples.example;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.UnitOfWork;

public class ProduTransfConsumeLocalFS {

    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start") //First component i.e. direct endpoint and 'start'
                        //try splitting and streaming,it will work on only first msg, needs to be fixed..
                        //.split().tokenize(" ").streaming()

                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("I am Testing the processor");
                                //Note processor has a process method that has Exchange object using which we can do a get
                                String message = exchange.getIn().getBody(String.class);
                                message = message.toUpperCase() + " <--msg has be altered";
                                //setting back the object as exchange object for next endpoint to receive it
                                //exchange.getOut().setBody(message);
                                exchange.getMessage().setBody(message);
                            }
                        }) //method that takes processor as an argument
                        .to("seda:end"); //Second component is seda and 'end'
            }
        });

        context.start();


        //Create producer Template
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        //Use instance of template
        producerTemplate.sendBody("direct:start","hey this is camel"); //takes endpoint as argument and object that needs to be sent

        //Create consumer Template
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);

        Thread.sleep(5 * 60 * 1000);
        context.stop();
    }
}
