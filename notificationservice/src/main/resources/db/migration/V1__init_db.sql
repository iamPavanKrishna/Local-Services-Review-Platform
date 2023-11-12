CREATE TABLE notification
(
    id              BINARY(16)   NOT NULL,
    user_id         BINARY(16)   NULL,
    read_status     SMALLINT     NULL,
    message_body    VARCHAR(255) NULL,
    imageurl        VARCHAR(255) NULL,
    delivery_status SMALLINT     NULL,
    retry_count     INT          NOT NULL,
    type            SMALLINT     NULL,
    channel_type    SMALLINT     NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE user_preference
(
    id                        BINARY(16) NOT NULL,
    user_id                   BINARY(16) NULL,
    service_id                BINARY(16) NULL,
    notification_type         SMALLINT   NULL,
    notification_channel_type SMALLINT   NULL,
    is_subscribed             BIT(1)     NOT NULL,
    notification_frequency    SMALLINT   NULL,
    CONSTRAINT pk_userpreference PRIMARY KEY (id)
);