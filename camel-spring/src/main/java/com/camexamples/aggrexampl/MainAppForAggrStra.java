package com.camexamples.aggrexampl;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;


public class MainAppForAggrStra{

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        //point to your routebuilder class
        RouteForAggregationStrategy routeAggr = new RouteForAggregationStrategy();

        try{
            context.addRoutes(routeAggr);
            context.start();
            //Thread.sleep(5 * 60 * 1000);
            //context.stop();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ProducerTemplate template = context.createProducerTemplate();
        //Use instance of template
        template.sendBodyAndHeader("direct:start", "one", "Id", 1);
        template.sendBodyAndHeader("direct:start", "two", "Id", 1);
        template.sendBodyAndHeader("direct:start", "three", "Id", 2);
        template.sendBodyAndHeader("direct:start", "four", "Id", 1);
        template.sendBodyAndHeader("direct:start", "five", "Id", 1);
    }
}

