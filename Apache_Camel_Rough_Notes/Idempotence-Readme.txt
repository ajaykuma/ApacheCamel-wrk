
Idempotent Consumer using Memory IdempotentRepository and File IdempotentRepository
-----------------
POM
<dependencies>
		<dependency>
			<groupId>org.apache.camel</groupId>
			<artifactId>camel-core</artifactId>
			<version>3.0.0-M3</version>
		</dependency>
	</dependencies>

--------------
Routebuilder:
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:C:/inputFolder").to("file:C:/outputFolder");
	}

}
---------------
Mainapp:
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

	public static void main(String[] args) {
		SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
		CamelContext ctx = new DefaultCamelContext();
		try {
			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
-----------
implementing the Idempotent Respository which will filter out duplicate messages. Also we will be using the File Name as the id which is stored in the Idempotent Repository for keeping track of the files processed.

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;

public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:C://inputFolder")
				.idempotentConsumer(header("CamelFileName"), MemoryIdempotentRepository.memoryIdempotentRepository(200))
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("This file is being processed the first time -- "
								+ exchange.getIn().getHeader("CamelFileName"));

					}
				}).to("file:C://outputFolder");
	}

}
----------------
FileIdempotentRepository Implementation
import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;

public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:C://inputFolder")
				.idempotentConsumer(header("CamelFileName"),
						FileIdempotentRepository.fileIdempotentRepository(new File("C://imp//track.txt")))
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("This file is being processed the first time -- "
								+ exchange.getIn().getHeader("CamelFileName"));

					}
				}).to("file:C://outputFolder");
	}
}


