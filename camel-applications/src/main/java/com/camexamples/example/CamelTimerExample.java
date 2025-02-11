
package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class CamelTimerExample {

        public static void main(String[] args) throws Exception {
            CamelContext context = new DefaultCamelContext();
            try {
                context.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from("timer://simpleTimer?period=1000")
                                .setBody(simple("msgs from timer at ${header.firedTime}"))
                                .to("stream:out");
                    }
                });
                context.start();
                Thread.sleep(3000);
            } finally {
                context.stop();
            }
        }
    }


