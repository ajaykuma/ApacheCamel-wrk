package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		com.camexamples.example.Employee employee = exchange.getIn().getBody(com.camexamples.example.Employee.class);
		// Modify the Employee Name
		employee.setEmpName("PeterJohnson");
		exchange.getIn().setBody(employee);
	}

}
