
package com.snapworld.snapworld_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class AfricaTalkingSmsService {

    @Value("${africastalking.username:}")
    private String username;

    @Value("${africastalking.api-key:}")
    private String apiKey;

    private final HttpClient client = HttpClient.newHttpClient();

    public void sendSms(String phoneNumber, String message)
            throws IOException, InterruptedException {

        if (username.isBlank() || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Africa's Talking credentials are not configured."
            );
        }

        String form = "username=" + encode(username)
                + "&to=" + encode(phoneNumber)
                + "&message=" + encode(message)
                + "&enqueue=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.africastalking.com/version1/messaging"
                ))
                .header("Accept", "application/json")
                .header("Content-Type",
                        "application/x-www-form-urlencoded")
                .header("apiKey", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "SMS request failed. HTTP status: "
                            + response.statusCode()
            );
        }
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
