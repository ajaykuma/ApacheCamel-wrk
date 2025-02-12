package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class InterceptrouteBuilder3 extends RouteBuilder {

	int count;

	@Override
	public void configure() throws Exception {

		interceptSendToEndpoint("activemq:queue:Testq2").process(new Processor() {
			public void process(Exchange exchange) {
				count++;
				System.out.println("interceptor called "+ count +" times "+exchange.getIn().getBody());
				
			}
		});
		
		from("file:inbox?noop=true").split().tokenize("\n").to("activemq:queue:Testq1");
		from("activemq:queue:Testq1").to("activemq:queue:Testq2");
	}

}