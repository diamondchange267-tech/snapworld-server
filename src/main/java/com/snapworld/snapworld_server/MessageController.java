
package com.snapworld.snapworld_server;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final JdbcTemplate jdbcTemplate;

    public MessageController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public MessageResponse sendMessage(
            @Valid @RequestBody MessageRequest request) {

        String sql = """
            INSERT INTO messages
                (sender_username, receiver_username, message_text)
            VALUES (?, ?, ?)
            RETURNING id, sender_username, receiver_username,
                      message_text, sent_at
            """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new MessageResponse(
                        rs.getLong("id"),
                        rs.getString("sender_username"),
                        rs.getString("receiver_username"),
                        rs.getString("message_text"),
                        rs.getObject("sent_at", OffsetDateTime.class)
                ),
                request.senderUsername(),
                request.receiverUsername(),
                request.messageText()
        );
    }

    @GetMapping
    public List<MessageResponse> getMessages(
            @RequestParam String user1,
            @RequestParam String user2) {

        String sql = """
            SELECT id, sender_username, receiver_username,
                   message_text, sent_at
            FROM messages
            WHERE (sender_username = ? AND receiver_username = ?)
               OR (sender_username = ? AND receiver_username = ?)
            ORDER BY sent_at ASC, id ASC
            LIMIT 200
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new MessageResponse(
                        rs.getLong("id"),
                        rs.getString("sender_username"),
                        rs.getString("receiver_username"),
                        rs.getString("message_text"),
                        rs.getObject("sent_at", OffsetDateTime.class)
                ),
                user1, user2, user2, user1
        );
    }

    public record MessageRequest(
            @NotBlank @Size(max = 100) String senderUsername,
            @NotBlank @Size(max = 100) String receiverUsername,
            @NotBlank @Size(max = 5000) String messageText
    ) {}

    public record MessageResponse(
            Long id,
            String senderUsername,
            String receiverUsername,
            String messageText,
            OffsetDateTime sentAt
    ) {}
}
