ActiveMQ n Apache Camel Integration:

Setup ActiveMQ and use Apache camel to send msgs/content to ActiveMQ
---------------------
https://activemq.apache.org/components/classic/download/

Stable - Supported: Actively supported and recommended for production use. 
This version receives regular updates, including new features, security patches, and bug fixes.
5.18.x	Javax JMS 1.1	Javax JMS 1.1/Jakarta JMS 2	11+	5.3.39	Log4j 2.24.1/Slf4j 2.0.13	
Jetty 9.4.56.v20240826	Stable - Supported	5.18.6	5.18.7	Dec 24

Download Java 17 for windows
Setup environment variables

Download: Windows	apache-activemq-5.18.6-bin.zip (Needs Java 11+)
 Directory of C:\apache-activemq-5.18.6

C:\apache-activemq-5.18.6>dir/p
 Volume in drive C has no label.
 Volume Serial Number is 1C18-400C

 Directory of C:\apache-activemq-5.18.6

09/26/2024  12:48 PM    <DIR>          .
09/26/2024  12:48 PM        10,832,985 activemq-all-5.18.6.jar
09/26/2024  12:48 PM    <DIR>          bin
09/26/2024  12:48 PM    <DIR>          conf
09/26/2024  12:48 PM    <DIR>          data
09/26/2024  12:48 PM    <DIR>          docs
09/26/2024  12:48 PM    <DIR>          examples
09/26/2024  12:48 PM    <DIR>          lib
09/26/2024  12:48 PM            41,200 LICENSE
09/26/2024  12:48 PM             3,418 NOTICE
09/26/2024  12:48 PM             2,674 README.txt
09/26/2024  12:48 PM    <DIR>          webapps
09/26/2024  12:48 PM    <DIR>          webapps-demo
               4 File(s)     10,880,277 bytes
               9 Dir(s)  240,848,453,632 bytes free

C:\apache-activemq-5.18.6>cd bin

 Directory of C:\apache-activemq-5.18.6\bin

09/26/2024  12:48 PM    <DIR>          .
09/26/2024  12:48 PM    <DIR>          ..
09/26/2024  12:48 PM            27,443 activemq
09/26/2024  12:48 PM             4,284 activemq-admin.bat
09/26/2024  12:48 PM             4,494 activemq.bat
09/26/2024  12:48 PM            16,483 activemq.jar
09/26/2024  12:48 PM    <DIR>          win32
09/26/2024  12:48 PM    <DIR>          win64
09/26/2024  12:48 PM            83,820 wrapper.jar
               5 File(s)        136,524 bytes
               4 Dir(s)  240,833,781,760 bytes free

#start activemq
C:\apache-activemq-5.18.6\bin>activemq start
Java Runtime: Oracle Corporation 17.0.12 C:\Java\jdk-17
  Heap sizes: current=1048576k  free=1042942k  max=1048576k
    JVM args: -Dcom.sun.management.jmxremote -Xms1G -Xmx1G -Djava.util.logging.config.file=logging.properties -Djava.security.auth.login.config=C:\apache-activemq-5.18.6\bin\..\conf\login.config -Dactivemq.classpath=C:\apache-activemq-5.18.6\bin\..\conf;C:\apache-activemq-5.18.6\bin\../conf;C:\apache-activemq-5.18.6\bin\../conf; -Dactivemq.home=C:\apache-activemq-5.18.6\bin\.. -Dactivemq.base=C:\apache-activemq-5.18.6\bin\.. -Dactivemq.conf=C:\apache-activemq-5.18.6\bin\..\conf -Dactivemq.data=C:\apache-activemq-5.18.6\bin\..\data -Djolokia.conf=file:C:\\apache-activemq-5.18.6\\bin\\..\\conf\\jolokia-access.xml -Djava.io.tmpdir=C:\apache-activemq-5.18.6\bin\..\data\tmp
Extensions classpath:
  [C:\apache-activemq-5.18.6\bin\..\lib,C:\apache-activemq-5.18.6\bin\..\lib\camel,C:\apache-activemq-5.18.6\bin\..\lib\optional,C:\apache-activemq-5.18.6\bin\..\lib\web,C:\apache-activemq-5.18.6\bin\..\lib\extra]
