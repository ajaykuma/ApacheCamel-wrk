package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).log("Received body ").handled(true);

        from("file://inputFolder?noop=true").process(new MyProcessor()).to("file://outputFolder");

        from("file://input_box?noop=true").process(new MyProcessor()).to("file://outputFolder");
    }}


