package com.camexamples.example;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp2exc {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2exc.xml");
        ctx.start();
        System.out.println("Application context started");
        try {
            Thread.sleep(5 * 60 * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.stop();
        ctx.close();
    }
}