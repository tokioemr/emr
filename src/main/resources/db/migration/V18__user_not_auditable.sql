ALTER TABLE users
    DROP CONSTRAINT fk_users_on_created_by;

ALTER TABLE users
    DROP CONSTRAINT fk_users_on_updated_by;

ALTER TABLE users
    DROP COLUMN created_at;

ALTER TABLE users
    DROP COLUMN created_by;

ALTER TABLE users
    DROP COLUMN deleted_at;

ALTER TABLE users
    DROP COLUMN updated_at;

ALTER TABLE users
    DROP COLUMN updated_by;