ACTIVEMQ_HOME: C:\apache-activemq-5.18.6\bin\..
ACTIVEMQ_BASE: C:\apache-activemq-5.18.6\bin\..
ACTIVEMQ_CONF: C:\apache-activemq-5.18.6\bin\..\conf
ACTIVEMQ_DATA: C:\apache-activemq-5.18.6\bin\..\data
Loading message broker from: xbean:activemq.xml
 INFO | Using Persistence Adapter: KahaDBPersistenceAdapter[C:\apache-activemq-5.18.6\bin\..\data\kahadb]
 INFO | Starting Persistence Adapter: KahaDBPersistenceAdapter[C:\apache-activemq-5.18.6\bin\..\data\kahadb]
 INFO | Starting KahaDBStore
 INFO | Opening MessageDatabase
 INFO | Page File: C:\apache-activemq-5.18.6\bin\..\data\kahadb\db.data. Recovering pageFile free list due to prior unclean shutdown..
 INFO | KahaDB is version 7
 INFO | Page File: C:\apache-activemq-5.18.6\bin\..\data\kahadb\db.data. Recovered pageFile free list of size: 0
 INFO | Starting Temp Data Store
 INFO | PListStore:[C:\apache-activemq-5.18.6\bin\..\data\localhost\tmp_storage] started
 INFO | Starting Job Scheduler Store
 INFO | Persistence Adapter successfully started
 INFO | Apache ActiveMQ 5.18.6 (localhost, ID:DESKTOP-92P6D6V-49838-1739002922314-0:1) is starting
 INFO | Listening for connections at: tcp://DESKTOP-92P6D6V:61616?maximumConnections=1000&wireFormat.maxFrameSize=104857600
 INFO | Connector openwire started
 INFO | Listening for connections at: amqp://DESKTOP-92P6D6V:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600
 INFO | Connector amqp started
 INFO | Listening for connections at: stomp://DESKTOP-92P6D6V:61613?maximumConnections=1000&wireFormat.maxFrameSize=104857600
 INFO | Connector stomp started
 INFO | Listening for connections at: mqtt://DESKTOP-92P6D6V:1883?maximumConnections=1000&wireFormat.maxFrameSize=104857600
 INFO | Connector mqtt started
 INFO | Starting Jetty server
 INFO | Creating Jetty connector
 WARN | ServletContext@o.e.j.s.ServletContextHandler@7b306b9f{/,null,STARTING} has uncovered http methods for path: /
 INFO | Listening for connections at ws://DESKTOP-92P6D6V:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600
 INFO | Connector ws started
 INFO | Apache ActiveMQ 5.18.6 (localhost, ID:DESKTOP-92P6D6V-49838-1739002922314-0:1) started
 INFO | For help or more information please see: http://activemq.apache.org
 INFO | ActiveMQ WebConsole available at http://127.0.0.1:8161/
 INFO | ActiveMQ Jolokia REST API available at http://127.0.0.1:8161/api/jolokia/
------------------------------
Login : admin and admin
http://127.0.0.1:8161/index.html

Click on 'Manage ActiveMQ broker'

Queues > give a name: my_queue <create>

Name  	Number Of Pending Messages  	Number Of Consumers  	Messages Enqueued  	Messages Dequeued  	Views  					Operations  
my_queue	0	0	0	0								Browse Active Consumers Active Producers 	Send To Purge Delete Pause

============
Create Camel Application to write to 'existing/newly created queue'

These are the dependencies that need to be added to your pom.xml
 <dependency>
          <groupId>org.apache.activemq</groupId>
          <artifactId>activemq-camel</artifactId>
          <version>5.18.0</version>
      </dependency>
      <dependency>
          <groupId>org.apache.activemq</groupId>
          <artifactId>activemq-client</artifactId>
          <version>5.18.0</version>
      </dependency>
      <dependency>
          <groupId>org.apache.activemq</groupId>
          <artifactId>activemq-pool</artifactId>
          <version>5.18.0</version>
      </dependency>
      <dependency>
          <groupId>org.apache.activemq</groupId>
          <artifactId>activemq-broker</artifactId>
          <version>5.18.0</version>
      </dependency>

refer: pom.xml of the project for all updated dependencies

Go to https://mvnrepository.com/ and search for
ActiveMQ :: Camel » 5.18.0 and other dependencies as mentioned above
and add them to your project's pom.xml.
------------------
Optionally[recommended] for any of examples:

