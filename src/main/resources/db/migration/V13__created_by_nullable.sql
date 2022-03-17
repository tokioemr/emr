ALTER TABLE users_permissions
    ADD CONSTRAINT pk_users_permissions PRIMARY KEY (user_id, permissions_id);

ALTER TABLE currencies
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_categories
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_feature_groups
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_feature_values
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_features
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_prices
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE product_tags
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE products
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE users
    ALTER COLUMN created_by DROP NOT NULL;
