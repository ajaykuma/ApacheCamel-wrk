package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class InterceptrouteBuilder1 extends RouteBuilder {

	int count;

	@Override
	public void configure() throws Exception {

		intercept().when(body().contains("data")).log("datafound");
		//no more routing when data is found
		//intercept to log endpoint..
		/*.process(new Processor() {
			public void process(Exchange exchange) {
				count++;
				System.out.println("interceptor called " + count + " times " + exchange.getIn().getBody());

			}
		});*/
		from("file:inbox?noop=true").split().tokenize("\n")
		//.parallelProcessing()
		.to("activemq:queue:Que1").to("activemq:queue:Que2");
	}

}