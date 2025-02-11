//redelivery-policy rules
package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder3exc extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).onRedelivery(new ReDeliveryProcessor()).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

        from("file://inputFolder?noop=true").process(new MyProcessor3exc()).to("file://outputFolder");
        from("file://outputFolder?noop=true").to("file://outputFolder1");

    }}


