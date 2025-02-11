package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdconsWithdirectvm {

    public static void main(String[] args) throws Exception{

        CamelContext context1 = new DefaultCamelContext();
        context1.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start") //First component i.e. direct endpoint and 'start'
                //or using timer
                //from("timer://simpleTimer?period=1000")
                //.setBody(simple("msgs from timer at ${header.firedTime}"))
                        .to("direct-vm:test"); //Second component is seda and 'end'
            }
        });
        
        CamelContext context2 = new DefaultCamelContext();
        context2.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct-vm:test") //First component i.e. direct endpoint and 'start'
                        .to("seda:end"); //Second component is seda and 'end'
                		//.log("msg received"); //Second component is seda and 'end'
            }
        });
        context1.start();
        context2.start();
        //Create producer Template
        ProducerTemplate producerTemplate = context1.createProducerTemplate();
        //Use instance of template
        producerTemplate.sendBody("direct:start","This is first msg"); //takes endpoint as argument and object that needs to be sent

        //Create consumer Template
        ConsumerTemplate consumerTemplate = context2.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);
        Thread.sleep(5 * 60 * 1000);
        //context1.stop();
        //context2.stop();

    }
}
