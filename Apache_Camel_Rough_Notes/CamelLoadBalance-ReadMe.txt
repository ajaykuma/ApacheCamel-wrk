# About Project

- Read some data from input file line by line
- Send the messages read from each line into different ActiveMQ queues based on round robin fashion using Camel load balancer


// Create below file in Input folder

learn 
camel
load balancer
using 
examples

// First include the dependencies into pom.xml . Refer to sample pom.xml file available

	
// Creating camel context 

	CamelContext ctx = new DefaultCamelContext();
	

// Create JMS Connection factory instance using ActiveMQ URL as argument. Then add it to the context 

	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        //define the jms component
    ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

// adding routes to the context . Takes Route builder as an argument

	ctx.addRoutes(new RouteBuilder()) {		

	//Override the configure method thus add 'throws Exception'
		}

		
//as of now

    public static void main(String[] args) throws Exception{

        CamelContext ctx = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        //define the jms component
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        });
    }

// Now lets add the component 


	 public void configure() throws Exception {
			
			// Using the file component we read the input data. 
			// Then use the Load Balance Integration Pattern using Round Robin policy to send data across multiple queues evenly
			from("file:C:/inbox?noop=true").split().tokenize("\n").loadBalance().roundRobin()
			.to("jms:queue:test1").to("jms:queue:test2").to("jms:queue:test3");		
			
            }


// Note: Other Load Balance Policies are as mentioned below

    Random, Sticky , Topic, Failover, Weighted Round-Robin, Weighted Random 

// Finally start the context and stop after a while.
	
	ctx.start();
	Thread.sleep(5 * 60 * 1000);
	ctx.stop();


	
	
::::::Final Code ::::::

package com.camelexamples.LoadBalance;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

    public static void main(String[] args) throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        //define the jms component
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
			
			from("file:C:/inbox?noop=true").split().tokenize("\n").loadBalance().roundRobin()
			.to("jms:queue:test1").to("jms:queue:test2").to("jms:queue:test3");		
			
            }
        });
		
	// Start the context
    ctx.start();
    Thread.sleep(5 * 60 * 1000);
    ctx.stop();
       
    }
}


// Run the application

--------------------------------------------------------------------------------------------