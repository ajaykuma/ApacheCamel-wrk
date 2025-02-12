package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.bind.JAXBContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;



public class CamelXMLToJSONTransf {

    public static void main(String[] args) throws Exception{
        CamelContext ctx = new DefaultCamelContext();

        ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                // XML Data Format
                JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
                JAXBContext con = JAXBContext.newInstance(Employee.class);
                xmlDataFormat.setContext(con);

                // JSON Data Format
                JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

                // First component i.e  file and input directory and Second component i.e file and output directory
                //.unmarshal().allowNullBody().jaxb()
                from("file:inputFolder").doTry().unmarshal(xmlDataFormat).process(new MyProcessor()).marshal(jsonDataFormat).

                        to("file:OuputFolder").doCatch(Exception.class).process(new Processor() {

                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                });
                
            }
        });

        // Start context
        ctx.start();
        Thread.sleep(5 * 60 * 1000);
        ctx.stop();

    }
}
