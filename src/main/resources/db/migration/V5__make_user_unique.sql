ALTER TABLE refresh_tokens
    ADD CONSTRAINT uc_refresh_tokens_user UNIQUE (user_id);
