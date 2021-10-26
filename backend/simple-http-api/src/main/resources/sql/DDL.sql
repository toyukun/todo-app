CRETATE DATABASE IF NOT EXISTS http_api_db;

CREATE TABLE IF NOT EXISTS `tasks`
(
    `id`          INT(11) PRIMARY KEY AUTO_INCREMENT,
    `title`       TEXT NOT NULL,
    `description` TEXT NOT NULL
) ENGINE = InnoDB
  CHARACTER SET utf8mb4
;