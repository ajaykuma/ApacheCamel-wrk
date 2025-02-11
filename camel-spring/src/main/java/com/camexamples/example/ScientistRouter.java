package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class ScientistRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:insert").log("Inserted new Scientist").beanRef("scientistMapper", "getMap")
                .to("sqlComponent:{{sql.insertScientist}}");

        from("direct:select").to("sqlComponent:{{sql.getAllScientists}}")
                .beanRef("scientistMapper", "readScientists").log("${body}");
    }

}