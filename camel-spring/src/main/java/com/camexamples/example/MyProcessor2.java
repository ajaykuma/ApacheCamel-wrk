package com.camexamples.example;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor2 implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("Testing spring");
    }

}
