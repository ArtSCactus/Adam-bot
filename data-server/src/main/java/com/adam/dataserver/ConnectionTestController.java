package com.adam.dataserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@RestController
public class ConnectionTestController {
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.status(200).body("Ready to serve.");
    }
}
