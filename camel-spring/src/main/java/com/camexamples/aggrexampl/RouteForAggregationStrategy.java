package com.camexamples.aggrexampl;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

public class RouteForAggregationStrategy extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .log("Sending  with correlation key ")
                .aggregate(header("Id"), new MyAggregationStrategy())
                                .completionSize(4)
                .log("After completion condition,message is sent to out ")
                //.to("mock:output");
                .to("stream:out");
    }


}
