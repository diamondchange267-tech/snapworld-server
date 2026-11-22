
CREATE TABLE IF NOT EXISTS messages (
                                        id BIGSERIAL PRIMARY KEY,
                                        sender_username VARCHAR(100) NOT NULL,
    receiver_username VARCHAR(100) NOT NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_messages_sender_receiver
    ON messages (sender_username, receiver_username, sent_at);

CREATE INDEX IF NOT EXISTS idx_messages_receiver_sender
    ON messages (receiver_username, sender_username, sent_at);
