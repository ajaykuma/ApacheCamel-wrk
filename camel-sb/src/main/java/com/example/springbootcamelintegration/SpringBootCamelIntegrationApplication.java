package com.example.springbootcamelintegration;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootCamelIntegrationApplication class serves as the entry point of the Spring Boot application.
 */
@SpringBootApplication
public class SpringBootCamelIntegrationApplication {
	 CamelContext context = new DefaultCamelContext();

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelIntegrationApplication.class, args); // Start the Spring Boot application
    }
}