We create a class to construct a Camel Route. A Route is like a instruction definition to Camel on how 
to move your messages from one point to another. 
We will create SimpleRouteBuilder.java file that will move files from <file:path> to ActiveMQ. 
We use the JMS component which we will define in the main class later.

#SimpleRouteBuilder.java

package com.camexamples.example;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {
	//configure route for jms component
    @Override
    public void configure() throws Exception {
        from("file:inbox?noop=true").split().tokenize("\n")
        .to("jms:queue:javainuse");
    }

}
        
When Camel application is started, it creates a CamelContext object that contains the definition of the Route to 
be started.
Thus we create default camel context and load the route created in SimpleRouteBuilder.
#MainApp.java
package com.camexamples.example;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

    public static void main(String[] args) {
        SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
        CamelContext ctx = new DefaultCamelContext();

//configure jms component        
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:8161");
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        try {
            ctx.addRoutes(routeBuilder);
            ctx.start();
            Thread.sleep(5 * 60 * 1000);
            ctx.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
----------------------------------
#Application sending file/file-content to ActiveMQ Queue
refer:FileToActiveMQ1.java
Note**
//Note** Before running application, make sure to create some files in above mentioned folder i.e. inbox
//If queue is not created, it will be created on execution
//Once run, click on queues and check if it shows --my_queue > click on check messages in queue.
//optionally, try creating a new file in input folder and check while context is running, if new msg is created in Queue.
//Once Application terminated, it would show 'uptime 5 minutes'
------------------
#Application to transform the content before copying to ActiveMQ
refer:FileToActiveMQ2.java
Note**
//Note** Before running application, make sure to create some files in above mentioned folder i.e. input_box
//If queue is not created, it will be created on execution
//Once run, click on queues and check if it shows --my_queue2 > click on check messages in queue.
//Once Application terminated, it would show 'uptime 5 minutes'
//Also notice , data file still remain in input folder.
//Now, run application again, by changing output queue to 'my_queue3' and disabling transformation and enabling
//to use split,tokenize and stream.

//Before commenting out transformation
/*
public void configure() throws Exception {
    //define endpoints
    from("file://input_box/?fileName=test1.txt&charset=utf-8&noop=true")
            .process(new Processor() {
            public void process(Exchange exchange) throws Exception {
            String message = exchange.getIn().getBody(String.class);
            message = message.toUpperCase() + " <--msges have to be altered";
            exchange.getMessage().setBody(message);
            }
            }) //method that takes processor as an argument
            .to("activemq:queue:my_queue2");
*/

//After commenting out transformation
/*public void configure() throws Exception {
    //define endpoints
    from("file://input_box/?fileName=test1.txt&charset=utf-8&noop=true")
            .split().tokenize("\n").streaming()
            .to("activemq:queue:my_queue3");
*/
------------------
#Application to use an Enterprise Integration Pattern(EIP) to split the file line by line 
and then send it to the queue.
refer:FileToActiveMQ2.java
refer: Understanding_EIP-ReadMe
------------------
#Application to use an Enterprise Integration Pattern(EIP) to split the file line by line 
and then send the contents to multiple Queues in a round robin fashion.(Load balancing)
refer:FileToActiveMQ3.java
refer: Understanding_EIP-ReadMe
Note**
//Note** Before running application, make sure to create some files in above mentioned folder i.e. inbox
//If queue(s) is not created, it will be created on execution
//Once run, click on queues and check if it shows --name of queue > click on check messages in queue.
//Once Application terminated, it would show 'uptime 5 minutes'
//Also notice , if data file still remains in input folder or no?
file: newfile.txt > [this is line 1
this is line 2
this is line 3 ]
--------------------------
refer:FileToActiveMQ4.java
refer: Understanding_EIP-ReadMe
-----------------------
refer:FileToActiveMQ5.java
refer: Understanding_EIP-ReadMe
------------------------
refer:FileToActiveMQ6.java
refer: Understanding_EIP-ReadMe
------------------------
refer:FileToActiveMQ7.java
refer: Understanding_EIP-ReadMe
--------------------------
#Application to Consume from ActiveMQ
refer:ConsumeActiveMQ.java
--------------------------
#Application to Send an object to ActiveMQ queue
refer:ObjectToActiveMQ9.java
======================================================
