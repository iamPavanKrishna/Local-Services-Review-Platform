ALTER TABLE user_preference
    ADD subscribed BIT(1) NULL;

ALTER TABLE user_preference
    MODIFY subscribed BIT(1) NOT NULL;

ALTER TABLE user_preference
    DROP COLUMN is_subscribed;