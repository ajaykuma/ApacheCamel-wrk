Apache Camel Redelivery policy
----------------------
A redelivery policy defines rules when Camel Error Handler perform redelivery attempts. 
For example you can setup rules that state how many times to try redelivery, and the delay in between attempts,etc.

Scenario: we will want to retry messages again (in case of failure) before the exception is handled.

First let's update applicationContext.xml

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:camel="http://camel.apache.org/schema/spring"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://camel.apache.org/schema/spring
		http://camel.apache.org/schema/spring/camel-spring.xsd">

    <bean id="routeBuilder" class="com.camexamples.example.SimpleRouteBuilder2" />

    <camelContext xmlns="http://camel.apache.org/schema/spring">
        <routeBuilder ref="routeBuilder" />
		<redeliveryPolicyProfile id="testRedeliveryPolicyProfile"
			retryAttemptedLogLevel="WARN" maximumRedeliveries="5"
			redeliveryDelay="5" />
    </camelContext>
</beans>

//Now configure the defined redelivery policy in our route.

//redelivery-policy rules
i.e. in SimpleRouteBuilder2 (a copy of SimpleRouteBuilder)
so this:
}).log("Received body ").handled(true);

becomes this:

        }).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

Make sure applicationContext.xml is updated for this new route
<bean id="routeBuilder" class="com.camexamples.example.SimpleRouteBuilder2" />
Test it!
-----------------------
//we may want in some cases to correct some data if the exception is thrown first time and before the 
//first redelivery attempt is made. For this make use of onRedelivery processor. Define the 
//Redelivery processor which corrects the data before the first redelivery attempt.

package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ReDeliveryProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("test1");
    }

}

//now in route we configure the onRedelivery processor.
i.e. in SimpleRouteBuilder3exc (a copy of SimpleRouteBuilder2)

so this becomes:
}).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

this:
 }).onRedelivery(new RedeliveryProcessor()).redeliveryPolicyRef("testRedeliveryPolicyProfile")
            .log("Received body ").handled(true);


Make sure applicationContext.xml is updated for this new route
<bean id="routeBuilder" class="com.camexamples.example.SimpleRouteBuilder3exc" />
Test it!


