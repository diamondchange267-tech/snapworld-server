
package com.snapworld.snapworld_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @GetMapping("/")
    public String home() {
        return "SNAPWORLD SERVER IS ONLINE!";
    }

    @GetMapping("/api/status")
    public String status() {
        return "SNAPWORLD API IS WORKING!";
    }
}

