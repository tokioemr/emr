START TRANSACTION;

with adminPermissionIdResult as (
    INSERT INTO user_permissions(name)
        VALUES ('CREATE_TAGS'), ('REMOVE_TAGS')
        RETURNING id
), adminUserIdResult as (
    SELECT id FROM users WHERE email = 'admin@example.com'
)
INSERT INTO users_permissions (user_id, permissions_id)
SELECT (SELECT id FROM adminUserIdResult) as id, id as permissions_id FROM adminPermissionIdResult;

COMMIT;
