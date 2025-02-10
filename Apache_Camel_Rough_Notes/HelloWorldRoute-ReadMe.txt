#Setup Maven project
Group Id: com.camexamples
Artifact Id: camel-applications

----------
#Get maven repository
from https://mvnrepository.com/ search for 'camel core' > Camel :: Core > click and take latest(3.4.2) or in my case
2.22.0 and add the dependency to pom.xml

<dependencies>
       <!-- https://mvnrepository.com/artifact/org.apache.camel/camel-core -->
       <dependency>
    		<groupId>org.apache.camel</groupId>
    		<artifactId>camel-core</artifactId>
    		<version>2.22.0</version>
	   </dependency>
   </dependencies>

#About code
//Creating camel context i.e.
//creating camel context instance
CamelContext context = new DefaultCamelContext(); //import the relevant packages 

//adding routes to the context 
context.addRoutes(builder);	//takes route builder as an argument*
//Thus we have to create a route builder instance*

----- 
//Thus create one more class HelloWorldRoute
//RouteBuilder is an abstract class which has one abstract method so we have to override the abstract method
//now mention in abstract method what it needs to do ie what route has to do,since it is simple helloworld
		// mention what route has to do
		System.out.println("Hello World in Camel");

-----
package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class HelloWorldRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// mention what route has to do
		System.out.println("Hello World in Camel");
		
	}
}
-----

//now in HelloWorld.java replace builder with 'new HelloWorldRoute' in context.addRoutes(builder)
//This method wants us to handle exception thus 'add throws exception'
//Thus 1.we created context
       2. added the router
       3.start the context

Finally this is how it looks
---------
package com.camexamples.example;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class HelloWorld {
    
    public static void main(String[] args) throws Exception {
		 
    	CamelContext context = new DefaultCamelContext();
        context.addRoutes(new HelloWorldRoute());	
        context.start();
	}
}
run the application
=========================================


