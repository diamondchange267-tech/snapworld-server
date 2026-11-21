
package com.snapworld.snapworld_server;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    private final MongoTemplate mongoTemplate;

    public ServerController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/")
    public String home() {
        return "SNAPWORLD SERVER IS ONLINE!";
    }

    @GetMapping("/api/status")
    public String status() {
        return "SNAPWORLD API IS WORKING!";
    }

    @GetMapping("/api/db-check")
    public String dbCheck() {
        mongoTemplate.getDb().runCommand(new Document("ping", 1));
        return "SUCCESS! SNAPWORLD IS CONNECTED TO MONGODB ATLAS!";
    }
}
