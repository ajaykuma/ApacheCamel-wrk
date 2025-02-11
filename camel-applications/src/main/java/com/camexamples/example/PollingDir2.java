package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PollingDir2 extends RouteBuilder {
    static Logger LOG = LoggerFactory.getLogger(PollingDir2.class);
    @Override
    public void configure() throws Exception{
                //from("file://input_box?delay=1000")

                //to ensure proper handling
                //from("file://input_box?delay=1000&preMove=staging&move=.completed")

                //configure file endpoint to only read the polling folder when a signal/read-marker exists
                //from("file://input_box?delay=1000&preMove=staging&move=.completed&doneFileName=ReadyFile.txt")

                //using splitting and streaming
                from("file://input_box?delay=1000&move=.completed")
                        //.split(body().tokenize("\n"))
                        //create a file that contains sample content for ex: "then is example"

                        .transform((body().regexReplaceAll("is", "not")))
                        //try commenting processor and check the result
                        .process(new Processor() {
                          public void process(Exchange msg) throws Exception {
                          String message = msg.getIn().getBody(String.class);
                          msg.getMessage().setBody(message.toUpperCase());
                          }})
                        .to("seda:end");
                //try wireTap and write to some folder and see what it does.
                //.writeTap("file://outputTest").to("seda:end");
                            }
}
