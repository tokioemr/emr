START TRANSACTION;

with adminPermissionIdResult as (
    INSERT INTO user_permissions(name)
    VALUES ('ADMIN')
    RETURNING id
), adminUserIdResult as (
    INSERT INTO users(username, password, enabled)
    VALUES ('admin', '$2y$10$fPm0/TpSieAYB5C2S79NLO9p9H9fp8nHQmFtOJu4hQr5QDWyDi2R.', true)
    RETURNING id
)
INSERT INTO users_permissions (user_id, permissions_id) VALUES
((SELECT id FROM adminUserIdResult), (SELECT id FROM adminPermissionIdResult));

COMMIT;
