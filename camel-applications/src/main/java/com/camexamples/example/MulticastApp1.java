package com.camexamples.example;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//multicasting sce1

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class MulticastApp1 {

  public static void main(String[] args) throws Exception {
      CamelContext context = new DefaultCamelContext();
      ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
      context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

      context.addRoutes(new RouteBuilder() {
          @Override
          public void configure() throws Exception {
        	  ExecutorService executor = Executors.newFixedThreadPool(15);
              from("file://inbox")
                      //.split()
                      //.tokenize("\n")
                      //.streaming()
              			.multicast().stopOnException()
              			.parallelProcessing().executorService(executor)
              			.to("log:processing?level=INFO")
                      //.loadBalance().roundRobin()
              .to("activemq:queue:Queue1","activemq:queue:Queue2","activemq:queue:Queue3");
              //.to("activemq:queue:Queue2")
              //.to("activemq:queue:Queue3");

          }
      });

      // Start the context
      context.start();
      Thread.sleep(5 * 60 * 1000);
      context.stop();

  }
}