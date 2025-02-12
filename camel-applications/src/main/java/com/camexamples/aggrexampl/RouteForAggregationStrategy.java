package com.camexamples.aggrexampl;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

public class RouteForAggregationStrategy extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start").multicast()
        		//.to("activemq:queue:q1","activemq:queue:q2","activemq:queue:q3")
                .log("Sending  with correlation key ")
                .aggregate(header("Id"), new MyAggregationStrategy())
                                .completionSize(10)
                .log("After completion condition,message is sent to out ")
                //.to("mock:output");
                .to("stream:out");
    }


}
