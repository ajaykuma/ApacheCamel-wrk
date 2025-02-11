/*You can also delay the timer, make it to wait for the configured time before the first event is generated
and one can also limit the maximum limit number of fires.
We will set the delay period to 2s and repeatCount to 2 which means the timer will wait for 2 seconds
before it starts generating its first event and it will fire only for two times. */

package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CallTimerOtherOptionsExample {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    fromF("timer://simpleTimer?delay=2s&repeatCount=2")
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