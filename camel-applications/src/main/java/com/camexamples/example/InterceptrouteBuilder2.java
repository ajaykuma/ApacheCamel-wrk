package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class InterceptrouteBuilder2 extends RouteBuilder {

	int count;

	@Override
	public void configure() throws Exception {
	
		interceptFrom("file*")
		.process(new Processor() {
			public void process(Exchange exchange) {
				
				count++;
				System.out.println("interceptor called " + count + " times " + exchange.getIn().getBody());

			}
		});

		from("file:inbox?noop=true").split().tokenize("\n").to("activemq:queue:que1");
		from("activemq:queue:que1").to("activemq:queue:que2");
		from("activemq:queue:que2").to("activemq:queue:que3");
		
	}

}

