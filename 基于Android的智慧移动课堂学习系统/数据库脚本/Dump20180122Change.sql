ALTER TABLE `smartclass`.`class_user` 
ADD COLUMN `unread_information_num` INT NULL DEFAULT 0 AFTER `user_id`;


CREATE TABLE `smartclass`.`inform_user` (
  `inform_id` INT NOT NULL,
  `detail` TINYTEXT NULL DEFAULT NULL,
  `if_unread` VARCHAR(32) NULL DEFAULT NULL,
  `class_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `create_date_time` DATETIME NULL DEFAULT NULL,
  `modify_date_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`inform_id`),
  INDEX `inform_user_ibfk_1_idx` (`class_id` ASC),
  INDEX `inform_user_ibfk_2_idx` (`user_id` ASC),
  CONSTRAINT `inform_user_ibfk_1`
    FOREIGN KEY (`class_id`)
    REFERENCES `smartclass`.`class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `inform_user_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `smartclass`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `smartclass`.`inform` 
ADD COLUMN `read_num` INT NULL DEFAULT NULL AFTER `detail`;


ALTER TABLE `smartclass`.`class_user` 
ADD COLUMN `if_in_class` VARCHAR(32) NULL DEFAULT NULL AFTER `user_id`;

ALTER TABLE `smartclass`.`homework` 
ADD COLUMN `homework_status` VARCHAR(32) NULL DEFAULT NULL AFTER `if_repository`,
ADD COLUMN `deadline` DATETIME NULL DEFAULT NULL AFTER `homework_status`;

ALTER TABLE `smartclass`.`homework_answer` 
ADD COLUMN `class_id` INT NULL DEFAULT NULL AFTER `remark_url`,
ADD INDEX `homework_answer_ibfk_3_idx` (`class_id` ASC);
ALTER TABLE `smartclass`.`homework_answer` 
ADD CONSTRAINT `homework_answer_ibfk_3`
  FOREIGN KEY (`class_id`)
  REFERENCES `smartclass`.`class` (`class_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `smartclass`.`material` 
DROP COLUMN `if_publish`,
DROP COLUMN `publish_date_time`;
