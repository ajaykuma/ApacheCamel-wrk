package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class HelloWorldMainApp {
    
    public static void main(String[] args) throws Exception {
		 
    	CamelContext context = new DefaultCamelContext();
        context.addRoutes(new HelloWorldRoute());
        
        context.start();
        //Thread.sleep(1 * 60 * 1000);
        //context.stop();
	}
}
