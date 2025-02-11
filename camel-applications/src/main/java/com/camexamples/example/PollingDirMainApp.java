package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class PollingDirMainApp {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        //point to your routebuilder class
        PollingDir pollingdir = new PollingDir();

        try{
            context.addRoutes(pollingdir);

                context.start();

            }
        catch (Exception e){
            e.printStackTrace();
        }
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        //Use instance of template
        String message = consumerTemplate.receiveBody("seda:end",String.class);
        System.out.println(message);


        //Thread.sleep(5 * 60 * 1000);
        //context.stop();


    }
}
