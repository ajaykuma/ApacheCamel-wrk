package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleTransformExampleUsingSpring {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        CamelContext context = SpringCamelContext.springCamelContext(
                appContext, false);
        try {
            context.start();
            ProducerTemplate template = new DefaultProducerTemplate(
                    context);
            template.start();
            template.sendBody("direct:start", "Hello");
        } finally {
            context.stop();
        }
    }

}
