#Routing With Java DSL
To define a route with Java DSL we will first need to create a DefaultCamelContext instance. After that, 
we need to extend RouteBuilder class and implement the configure method which will contain route flow:

private static final long DURATION_MILIS = 10000;
private static final String SOURCE_FOLDER = "src/test/source-folder";
private static final String DESTINATION_FOLDER 
  = "src/test/destination-folder";
 
@Test
public void moveFolderContentJavaDSLTest() throws Exception {
    CamelContext camelContext = new DefaultCamelContext();
    camelContext.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER + "?delete=true").process(
          new FileProcessor()).to("file://" + DESTINATION_FOLDER);
      }
    });
    camelContext.start();
    Thread.sleep(DURATION_MILIS);
    camelContext.stop();
}

The configure method can be read like this: read files from the source folder, processes them with FileProcessor 
and send the result to a destination folder. Setting delete=true means the file will be deleted from source folder 
after it is processed successfully.

In order to start Camel, we need to call start method on CamelContext. Thread.sleep is invoked in order to 
allow Camel the time necessary to move the files from one folder to another.

FileProcessor implements Processor interface and contains single process method which contains logic for 
modifying file names:

public class FileProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        String originalFileName = (String) exchange.getIn().getHeader(
          Exchange.FILE_NAME, String.class);
 
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
          "yyyy-MM-dd HH-mm-ss");
        String changedFileName = dateFormat.format(date) + originalFileName;
        exchange.getIn().setHeader(Exchange.FILE_NAME, changedFileName);
    }
}

In order to retrieve file name, we have to retrieve an incoming message from an exchange and access 
its header. Similar to that, to modify file name, we have to update message header.