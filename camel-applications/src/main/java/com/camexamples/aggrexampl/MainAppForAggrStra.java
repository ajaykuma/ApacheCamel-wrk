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
        template.sendBodyAndHeader("direct:start", "twenty-one", "Id", 10);
        template.sendBodyAndHeader("direct:start", "twenty-two", "Id", 11);
        template.sendBodyAndHeader("direct:start", "twenty-three", "Id", 21);
        template.sendBodyAndHeader("direct:start", "twenty-four", "Id", 21);
        template.sendBodyAndHeader("direct:start", "twenty-five", "Id", 11);
    }
}

