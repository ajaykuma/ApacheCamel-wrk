//Using time option, we can let the timer know when we want the first event to be fired. In the pattern we specify
// the custom date pattern to use to set the time. The pattern expected is: yyyy-MM-dd HH:mm:ss or yyyy-MM-dd'T'HH:mm:ss.

package com.camexamples.example;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTimerTimePatternExample {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    Date future = new Date(new Date().getTime() + 1000);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String time = sdf.format(future);

                    fromF("timer://simpleTimer?time=%s&pattern=dd-MM-yyyy HH:mm:ss", time)
                            .setBody(simple("Hello from timer at ${header.firedTime}"))
                            .to("stream:out");
                }
            });
            camelContext.start();
            Thread.sleep(2* 60* 1000);
        } finally {
            camelContext.stop();
        }
    }
}
