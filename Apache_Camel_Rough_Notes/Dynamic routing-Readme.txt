Dynamic routing..

Define route:

package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("file:C:/inbox?noop=true").split().tokenize("\n").dynamicRouter(method(DynamicRouterBean.class, "route"));

		from("direct:route1").process(new Processor() {
			public void process(Exchange exchange) {
				String body = exchange.getIn().getBody().toString();
				body = body + " in route 1";
				System.out.println(body);
				exchange.getIn().setBody(body);
			}
		});

		from("direct:route2").process(new Processor() {
			public void process(Exchange exchange) {
				String body = exchange.getIn().getBody().toString();
				body = body + " in route 2";
				System.out.println(body);
				exchange.getIn().setBody(body);
			}
		});

		from("direct:route3").process(new Processor() {
			public void process(Exchange exchange) {
				String body = exchange.getIn().getBody().toString();
				body = body + " in route 3";
				exchange.getIn().setBody(body);
				System.out.println(body);
			}
		});
	}
}

-------------
In the below example using the dynamic router we define the sequence of the routes depending on their body message -
Dynamic Router will set a property (Exchange.SLIP_ENDPOINT) on the Exchange which contains the current endpoint as it advanced though the slip. This allows you to know how far we have processed in the slip. (It's a slip because the Dynamic Router implementation is based on top of Routing Slip).

package com.camexamples.example;

import org.apache.camel.Exchange;
import org.apache.camel.Header;

public class DynamicRouterBean {
	public String route(String body, @Header(Exchange.SLIP_ENDPOINT) String previousRoute) {
		if (previousRoute == null) {
			return "direct://route3";
			// check the body content and decide route
		} else if (body.toString().equals("javainuse in route 3")) {
			return "direct://route2";
			// check the body content and decide route
		} else if (body.toString().equals("javainuse in route 3 in route 2")) {
			return "direct://route1";
		} else {
			return null;
		}
	}
}

------------
When Camel is started, it creates a CamelContext object that contains the definition of the Route to be started. Below we create default camel context and load the routes created in SimpleRouteBuilder.

package com.camexamples.example;
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

Run MainApp class as a java application