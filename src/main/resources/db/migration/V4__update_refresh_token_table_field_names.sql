ALTER TABLE refresh_tokens
    ADD user_id BIGINT;

ALTER TABLE refresh_tokens
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE refresh_tokens
    DROP COLUMN "user";

ALTER TABLE refresh_tokens
    ADD CONSTRAINT FK_REFRESH_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
