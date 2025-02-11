package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProduConsumeLocalFS {

    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start") //First component i.e. direct endpoint and 'start'
                        .to("seda:end"); //Second component is seda and 'end'
            }
        });

        context.start();

        //Create producer Template
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        //Use instance of template
        producerTemplate.sendBody("direct:start","This is first msg from ProdConsume"); //takes endpoint as argument and object that needs to be sent
        //Create consumer Template
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);
        context.stop();

    }
}
