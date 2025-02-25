//sending file/file-content to ActiveMQ Queue
//to be fixed, changes to be done in POM to be able to use ActiveMQ Component
//error-java.io.IOException: Unknown data type: 47
package com.camexamples.example;
//import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
//import javax.jms.ConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;

public class FileToActiveMQ1copy {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
        //creating a connection to activeMQ server
        //using ActiveMQ component instead of JMS
        // Define ActiveMQ Component
       context.addComponent("activemq",ActiveMQComponent.activeMQComponent("tcp://127.0.0.1:8161"));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //define endpoints
                /*note here that since,we are reading from folder,it works fine but for a specific file
                we will have to give file with specifics as shows in other examples
                 */
                from("file://inbox")
                        .to("activemq:queue:my_queue_test");
            }
        });
        //starting the context & running infinitely
        //while(true)
        //    context.start();
        //
        // Start the context with sleep time
        context.start();
        Thread.sleep(5 * 60 * 1000);
        context.stop();
    }
}
