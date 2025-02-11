package com.camexamples.example;

import org.slf4j.*;
import org.apache.camel.*;
import org.apache.camel.builder.*;
import java.io.*;

public class PollingDir extends RouteBuilder {
    //static Logger LOG = LoggerFactory.getLogger(PollingDir.class);
    @Override
    public void configure() throws Exception{
        //from("file://input_box?delay=1000")

                //to ensure proper handling
        //from("file://input_box?delay=1000&preMove=staging&move=.completed")

                //configure file endpoint to only read the polling folder when a signal/read-marker exists
        //from("file://input_box?delay=1000&preMove=staging&move=.completed&doneFileName=ReadyFile.txt")

        from("file://C:/Users/Win10/workspace2/camel-applications/input_box/?fileName=test1.txt&charset=utf-8&noop=true")
                .to("seda:end");


    }
}
