ALTER TABLE product_tags
    ADD deleted_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE product_images
    ADD extension VARCHAR(5);

ALTER TABLE product_images
    ALTER COLUMN extension SET NOT NULL;
