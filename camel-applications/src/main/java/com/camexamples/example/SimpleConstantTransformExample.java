package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class SimpleConstantTransformExample {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                            //The Log component logs message exchanges to the underlying logging mechanism.
                    .log("Article name is ${body}")
                    .choice()
                        .when().simple("${body} contains 'Camel'")
                            //specify constant strings as a type of expression to transform
                            .transform(constant("Yes"))
                            .to("stream:out")
                        .otherwise()
                            .transform(constant("No"))
                            .to("stream:out")
                    .end();
                }
            });

            context.start();
            ProducerTemplate template = new DefaultProducerTemplate(
                    context);
            template.start();
            template.sendBody("direct:start", "Camel Components");
            template.sendBody("direct:start", "Spring Integration");
        } finally {
            context.stop();
        }
    }
}
