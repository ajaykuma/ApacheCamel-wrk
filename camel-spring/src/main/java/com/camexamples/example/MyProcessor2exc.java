package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor2exc implements Processor {

    public void process(Exchange exchange) throws Exception {

        System.out.println("Exception Thrown");
        throw new CamelCustomException();
    }

}
