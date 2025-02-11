package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor3exc implements Processor {

    public void process(Exchange exchange) throws Exception {

        String a = exchange.getIn().getBody(String.class);
        System.out.println("hello " + a);
        if(a.equalsIgnoreCase("newtest"))
          throw new CamelCustomException();

    }

}
