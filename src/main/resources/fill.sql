USE `contacts_test`;

INSERT INTO `attachment` (`id`,`filename`,`upload_date`,`commentary`,`contact_id`) VALUES (1,'test.jpg','2016-12-01','Nice pic',1);

INSERT INTO `city` (`id`,`name`) VALUES (1,'Минск');

INSERT INTO `contact` (`id`,`first_name`,`last_name`,`patronymic`,`birthday`,`sex`,`nationality_id`,`marital_status_id`,`website`,`email`,`job`,`state_id`,`city_id`,`street`,`house`,`flat`,`zip_code`) VALUES (1,'Евгений','Шилов','Павлович','1997-06-25','m',NULL,NULL,NULL,NULL,'bsuir',NULL,1,'Артиллерийская',NULL,NULL,NULL);
INSERT INTO `contact` (`id`,`first_name`,`last_name`,`patronymic`,`birthday`,`sex`,`nationality_id`,`marital_status_id`,`website`,`email`,`job`,`state_id`,`city_id`,`street`,`house`,`flat`,`zip_code`) VALUES (2,'Станислав','Грамацкий','Константинович','1997-08-31','m',NULL,NULL,NULL,NULL,'epam',NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO `phone` (`id`,`country_code`,`operator_code`,`number`,`type`,`commentary`,`contact_id`) VALUES (1,375,33,6931155,'m','Рабочий',1);
INSERT INTO `phone` (`id`,`country_code`,`operator_code`,`number`,`type`,`commentary`,`contact_id`) VALUES (2,375,2241,57285,'h','Не звонить',1);
