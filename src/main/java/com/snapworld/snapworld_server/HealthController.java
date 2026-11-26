
package com.snapworld.snapworld_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
                "application", "SNAPWORLD",
                "status", "ONLINE",
                "message", "SNAPWORLD server is running!"
        );
    }
}
