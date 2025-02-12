package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;

public class ConsumeIdem2 extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:input_box")
				.idempotentConsumer(header("CamelFileName"), MemoryIdempotentRepository.memoryIdempotentRepository(200))
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("This file is being processed the first time -- "
								+ exchange.getIn().getHeader("CamelFileName"));
					}
				}).to("file:outputFolder");
	}

}
