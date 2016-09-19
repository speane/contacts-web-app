CREATE SCHEMA IF NOT EXISTS `contacts_test` DEFAULT CHARACTER SET utf8 ;
USE `contacts_test` ;

-- -----------------------------------------------------
-- Table `contacts_test`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`city` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`marital_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`marital_status` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`nationality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`nationality` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`state` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`contact` (
  `id` INT(11) NOT NULL,
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
  PRIMARY KEY (`id`),
  INDEX `nationality_id_idx` (`nationality_id` ASC),
  INDEX `marital_status_idx` (`marital_status_id` ASC),
  INDEX `state_idx` (`state_id` ASC),
  INDEX `city_idx` (`city_id` ASC),
  CONSTRAINT `city`
    FOREIGN KEY (`city_id`)
    REFERENCES `contacts_test`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `marital_status`
    FOREIGN KEY (`marital_status_id`)
    REFERENCES `contacts_test`.`marital_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `nationality_id`
    FOREIGN KEY (`nationality_id`)
    REFERENCES `contacts_test`.`nationality` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `state`
    FOREIGN KEY (`state_id`)
    REFERENCES `contacts_test`.`state` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`attachment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `filename` VARCHAR(45) NOT NULL,
  `upload_date` DATE NOT NULL,
  `commentary` TEXT NULL DEFAULT NULL,
  `contact_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `contact_id_idx` (`contact_id` ASC),
  CONSTRAINT `contact_id`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contacts_test`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `contacts_test`.`phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_test`.`phone` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `country_code` INT(11) NULL DEFAULT NULL,
  `operator_code` INT(11) NULL DEFAULT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `type` ENUM('m', 'h') NULL DEFAULT NULL,
  `commentary` TEXT NULL DEFAULT NULL,
  `contact_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `contact_id_idx` (`contact_id` ASC),
  CONSTRAINT `contact_phone_id`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contacts_test`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;