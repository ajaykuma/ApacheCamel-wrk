package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ReDeliveryProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("test1");
    }

}