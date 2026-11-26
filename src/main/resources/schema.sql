
-- ==========================================
-- SNAPWORLD DATABASE
-- PostgreSQL / Railway
-- ==========================================

-- 1. USERS
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username
    ON users (LOWER(username));

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email
    ON users (LOWER(email));

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_phone
    ON users (phone_number);


-- 2. PHONE VERIFICATIONS
CREATE TABLE IF NOT EXISTS phone_verifications (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   phone_number VARCHAR(20) NOT NULL UNIQUE,
    code_hash VARCHAR(255) NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    attempts INTEGER NOT NULL DEFAULT 0,
    verified_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );


-- 3. MESSAGES
CREATE TABLE IF NOT EXISTS messages (
                                        id BIGSERIAL PRIMARY KEY,
                                        sender_username VARCHAR(100) NOT NULL,
    receiver_username VARCHAR(100) NOT NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_messages_sender_receiver
    ON messages (
    sender_username,
    receiver_username,
    sent_at
    );

CREATE INDEX IF NOT EXISTS idx_messages_receiver_sender
    ON messages (
    receiver_username,
    sender_username,
    sent_at
    );


-- ==========================================
-- END OF SNAPWORLD DATABASE
-- ==========================================
