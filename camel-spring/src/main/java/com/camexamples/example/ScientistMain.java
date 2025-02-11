package com.camexamples.example;

import com.camexamples.example.Scientist;
import java.util.Date;
import java.util.List;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScientistMain {

    public static void main(String[] args) {
        try {
            ApplicationContext springCtx = new ClassPathXmlApplicationContext("applicationContextSc.xml");

            CamelContext context = springCtx.getBean("scientistContext", CamelContext.class);

            context.start();

            ProducerTemplate producerTemplate = context.createProducerTemplate();
            // Insert sc 1
            Scientist sc1 = getScientist1();
            String resp = producerTemplate.requestBody("direct:insert", sc1, String.class);

            // Insert sc 2
            Scientist sc2 = getScientist2();
            resp = producerTemplate.requestBody("direct:insert", sc2, String.class);
            // Get Scientist of inserted scientists
            List<Scientist> scientists = producerTemplate.requestBody("direct:select", null, List.class);
            System.out.println("scientists:" + scientists);

        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    private static Scientist getScientist1() {

        Scientist sc = new Scientist();

        sc.setscId("scId1");
        sc.setscName("sc1");
        return sc;

    }

    private static Scientist getScientist2() {

        Scientist sc = new Scientist();

        sc.setscId("empId2");
        sc.setscName("emp2");
        return sc;

    }
}
