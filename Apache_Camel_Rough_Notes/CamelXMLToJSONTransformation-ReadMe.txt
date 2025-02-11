# About Project

- Read some XML data
- Unmarshall to a Java object
- Modify the data 
- Send the modified data to ActiMQ queue in JSON format

// Create below XML file in Input folder

filename: employee
<employee>
<empName>Example</empName>
<empId>1</empId>
</employee>

// First include the dependencies into pom.xml . Refer to sample pom.xml file available

// We will be UnMarshalling and Marshalling object of Employee. Create the Employee class

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

	private String empName;
	private int empId;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	
// Creating camel context 

	CamelContext ctx = new DefaultCamelContext();

// adding routes to the context . Takes Route builder as an argument

	ctx.addRoutes(new RouteBuilder()) {		

	//Override the configure method thus add 'throws Exception'
		}

------------------------		
//as of now

    public static void main(String[] args) throws Exception{

        CamelContext ctx = new DefaultCamelContext();
        ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

            }
        });
    }

-------------------------------

// Now lets add the JSON and XML data format instances for UnMarshalling and Marshalling. 

		// XML Data Format using Jaxb to unmarhsall XML data 
				JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
				
				// Creating JAXB context instance with Employee object as the argument
				JAXBContext con = JAXBContext.newInstance(Employee.class); 
				xmlDataFormat.setContext(con);

		// JSON Data Format using Jackson to marshall data into JSON format. Takes Employee object as the argument
		
				JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
				

// Add the Components. Here we try to read data from Input Folder using file component. Unmarshall the XML data . 
//Then call MyProcessor class to modify the data where we change Employee Name. Once modified we marshall the data 
//into JSON format and send to Output folder. 
// Here we have used try and catch to handle any exception while Marshalling and UnMarshalling of data.
//In case of any exception the details are printed.
		
from("file:C:/inputFolder").doTry().unmarshal(xmlDataFormat).process(new MyProcessor()).marshal(jsonDataFormat).

					to("jms:queue:javainuse").doCatch(Exception.class).process(new Processor() {

					public void process(Exchange exchange) throws Exception {
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
					System.out.println(cause);
							}
						});
				

// Now create a MyProcessor Java class to modify the XML unmarhsall data implementing Processor interface.


	public class MyProcessor implements Processor {

			public void process(Exchange exchange) throws Exception {
				Employee employee = exchange.getIn().getBody(Employee.class);
				// Changing the Employee Name to Ajay
				employee.setEmpName("Ajay");
				exchange.getIn().setBody(employee);
			}

	}

// Finally start the context and stop after a while.
	
	ctx.start();
	Thread.sleep(5 * 60 * 1000);
	ctx.stop();

	
::::::Final Code ::::::

MainApp.java :

package com.camelexamples.XMLtoJSONexample;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;


public class MainApp {

	public static void main(String[] args) throws Exception{
		CamelContext ctx = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				// XML Data Format
				JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
				JAXBContext con = JAXBContext.newInstance(Employee.class);
				xmlDataFormat.setContext(con);

				// JSON Data Format
				JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

				from("file:C:/inputFolder").doTry().unmarshal(xmlDataFormat).process(new MyProcessor()).marshal(jsonDataFormat).

						to("jms:queue:javainuse").doCatch(Exception.class).process(new Processor() {

							public void process(Exchange exchange) throws Exception {
								Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
								System.out.println(cause);
							}
						});
			}
		});

			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();

	}
}

--------------
Employee.java :

package com.camelexamples.XMLtoJSONexample;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

	private String empName;
	private int empId;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}
}

------------------
MyProcessor.java :

package com.camelexamples.XMLtoJSONexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Employee employee = exchange.getIn().getBody(Employee.class);
		employee.setEmpName("JavaInUse Rocks");
		exchange.getIn().setBody(employee);
	}

}



// Run the application

-----------------------------------------------------------------------------------------