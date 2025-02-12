/*In our example, we are using two different routes. The first route is accepting the input as the instance of
Order Model Class, then separate the Items data from it and send the split message for processing.
The output is then sent to the second route.
The second route then processes the data (setting the price) based on the type of the item.*/

package com.camexamples.aggrexampl;

import org.apache.camel.builder.RouteBuilder;

public class OrderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:processOrder")
                .split(body().method("getItems"), new OrderItemStrategy())
                // each split  message is send to this bean to process it
                .to("direct:processItem")
                .end();


        from("direct:processItem")
                .choice()
                .when(body().method("getType").isEqualTo("Book"))
                .to("bean:itemService?method=processBook").
                otherwise()
                .to("bean:itemService?method=processPhone");
    }
}