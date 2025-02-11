package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
public class SimpleRouteBuilder1 extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file://input_box?noop=true").process(new MyProcessor1()).to("file://outputFolder");
    }}
