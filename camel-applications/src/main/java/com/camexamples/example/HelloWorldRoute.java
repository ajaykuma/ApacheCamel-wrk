package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class HelloWorldRoute extends RouteBuilder{

	//@Override
	public void configure() throws Exception {
		// mention what route has to do
		System.out.println("Hello World in Camel- testing");
		
	}
}
