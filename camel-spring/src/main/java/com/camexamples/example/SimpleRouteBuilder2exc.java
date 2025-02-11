package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class SimpleRouteBuilder2exc extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://inputFolder?noop=true").doTry().process(new MyProcessor2exc()).to("file://outputFolder")
                .doCatch(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).log("Received body ");
//having one more route will create a problem say something like this
//from("file://input_box?noop=true").process(new MyProcessor()).to("file://outputFolder");
        //Note**doTry and DoCatch disadvantage is --configured for a single route
    }

}

