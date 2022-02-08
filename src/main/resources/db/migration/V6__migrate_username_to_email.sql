ALTER TABLE users
    ADD email VARCHAR(255);

UPDATE users SET email = 'admin@example.com' WHERE username = 'admin';

ALTER TABLE users
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    DROP COLUMN username;
