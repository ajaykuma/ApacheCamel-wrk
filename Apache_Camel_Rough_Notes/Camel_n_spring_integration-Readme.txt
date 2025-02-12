//Integrating Java DSL and Spring
//creating SimpleRouteBuilder but calling it using spring

Setup dependencies in pom.xml and build
---------------
 <dependencies>
    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-core</artifactId>
        <version>2.13.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-spring</artifactId>
        <version>2.13.0</version>
    </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>2.13.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.13.3</version>
        </dependency>

    </dependencies>
------------

//Create camel processor:

package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
    }

}

refer: MyProcessor1.java
---------------------------
//Create the SimpleRouteBuilder class that extends RouteBuilder 
and configures the camel route.
package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;
import com.camexamples.example.MyProcessor;

public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://inputFolder?noop=true").process(new MyProcessor()).to("file://outputFolder");
    }

}
refer: SimpleRouteBuilder1.java
---------------------------------
//Create the application context that calls the Java DSL RouteBuilder class using the routeBuilder

>src>main>resources>applicationContext1.xml

<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:camel="http://camel.apache.org/schema/spring"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd          
		http://camel.apache.org/schema/spring 
		http://camel.apache.org/schema/spring/camel-spring.xsd">

	<bean id="routeBuilder" class="com.camexamples.example.SimpleRouteBuilder1" />

	<camelContext xmlns="http://camel.apache.org/schema/spring">
		<routeBuilder ref="routeBuilder" />
	</camelContext>
</beans>
refer: applicationContext1.xml
------------------
//the above application context file will be used to call our route ie SimpleRouteBuilder1

//a bean will be created for our routebuilder class,
[	<bean id="routeBuilder" class="com.camexamples.route.SimpleRouteBuilder1" />]

this bean will be called with the routebuilder
[<routeBuilder ref="routeBuilder" />]
----------------------

finally load the context file to start the JAVA DSL camel route
package com.camexamples.example;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
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

Thus route will automatically get loaded whenever this appl context will be started.
refer MainApp1.java
------------------------

Also add log4j2.xml under src/main/java/resources to take care of logging

<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">

    <!-- Author:  Crunchify.com  -->
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
        </Console>

        <RollingFile name="RollingFile" filename="log/MyAppsTest.log"
                     filepattern="${logPath}/%d{YYYYMMddHHmmss}-fargo.log">
            <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
            <Policies>
                <SizeBasedTriggeringPolicy size="100 MB" />
            </Policies>
            <DefaultRolloverStrategy max="20" />
        </RollingFile>

    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console" />
            <AppenderRef ref="RollingFile" />
        </Root>
    </Loggers>
</Configuration>

===================
Run MainApp1.java to test
===================
Working with Exceptions:

Adding exception handling code for builder class.
The exception handling for Apache camel can be implemented in 2 ways.
Using Do Try block
Using OnException block

Example:
//Using Do Try block
This approach is similar to the Java try catch block. 
So the thrown exception will be immediately caught and the message wont keep on retrying.

package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class SimpleRouteBuilder2exc extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://inputFolder?noop=true").doTry().process(new MyProcessor2exc()).to("file://outputFolder")
                .doCatch(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).log("Received body ");
//having one more route will create a problem say something like this
//from("file://input_box?noop=true").process(new MyProcessor()).to("file://outputFolder");
//Note**doTry and DoCatch disadvantage is --configured for a single route
    }

}

-----------------------------------
Example:
#Thus Using OnException block
The OnException block is written as a separate block from the routes.
This applies to all the routes.

//using ReDeliveryProcessor and redelivery-policy rules
package com.camexamples.example;

import com.camexamples.exception.CamelCustomException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder3exc extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).onRedelivery(new ReDeliveryProcessor()).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

        from("file://inputFolder?noop=true").process(new MyProcessor3exc()).to("file://outputFolder");
        from("file://outputFolder?noop=true").to("file://outputFolder1");

    }}

Refer the project: 
-----------------------------------
#[no retry rules or special exception handling]
MainApp1--applicationContext1.xml--SimpleRouteBuilder1--MyProcessor1

#[doTry() and doCatch() example in SimpleRouteBuilderexc]
MainApp2exc--applicationContext2exc.xml--SimpleRouteBuilder2exc--MyProcessor2exc

#[with redeliveryPolicyProfile in applicationContext2 file,
onException Block used + myProcessor2 ]
MainApp2--applicationContext2.xml--SimpleRouteBuilder2--MyProcessor2

#[with redeliveryPolicyProfile in applicationContext3exc file,
onException Block used + myProcessor3exc +ReDeliveryProcessor]
MainApp3exc--applicationContext3exc.xml--SimpleRouteBuilder3exc--MyProcessor3exc
                                                      --ReDeliveryProcessor(if failure)

Also refer to:
camel-spring
CamelCustomException.java

src>main>resources
applicationContext*.xml files.

----------------
Camel & Spring boot :
Create a 'spring-boot-camel-integration' project using spring initializer

package: com.example.springbootcamelintegration

configure application.properties in src/main/resources
# Application name
spring.application.name=spring-boot-camel-integration

# Log level for Apache Camel
logging.level.org.apache.camel=INFO

# Log file name
logging.file.name=application.log

in src/main/java > com.example.springbootcamelintegration
--has SpringBootCamelIntegrationApplication.java by default
----------
package com.example.springbootcamelintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootCamelIntegrationApplication class serves as the entry point of the Spring Boot application.
 */
@SpringBootApplication
public class SpringBootCamelIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelIntegrationApplication.class, args); // Start the Spring Boot application
    }
}
-----------
--within package create initial Aggregator route/FileMoveRoute.java
The FileMoveRoute class extends RouteBuilder and overrides the configure() method to define the route.
The route starts from the direct:fileMoveRoute endpoint, 
logs the file name being processed, and moves the file to the specified destination directory.
--
package com.example.springbootcamelintegration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * FileMoveRoute class defines a simple Camel route that moves files
 * from a source directory to a destination directory.
 */
@Component
public class FileMoveRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Define the route starting from a direct component
       from("direct:fileMoveRoute")
       .log("Processing file: ${file:name}") // Log the name of the file being processed
       .to("file:Testing Folder?fileName=${file:name.noext}.bak"); // Move the file to the destination directory and rename it with a .bak extension
    }
}

--

Create the Controller to Trigger the Camel Route
The FileController class is annotated with @RestController, making it a Spring MVC controller.
It uses ProducerTemplate to send messages to the Camel route.
The startFileMove() method triggers the Camel route by sending a message to the direct:fileMoveRoute endpoint.

--
package com.example.springbootcamelintegration;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileController class provides a REST endpoint to trigger the Camel route.
 */
@RestController
public class FileController {

    @Autowired
    private ProducerTemplate producerTemplate; // Inject ProducerTemplate to send messages to Camel routes

    /**
     * This endpoint starts the file move process by triggering the Camel route.
     *
     * @return String message indicating the start of the process
     */
    @GetMapping("/startFileMove")
    public String startFileMove() {
        producerTemplate.sendBody("direct:fileMoveRoute", "Sample content"); // Trigger the Camel route with sample content
        return "File move process started!";
    }
}

---------------
Run the main application
Access browser : http://localhost:8080/startFileMove (to check)

We can use tool such as Postman to send GET request
After sending the request, check the destination directory to verify that the file has been moved and renamed with a .bak extension.
----------------


