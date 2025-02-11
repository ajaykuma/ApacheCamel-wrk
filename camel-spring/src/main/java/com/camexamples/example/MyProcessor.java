package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        //for 3
        String a = exchange.getIn().getBody(String.class);
        System.out.println("hello " + a);
        if(a.equalsIgnoreCase("test"))
            throw new CamelCustomException();
        //uncomment for 1 and 2
        //System.out.println("Exception Thrown");
        //throw new CamelCustomException();
    }

}
