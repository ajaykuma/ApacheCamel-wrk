EIP Patterns:
-------------------------------------
Splitter:

The Splitter from the EIP patterns allows users split a content into a number of pieces and process them individually.

For example:
		//define endpoints
                from("file://input_box?noop=True")
			    .split().tokenize("\n")
                        .to("activemq:queue:my_queue");

In this route, content in input_box i.e. file is split based on token i.e. "\n" for new line and 
sends the content to activemq queues.
#Add a log file or point to a log file and test the splitter to send data to ActiveMQ queue.

refer:FileToActiveMQ2.java
refer:FileToActiveMQ3.java
input_box/test1.txt contains
[
This is a sample content for application 2.
Lets see if it gets transformed.
]

Add '.streaming' after '.tokenize' for the route picks
up the files and then splits the file content line by line. This is done by using the Splitter EIP 
in streaming mode. The streaming mode ensures that the entire file isn’t loaded into memory; 
instead it’s loaded piece by piece on demand, which ensures low memory usage.
Add .loadBalance().roundRobin() if sending data to multiple queues as shown in next example
----------------------------------
Content Based Router:

The Content-Based Router inspects the content and routes it to another channel based on the content.

For example: 
				//define endpoints
        		from("file://input_box/?fileName=test2.txt&charset=utf-8&noop=true")
                //if just reading from folder
                //from("file://input_box?noop=True")
                //logging using header of file
                .log("Starting to process file: ${header.CamelFileName}")
                .split().tokenize("\n")
        		.to("direct:test");
                
                //Adding logging and setting header for the messages
                //Use headers to access dynamic properties during your route by .getHeader("myHeader")
                from("direct:test")
                	.log("Routing as per content in ${file:name}")
                	.setHeader("myHeader", constant("10Feb2025"))                                
                .to("direct:test1");
                
                //Content Based routing- Route the message based on the token it contains.
                from("direct:test1")
                	.choice().
                        when(body().contains("queue1-content"))
                    .to("activemq:queue:Queue1").
                        when(body().contains("queue2-content"))
                    .to("activemq:queue:Queue2")
                        .when(body().contains("queue3-content"))
                    .to("activemq:queue:Queue3").
                    otherwise()
                .to("activemq:queue:my_queue_extras");
            }

Here in this route, content is read from a specific file,inspected and then as per rules defined routed 
to different queues of ActiveMQ. The queues get created and contain lines from the file.

input_box/test2.txt contains
[
queue1-content > This is a sample content for queue1
queue2-content > This is a sample content for queue2
queue3-content > This is a sample content for queue3
]
refer:FileToActiveMQ4.java
-------------------------------------------
Message Filter:

A Message Filter is a special form of a Content-Based Router. It examines the message content and passes the
message to another channel if the message content matches certain criteria. Otherwise, it discards the message.

For example:
		//define endpoints
                from("file://input_box/?fileName=test2.txt&charset=utf-8&noop=true")
                        //if just reading from folder
                        //from("file://input_box?noop=True")
                        .split().tokenize("\n")
                .to("direct:test");
                //Content Based routing- Route the message by filtering it based on criteria or discard it.
                from("direct:test")
                        .filter(body().contains("queue1-content"))
                .to("activemq:queue:Queue1");

Here in this route, content is inspected and then content is routed if it passes mentioned criteria or ignored..

Camel provides a timer component to trigger routes at specific intervals.
                from("timer://myTimer?period=2000")
                .to("log:TimerRoute?level=INFO&showAll=true");
the "from" method with the "timer://myTimer?period=2000" endpoint triggers the route every 2 seconds. 
It logs a message to the console using the "log" endpoint.
refer:FileToActiveMQ5.java
----------------------------------------------
Recipient List:
A Content-Based Router allows us to route a message to the correct system based on message content.
Say deciding the name of the queue at run time depending on the content ..

