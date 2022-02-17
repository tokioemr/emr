START TRANSACTION;

WITH new_permissions AS (
    INSERT INTO user_permissions (name)
    VALUES ('CREATE_USERS'), ('VIEW_USERS'), ('VIEW_REPORTS')
    RETURNING id
)
INSERT INTO users_permissions (user_id, permissions_id)
SELECT 1 as user_id, new_permissions.id as permissions_id FROM new_permissions;

COMMIT;
