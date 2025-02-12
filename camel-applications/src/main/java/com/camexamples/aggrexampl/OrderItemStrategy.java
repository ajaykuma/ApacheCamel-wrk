/*using AggregationStrategy for our purpose.
On the first invocation of the aggregate method,  the oldExchange parameter is null.
The reason is that we have not aggregated anything yet. So it’s only the newExchange that has a value.
*/

package com.camexamples.aggrexampl;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class OrderItemStrategy implements AggregationStrategy {

    //@Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {

            Item newItem= newExchange.getIn().getBody(Item.class);
            System.out.println("Aggregate first item: " + newItem);

            Order currentOrder = new Order();
            currentOrder.setId("ORD"+System.currentTimeMillis());
            List currentItems = new ArrayList();

            currentItems.add(newItem);
            currentOrder.setItems(currentItems);
            currentOrder.setTotalPrice(newItem.getPrice());

            newExchange.getIn().setBody(currentOrder);

            // the first time we aggregate we only have the new exchange,
            // so we just return it
            return newExchange;
        }

        Order order = oldExchange.getIn().getBody(Order.class);
        Item newItem= newExchange.getIn().getBody(Item.class);

        System.out.println("Aggregate old items: " + order);
        System.out.println("Aggregate new item: " + newItem);

        order.getItems().add(newItem);

        double totalPrice = order.getTotalPrice() + newItem.getPrice();
        order.setTotalPrice(totalPrice);

        // return old as this is the one that has all the orders gathered until now
        return oldExchange;
    }


}
