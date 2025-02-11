package com.camexamples.example;

import org.slf4j.*;
import org.apache.camel.builder.*;
import org.apache.camel.main.Main;
import org.apache.camel.component.dataset.*;

public class WriteMsgsLocalFS extends Main {
    static Logger LOG = LoggerFactory.getLogger(WriteMsgsLocalFS.class);
    public static void main(String[] args) throws Exception {
        WriteMsgsLocalFS main = new WriteMsgsLocalFS();
        //main.enableHangupSupport();
        main.addRouteBuilder(createRouteBuilder());
        main.bind("sampleGenerator", createDataSet());
        main.run(args);
    }
    static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("dataset://sampleGenerator")
                        .to("file://output_box");
            }
        };
    }
    static DataSet createDataSet() {
        return new SimpleDataSet();
    }
}

