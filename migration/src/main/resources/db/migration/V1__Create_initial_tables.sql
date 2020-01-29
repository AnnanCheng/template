/* Create table */

CREATE TABLE `coordinates` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`post_code` VARCHAR(50) NOT NULL,
	`latitude` DOUBLE NOT NULL,
	`longitude` DOUBLE NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `post_code` (`post_code`)
)
ENGINE=InnoDB
;
