//redelivery-policy rules
package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

        from("file://inputFolder?noop=true").process(new MyProcessor2()).to("file://outputFolder");

    }}


