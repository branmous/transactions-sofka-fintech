CREATE TABLE `transactions` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `amount` decimal(19,2) NOT NULL,
    `commission` decimal(19,2) NOT NULL,
    `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
