package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class IdemBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:inputFolder").to("file:outputFolder");
	}

}