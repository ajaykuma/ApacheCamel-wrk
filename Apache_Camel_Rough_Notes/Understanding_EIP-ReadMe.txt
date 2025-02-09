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
----------------------------------
Content Based Router:

The Content-Based Router inspects the content and routes it to another channel based on the content.

For example: 
		//define endpoints
        from("file://input_box/?fileName=test2.txt&charset=utf-8&noop=true")
                //if just reading from folder
                //from("file://input_box?noop=True")
                .split().tokenize("\n").to("direct:test");
                //Content Based routing- Route the message based on the token it contains.
                from("direct:test")
                .choice().
                        when(body().contains("queue1-content"))
                        .to("activemq:queue:Queue1").
                        when(body().contains("queue2-content"))
                        .to("activemq:queue:Queue2")
                        .when(body().contains("queue3-content"))
                        .to("activemq:queue:Queue3").
                        otherwise()
                .to("activemq:queue:my_queue_extras");

Here in this route, content is read from a specific file,inspected and then as per rules defined routed to different
queues of ActiveMQ. The queues get created and contain lines from the file

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
                        .split().tokenize("\n").to("direct:test");
                //Content Based routing- Route the message by filtering it based on criteria or discard it.
                from("direct:test")
                        .filter(body().contains("queue1-content"))
                        .to("activemq:queue:Queue1");

Here in this route, content is inspected and then content is routed if it passes mentioned criteria or ignored..

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





