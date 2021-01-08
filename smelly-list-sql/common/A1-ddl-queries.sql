-- USER TABLE
CREATE TABLE `user_credential` (
    `public_id` VARCHAR(8) NOT NULL UNIQUE,
    `username` VARCHAR(24) NOT NULL,
    `email` VARCHAR(254),
    `role_mask` TINYINT NOT NULL,
    `password_hash` BINARY(60) NOT NULL,
    PRIMARY KEY (`public_id`)
);

