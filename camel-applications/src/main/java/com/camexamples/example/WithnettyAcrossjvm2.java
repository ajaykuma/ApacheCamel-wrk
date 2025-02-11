package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WithnettyAcrossjvm2 {

    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("netty4:tcp://127.0.0.1:9999?transferExchange=true") 
                //or using timer
                //from("timer://simpleTimer?period=1000")
                //.setBody(simple("msgs from timer at ${header.firedTime}"))
                        .log("msg received");
            }
        });
        
        
        context.start();
        Thread.sleep(10 * 60 * 1000);
        context.stop();


    }
}
