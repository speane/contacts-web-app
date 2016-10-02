CREATE USER IF NOT EXISTS 'evgeny_shilov'@'localhost' IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES ON evgeny_shilov_contacts.* TO 'evgeny_shilov'@'localhost';

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
-- -----------------------------------------------------
-- Schema contacts_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `evgeny_shilov_contacts` DEFAULT CHARACTER SET utf8 ;
USE `evgeny_shilov_contacts` ;

-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`city` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`marital_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`marital_status` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;

INSERT INTO `marital_status` (`id`,`name`) VALUES (1,'Женат/замужем');
INSERT INTO `marital_status` (`id`,`name`) VALUES (2,'Не женат/не замужем');


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`nationality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`nationality` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`state` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`contact` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NULL DEFAULT NULL,
  `birthday` DATE NULL DEFAULT NULL,
  `sex` ENUM('m', 'f') NULL DEFAULT NULL,
  `nationality_id` INT(11) NULL DEFAULT NULL,
  `marital_status_id` INT(11) NULL DEFAULT NULL,
  `website` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `job` VARCHAR(45) NULL DEFAULT NULL,
  `state_id` INT(11) NULL DEFAULT NULL,
  `city_id` INT(11) NULL DEFAULT NULL,
  `street` VARCHAR(45) NULL DEFAULT NULL,
  `house` VARCHAR(5) NULL DEFAULT NULL,
  `flat` VARCHAR(5) NULL DEFAULT NULL,
  `zip_code` VARCHAR(10) NULL DEFAULT NULL,
  `image_filename` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `nationality_id_idx` (`nationality_id` ASC),
  INDEX `marital_status_idx` (`marital_status_id` ASC),
  INDEX `state_idx` (`state_id` ASC),
  INDEX `city_idx` (`city_id` ASC),
  CONSTRAINT `city_fk`
  FOREIGN KEY (`city_id`)
  REFERENCES `contacts_test`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `marital_status_fk`
  FOREIGN KEY (`marital_status_id`)
  REFERENCES `contacts_test`.`marital_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `nationality_id_fk`
  FOREIGN KEY (`nationality_id`)
  REFERENCES `contacts_test`.`nationality` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `state_fk`
  FOREIGN KEY (`state_id`)
  REFERENCES `contacts_test`.`state` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`attachment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `filename` VARCHAR(45) NOT NULL,
  `upload_date` DATE NOT NULL,
  `commentary` TEXT NULL DEFAULT NULL,
  `contact_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `attachment_contact_idx` (`contact_id` ASC),
  CONSTRAINT `attachment_contact_fk`
  FOREIGN KEY (`contact_id`)
  REFERENCES `contacts_test`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`phone_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`phone_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;

INSERT INTO `phone_type` (`id`,`name`) VALUES (1,'Мобильный');
INSERT INTO `phone_type` (`id`,`name`) VALUES (2,'Домашний');


-- -----------------------------------------------------
-- Table `evgeny_shilov_contacts`.`phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evgeny_shilov_contacts`.`phone` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `country_code` INT(11) NOT NULL,
  `operator_code` INT(11) NOT NULL,
  `number` INT(11) NOT NULL,
  `commentary` TEXT NULL DEFAULT NULL,
  `contact_id` INT(11) NOT NULL,
  `phone_type_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `phone_contact_idx` (`contact_id` ASC),
  INDEX `phone_type_idx` (`phone_type_id` ASC),
  CONSTRAINT `phone_contact`
  FOREIGN KEY (`contact_id`)
  REFERENCES `contacts_test`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `phote_type_fk`
  FOREIGN KEY (`phone_type_id`)
  REFERENCES `contacts_test`.`phone_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;