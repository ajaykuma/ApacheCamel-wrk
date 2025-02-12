/*we have used the createProducerTemplate method of the created camel context’s instance,
so that we can send the data to the route for processing. In our case, then we send request body
with three different parameters like Router name, Order instance, and Class type. */

package com.camexamples.aggrexampl;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
        try {
           ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "camel-context.xml");

            CamelContext context = applicationContext.getBean("orderCtx",
                    CamelContext.class);

            ProducerTemplate producerTemplate = context.createProducerTemplate();

            List items = new ArrayList();
            items.add(new Item("1", "Camel in Action book", "Book"));
            items.add(new Item("2", "Apple IPhone8", "Phone"));

            Order myOrder = new Order();
            myOrder.setItems(items);

            Order respOrder = producerTemplate.requestBody(
                    "direct:processOrder", myOrder, Order.class);

            System.out.println("resp order:"+respOrder);

            context.stop();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}