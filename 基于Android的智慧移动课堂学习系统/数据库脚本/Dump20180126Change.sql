ALTER TABLE `smartclass`.`class_user` 
CHARACTER SET = utf8 ;

ALTER TABLE `smartclass`.`class_user` 
CHANGE COLUMN `if_in_class` `if_in_class` VARCHAR(32) NULL DEFAULT NULL ;

ALTER SCHEMA `smartclass`  DEFAULT CHARACTER SET utf8 ;

ALTER TABLE `smartclass`.`class_user_exp` 
CHARACTER SET = utf8 ;

ALTER TABLE `smartclass`.`directory` 
CHANGE COLUMN `name` `name` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `name_detail` `name_detail` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `code_detail` `code_detail` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `remark` `remark` TEXT NULL DEFAULT NULL ;

ALTER TABLE `smartclass`.`inform_user` 
CHARACTER SET = DEFAULT , COLLATE = DEFAULT ;

ALTER TABLE `smartclass`.`inform_user` 
CHANGE COLUMN `detail` `detail` TINYTEXT NULL DEFAULT NULL ,
CHANGE COLUMN `if_unread` `if_unread` VARCHAR(32) NULL DEFAULT NULL ;

ALTER TABLE `smartclass`.`whisper_message` 
CHARACTER SET = DEFAULT ;

ALTER TABLE `smartclass`.`whisper_message` 
CHANGE COLUMN `detail` `detail` TEXT NULL DEFAULT NULL ;

