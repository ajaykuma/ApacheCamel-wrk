package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopy2 {

    public static void main(String[] args) throws Exception{

     CamelContext context = new DefaultCamelContext();
     //adding routes to context
     context.addRoutes(new RouteBuilder() {
         @Override
         public void configure() throws Exception {
            from("file:input_box?noop=true")
                    .to("file:output_box");
         }
     });
     while(true)
         context.start();
}}
