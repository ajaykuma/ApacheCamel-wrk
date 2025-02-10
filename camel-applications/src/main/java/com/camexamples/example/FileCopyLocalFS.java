package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopyLocalFS {

    public static void main(String[] args) throws Exception{

        CamelContext context = new DefaultCamelContext();
        //adding routes to context
        context.addRoutes(new RouteBuilder() {
            
        	/*copy data from one location/file/folder to another */
        	@Override
            public void configure() throws Exception {
                from("file:input_box?noop=true")
                        .to("file:output_box");
            
            }
        });
        context.start();
        Thread.sleep(2 * 60 * 1000);
                context.stop();
    }}
