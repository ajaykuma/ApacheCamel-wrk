#intercept
Controlling when to intercept using a predicate

intercept().when(body().contains("sass")).to("mock:intercepted");

Stop routing after being intercepted
if the message body contains the word 'sass' we want to log and stop
intercept().when(body().contains("sass"))
  .to("log:test")
  .stop(); // stop continue routing

##interceptFrom
The interceptFrom is for intercepting any incoming Exchange, in any route (it intercepts all the from EIPs)

>to do some custom behavior for received Exchanges. 
You can provide a specific uri for a given Endpoint then it only applies for that route.

#to log all the incoming messages
interceptFrom()
  .to("log:incoming");

other routes:
from().to()
from().to()

#to only apply a specific endpoint, such as all jms endpoints
interceptFrom("jms*")
  .to("log:incoming");

from("jms:queue:order")
  .to("bean:processOrder");

from("file:inbox")
  .to("ftp:someserver/backup")

##interceptSendToEndpoint
when Apache Camel is sending a message to an endpoint.

This can be used to do some custom processing before the message is sent to the intended destination.
The interceptor can also be configured to not send to the destination (skip) which means the message is detoured instead.
A Predicate can also be used to control when to intercept, which has been previously covered.


example:
we want to intercept when a message is being sent to kafka:
interceptSendToEndpoint("kafka*")
  .to("log:beforeKafka");

from("jms:queue:order")
  .to("bean:validateOrder")
  .to("file:result")
  .to("kafka:order");

to process the message after it has been sent to the intended destination
interceptSendToEndpoint("kafka*")
  .to("log:beforeKafka")
  .afterUri("bean:afterKafka");

from("jms:queue:order")
  .to("bean:validateOrder")
  .to("file:result")
  .to("kafka:order");

to intercept and skip sending messages to a specific endpoint.
interceptSendToEndpoint("kafka*").skipSendToOriginalEndpoint()
  .to("mock:kafka");

---------------------------------
--create routebuilder:
--create mainapp: InterceptMainapp.java

refer : 
--------------
Intercept that intercepts each and every processing step while routing an Exchange in the route : InterceptrouteBuilder1.java
InterceptFrom that intercepts incoming Exchange in the route					: InterceptrouteBuilder2.java
InterceptSendToEndpoint that intercepts when an Exchange is about to be sent to the given Endpoint : InterceptrouteBuilder3.java



