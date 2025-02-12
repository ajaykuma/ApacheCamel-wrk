package com.camexamples.aggrexampl;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class MyAggregationStrategy implements AggregationStrategy {
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        String oldMessageBody = oldExchange.getIn().getBody(String.class);
        String newMessageBody = newExchange.getIn().getBody(String.class);
        String combinedBody = oldMessageBody +" " + newMessageBody;
        oldExchange.getIn().setBody(combinedBody);
        return oldExchange;
    }
}