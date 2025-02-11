#About code

Producer to send some data to endpoint
Consumer to consume the data from endpoint

//Creating camel context i.e.
//creating camel context instance
CamelContext context = new DefaultCamelContext(); //import the relevant packages 

//adding routes to the context 
context.addRoutes(builder);	//takes route builder as an argument*
//Thus
content.addRoutes(new RouteBuilder()) {

//Override the configure method thus add 'throws Exception'
}


//as of now

    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        });
    }


//Now let's components
public void configure() throws Exception {
            from("direct:start") //First component i.e. direct endpoint and 'start'
                    .to("seda:end"); //Second component is seda and 'end'
            }

//Create producer & consumer template
        //Create producer Template
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        //Use instance of template
        producerTemplate.sendBody("direct:start","This is first msg"); //takes endpoint as argument and object that needs to be sent

        //Create consumer Template
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);

//finally add context.start()

Final Code :
package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdAndConsumeExample {

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
        producerTemplate.sendBody("direct:start","This is first msg"); //takes endpoint as argument and object that needs to be sent

        //Create consumer Template
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);

    }
}

run the application
=========================================


