package com.example.springbootcamelintegration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * FileMoveRoute class defines a simple Camel route that moves files
 * from a source directory to a destination directory.
 */
@Component
public class FileMoveRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Define the route starting from a direct component
       from("direct:fileMoveRoute")
       .log("Processing file: ${file:name}") // Log the name of the file being processed
       .to("file:Testing Folder?fileName=${file:name.noext}.bak"); // Move the file to the destination directory and rename it with a .bak extension
    }
}

