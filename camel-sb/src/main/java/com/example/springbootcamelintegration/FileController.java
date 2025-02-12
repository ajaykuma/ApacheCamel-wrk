package com.example.springbootcamelintegration;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileController class provides a REST endpoint to trigger the Camel route.
 */
@RestController
public class FileController {

    @Autowired
    private ProducerTemplate producerTemplate; // Inject ProducerTemplate to send messages to Camel routes

    /**
     * This endpoint starts the file move process by triggering the Camel route.
     *
     * @return String message indicating the start of the process
     */
    @GetMapping("/startFileMove")
    public String startFileMove() {
        producerTemplate.sendBody("direct:fileMoveRoute", "Sample content"); // Trigger the Camel route with sample content
        return "File move process started!";
    }
}