For example:

	 //define endpoints
      from("file://C:/Users/Win10/workspace2/camel-applications/input_box/?fileName=test2.txt&charset=utf-8&noop=true")
                        //if just reading from folder
                        //from("file:input_box?noop=True")
                        .split().tokenize("\n").to("direct:test");
                from("direct:test")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String recipient = exchange.getIn().getBody().toString();
                                String recipientQueue="activemq:queue:"+recipient;
                                exchange.getIn().setHeader("queue", recipientQueue);
                            }
                        }).recipientList(header("queue"));

Here in this route we use processor,which has exchange object that takes the messages and stores it in recipient
then recipient queue is created and added to header variable queue.
thus based on content queue will be created.

refer:FileToActiveMQ6.java
----------------------------------------------
Wire Tap
refer:
Wire Tap allows you to route messages to a separate location while they are being forwarded to the 
ultimate destination.

	//define endpoints
	from("file://input_box").split().tokenize("\n").to("direct:test1");
        
        from("direct:test1")
        //Wire Tap:Suppose get some error so send seperate copies of the message to 
        //DeadLetter queue and also to direct:test2 
        .wireTap("activemq:queue:DeadLetterQueue")
        .to("direct:test2");
        
        from("direct:test2")
        .process(new Processor() {
            public void process(Exchange arg0) throws Exception {
		System.out.println(arg0.getIn().getBody().toString());
              
            }

refer:FileToActiveMQ7.java
----------------------------------------------
Concurrency: 
With concurrency, you can achieve higher performance; by executing in parallel, you can get more work done in
less time.
Camel processes multiple messages concurrently in Camel routes, and it leverages
the concurrency features from Java.

Consuming from ActiveMQ
			context.addRoutes(new RouteBuilder() {
            @Override
            	public void configure() throws Exception {
            	//define endpoints
            	from("activemq:queue:Queue1")
            	.to("seda:end?concurrentConsumers=5");
            	from("seda:end")
            	//.to("log:msgs?level=DEBUG")
            	.to("log:mylogs?showAll=true&multiline=true")
            	.to("file:Output");
            	
Scenario 1:
we will see data is consumed from seda and shows up in console as per consumerTemplate,
nothing saved on local disk.

Scenario 2:
enable concurrentConsumers from seda:end and re run.
.to("seda:end?concurrentConsumers=2");
Note** Here we consume from activemq to seda and then from seda. Once a msg is consumed from activemq it doesnt 
exist in the queue any more. Here we use SEDA which is for asynchronous invocation.

refer: ConsumeActiveMQ8.java
-------------------------------------
Consuming from --file/MQ

					from("file://inbox") //add log file here which contains more data
                	.split() //optional we can give .split(body().tokenize("\n"))
                	.tokenize("\n")
                	.streaming()
                	//.parallelProcessing()
                	.to("log:processing?level=INFO")
                .to("direct:logdata")
                .end()
                .log("Done processing file: ${header.CamelFileName}");
            	from("direct:logdata")
                .to("log:mylogs?showAll=true&multiline=true")
                .to("file:Output-new");

--Test run without parallel processing
If it complains about no consumer, add a consumerTemplate.
It might show all processing happening in 2 threads
thread #1: for processing msgs
thread #2: for InflightExchange and cleaning up.

--editing other configuration settings
--for default shutdown timeout is 300 sec
 //context.getShutdownStrategy().setTimeout(10);
 --in route
            	    .routeId("myCustomRouteId1")
            	    .autoStartup(false)
            	    //By default, Apache Camel starts up routes in a non-deterministic order.  
            	    //to control the startup order
            	    //.startupOrder(1)
                	.shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                    .delay(1000)
                    .split() //optional we can give .split(body().tokenize("\n"))
                	.tokenize("\n")
                	.streaming()

ShutdownRunningTask.CompleteCurrentTaskOnly                	
(Default) Usually, a route operates on just a single message at a time, so you can safely shut down 
the route after the current task has completed.
ShutdownRunningTask.CompleteAllTasks
Specify this option in order to shut down batch consumers gracefully.

--Test with parallel processing
refer: ConsumeActiveMQ9.java
----------------------------------------------
Multicasting:
Idempotence:
--------------------------------------------
refer: Consumeidem1.java
refer: Consumeidem2.java



