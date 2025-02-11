package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {
    //configure route for jms component

    public void configure() {
        from("file:inbox?noop=true").split().tokenize("\n")
                .to("activemq:queue:my_queue");
    }

}