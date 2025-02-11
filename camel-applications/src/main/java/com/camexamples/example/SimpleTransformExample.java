package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class SimpleTransformExample {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .transform(simple("<out>${body}</out>"))
                    .to("stream:out");
                }
            });
            context.start();
            ProducerTemplate template = new DefaultProducerTemplate(
                    context);
            template.start();
            template.sendBody("direct:start", "Testing Transformation");
        } finally {
            context.stop();
        }
    }
}
