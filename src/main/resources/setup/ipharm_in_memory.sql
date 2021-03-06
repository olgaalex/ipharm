--
-- Table structure for table `customer`
--
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(70) NOT NULL,
  `first_name` varchar(70) NOT NULL,
  `middle_name` varchar(70) DEFAULT NULL,
  `last_name` varchar(70) DEFAULT NULL,
  `phone_number` varchar(45) NOT NULL,
  `address` varchar(300) NOT NULL,
  `registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `authority` varchar(45) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uk_customer__customer_id` (`customer_id`),
  UNIQUE KEY `uk_customer__login` (`login`),
  UNIQUE KEY `uk_customer__email` (`email`),
  UNIQUE KEY `uk_customer__phone_number` (`phone_number`)
);

--
-- Dumping data for table `customer`
--
INSERT INTO `customer` VALUES 
	(1,'olga','olga','o.ooo@gmail.com','Ольга','Ивановна','Иванова','xxx-xx-xx','Украина, г. Киев, ул. Абрикосовая, 8 ','2013-11-20 22:06:40','ROLE_USER'),
	(2,'sidor','sidor','s.sss@gmail.com','Сидор','Сидорович','Сидоров','yyy-yy-yy','Украина, г. Киев, ул. Виноградная, 88','2013-11-20 22:06:40','ROLE_USER'),
	(3,'frodo','frodo','ljumos@ukr.net','Фродо','Бильбович','Сумкин','1234567','Shire, Backland, Anystreet, 1','2013-11-20 22:06:40','ROLE_USER'),
	(4,'harry','harry','ljumos@rambler.ru','Гарри','Джеймс','Поттер','8901234','England, London, Tisovaya, 4','2013-11-20 22:06:40','ROLE_USER'),
	(5,'xxx@gmail.com','12345','xxx@gmail.com','tfbvgy','','','111-11-11',', , , , .','2014-02-03 21:27:02','ROLE_USER'),
	(6,'zzz@gmail.com','qwertyu','zzz@gmail.com','newCustomer','','','222-22-22','','2014-02-05 20:27:33','ROLE_USER'),
	(7,'admin','admin','admin@admin.com','Админ','Админович','Админкин','333-33-33','','2014-07-17 23:25:49','ROLE_ADMIN');

--
-- Table structure for table `the_order`
--
DROP TABLE IF EXISTS `the_order`;
CREATE TABLE `the_order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int(10) unsigned NOT NULL,
  `payment_method` varchar(45) NOT NULL,
  `shipping_method` varchar(45) NOT NULL,
  `comment` varchar(300),
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_the_order__order_id` (`order_id`),
  CONSTRAINT `fk_the_order__customer__customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

--
-- Dumping data for table `the_order`
--
INSERT INTO `the_order` VALUES 
	(1,'2013-11-20 22:16:24',1,'PAY_PAL','COURIER', 'Пусть курьер позвонит, когда будет у подъезда - я выйду заберу товар. А то в квартире домофон не работает, и курьер никак не сможет войти.'),
	(2,'2013-11-20 22:16:24',2,'CASH','POST', 'Просто отправьте почтой.'),
	(3,'2013-11-20 22:16:24',3,'CASHLESS','SELF_DELIVERY', 'Если отправлюсь в путь сегодня, то смогу забрать заказ завтра или послезавтра приблизительно в 18:00 вечера.');

--
-- Table structure for table `pharmaceutical_form`
--
DROP TABLE IF EXISTS `pharmaceutical_form`;
CREATE TABLE `pharmaceutical_form` (
  `pharmaceutical_form_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pharmaceutical_form_name` varchar(45) NOT NULL,
  PRIMARY KEY (`pharmaceutical_form_id`),
  UNIQUE KEY `uk_pharmaceutical_form__pharmaceutical_form_id` (`pharmaceutical_form_id`),
  UNIQUE KEY `uk_pharmaceutical_form__pharmaceutical_form_name` (`pharmaceutical_form_name`)
);

--
-- Dumping data for table `pharmaceutical_form`
--
INSERT INTO `pharmaceutical_form` VALUES 
	(1,'капли'),
	(2,'таблетки'),
	(3,'суппозиторий'),
	(4,'драже'),
	(5,'капсулы'),
	(6,'мазь'),
	(7,'гель'),
	(8,'крем'),
	(9,'сироп'),
	(10,'порошок'),
	(11,'раствор спиртовый'),
	(12,'раствор водный'),
	(13,'раствор масляный'),
	(14,'ампулы'),
	(15,'эмульсия'),
        (16,'леденцы');

--
-- Table structure for table `producer`
--

DROP TABLE IF EXISTS `producer`;
CREATE TABLE `producer` (
  `producer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `producer_name` varchar(60) NOT NULL,
  `producer_address` varchar(120) NOT NULL,
  PRIMARY KEY (`producer_id`),
  UNIQUE KEY `uk_producer__producer_id` (`producer_id`),
  UNIQUE KEY `uk_producer__producer_name` (`producer_name`)
);

--
-- Dumping data for table `producer`
--
INSERT INTO `producer` VALUES 
	(1,'Дарница','Украина, Киев, ул. Бориспольская, 13'),
	(2,'Материа Медика','Россия, Москва, 3-й Самотечный переулок, 9'),
	(3,'Pfaizer','США, Нью-Йорк'),
	(4,'Bayer','Германия, Берлин, Мюллерштрассе, 178'),
	(5,'GlaxoSmithKline Export','Великобритания'),
	(6,'Pliva Hrvatska D.O.O.','Хорватия'),
	(7,'Teva','Украина, Киев, б-р Дружбы Народов, 19'),
	(8,'UPSA','Франция'),
	(9,'Lannacher Himittel','Австрия'),
	(10,'Фармак','Украина, Киев'),
	(11,'Gedeke AG','Германия');

--
-- Table structure for table `product_group`
--
DROP TABLE IF EXISTS `product_group`;
CREATE TABLE `product_group` (
  `product_group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_name` varchar(60) NOT NULL,
  PRIMARY KEY (`product_group_id`),
  UNIQUE KEY `uk_product_group__product_group_id` (`product_group_id`),
  UNIQUE KEY `uk_product_group__product_group_name` (`product_group_name`)
);

--
-- Dumping data for table `product_group`
--
INSERT INTO `product_group` VALUES 
	(1,'Прочие'),
	(2,'Анальгетики'),
	(3,'Жаропонижающие'),
	(4,'Органы дыхания'),
	(5,'Сердечно-сосудистая система'),
	(6,'Мочеполовая система'),
	(7,'Пищеварительная система'),
	(8,'Система крови'),
	(9,'Гормональные'),
	(10,'Витамины'),
	(11,'Антибиотики'),
	(12,'Антигельминтные'),
	(13,'Противовирусные'),
	(14,'Противогрибковые'),
	(15,'Противоопухолевые'),
	(16,'Вакцины'),
	(17,'Антисептические и дезинфецирующие'),
	(19,'Иммунотропные'),
	(20,'Гомеопатические средства');

--
-- Table structure for table `product_subgroup`
--
DROP TABLE IF EXISTS `product_subgroup`;
CREATE TABLE `product_subgroup` (
  `product_subgroup_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_id` int(10) unsigned NOT NULL,
  `product_subgroup_name` varchar(120) NOT NULL,
  PRIMARY KEY (`product_subgroup_id`),
  UNIQUE KEY `uk_product_subgroup__product_subgroup_id` (`product_subgroup_id`),
  UNIQUE KEY `uk_product_subgroup__product_subgroup_name` (`product_subgroup_name`),
  CONSTRAINT `fk_product_subgroup__product_group__product_group_id` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`product_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

--
-- Dumping data for table `product_subgroup`
--
INSERT INTO `product_subgroup` VALUES 
	(1,4,'Стимуляторы дыхания'),
	(2,4,'От кашля'),
	(3,4,'Отхаркивающие'),
	(4,4,'Бронхолитики'),
	(5,5,'Кардиотонические '),
	(6,5,'Антиангинальные'),
	(7,5,'Противоаритмические'),
	(8,5,'Антигипертензивные'),
	(9,8,'Антикоагулянты'),
	(10,8,'Коагулянты'),
	(11,6,'Мочегонные'),
	(12,6,'Влияющие на тонус и сокращения матки'),
	(13,7,'Влияющие на аппетит'),
	(14,7,'Улучшающие секрецию желудка'),
	(15,7,'Улучшающие секрецию поджелудочной железы'),
	(16,7,'Гепатотропные и гепатопротекторы'),
	(17,7,'Влияющие на деятельность желчного пузыря'),
	(18,7,'Влияющие на моторику ЖКТ'),
	(19,7,'Рвотные и противорвотные'),
	(20,11,'Широкого спектра'),
	(21,11,'Пеницилины'),
	(22,11,'Цефалоспорины'),
	(23,11,'Макролины и азалиды'),
	(24,11,'Тетрациклины'),
	(25,11,'Аминогликозиды'),
	(26,11,'Хлорамфениколы'),
	(27,11,'Фторхинолины'),
	(28,2,'Противовоспалительные'),
	(29,19,'Иммуномодуляторы'),
	(30,1,'Офтальмологические средства'),
	(31,5,'Регуляторы потенции'),
	(32,13,'Противогерпетичные');

--
-- Table structure for table `product`
--
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_international_name` varchar(70) NOT NULL,
  `storage_on_hand` int(10) unsigned NOT NULL,
  `annotation` text,
  `quantitative_composition` int(10) unsigned NOT NULL,
  `price` decimal(6,2) unsigned NOT NULL,
  `dosage` varchar(45) NOT NULL,
  `image` mediumblob,
  `producer_id` int(10) unsigned NOT NULL,
  `pharmaceutical_form_id` int(10) unsigned NOT NULL,
  `product_subgroup_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `uk_product__product_id` (`product_id`),
  UNIQUE KEY `uk_product__compound` (`product_name`, `product_international_name`, `quantitative_composition`, `dosage`, `producer_id`, `pharmaceutical_form_id`),
  CONSTRAINT `fk_product__pharmaceutical_form__pharmaceutical_form_id` FOREIGN KEY (`pharmaceutical_form_id`) REFERENCES `pharmaceutical_form` (`pharmaceutical_form_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product__producer__producer_id` FOREIGN KEY (`producer_id`) REFERENCES `producer` (`producer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product__product_subgroup__product_subgroup_id` FOREIGN KEY (`product_subgroup_id`) REFERENCES `product_subgroup` (`product_subgroup_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

--
-- Dumping data for table `product`
--
INSERT INTO `product` VALUES 
	(1,'Анаферон','gamma-interferon antibodies purified',100,'Вот так выглядит аннотация для Анаферона, но это не окончательный результат. Вскоре все изменится и аннотация станет очень большой и длинной и с большим количеством умных слов. ',20,55.31,'',NULL,2,2,29),
	(2,'Аспирин','acetilsalicilic acid',50,'Вот так выглядит аннотация для Аспирина, но это не окончательный результат. Вскоре все изменится и аннотация станет очень большой и длинной и с большим количеством умных слов. ',20,74.98,'100 мг',NULL,4,2,28),
	(3,'Визин','tetryzolinum',67,'Вот так выглядит аннотация для Визина, но это не окончательный результат. Вскоре все изменится и аннотация станет очень большой и длинной и с большим количеством умных слов. ',1,32.23,'15 мл',NULL,3,1,30),
	(4,'Виагра','sildenafil',86,'Вот так выглядит аннотация для Виагры, но это не окончательный результат. Вскоре все изменится и аннотация станет очень большой и длинной и с большим количеством умных слов. ',4,375.90,'50 мг',NULL,3,2,31),
	(5,'Ацетилсалициловая кислота','acetilsalicilic acid',200,'Вот так выглядит аннотация для Ацетилсалициловой кислоты, но это не окончательный результат. Вскоре все изменится и аннотация станет очень большой и длинной и с большим количеством умных слов. ',10,2.23,'50 мг',NULL,1,2,28),
	(6,'Ацикловир','aciclovir',100,'Аннотация для Ацикловира',20,16.00,'200 мг',NULL,1,2,32),
	(7,'Зовиракс','aciclovir',259,'Аннотация для Зовиракса',25,240.12,'250 мг',NULL,5,2,32),
	(8,'Силденафил','sildenafil',168,'Аннотация для Силденафила',1,55.00,'100 мг',NULL,6,2,31),
	(9,'Динамико','sildenafil',123,'Аннотация для Динамико',1,40.00,'100 мг',NULL,7,2,31),
	(10,'Упсарин','acetilsalicilic acid',342,'Аннотация для Упсарина',10,30.23,'500 мг',NULL,8,2,28),
	(11,'Тромбо','acetilsalicilic acid',124,'Аннотация для Тромбо',10,7.86,'50 мг',NULL,9,2,28),
	(12,'Виаль','tetryzolinum',210,'Аннотация для Виаля',1,23.00,'10 мл',NULL,10,1,30),
	(13,'Тизин','tetryzolinum',100,'Аннотация для Тизина',1,30.01,'10 мл',NULL,11,1,30);

--
-- Table structure for table `order_item`
--
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `order_item_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_quantity` int(10) unsigned NOT NULL,
  `product_price` decimal(6,2) unsigned NOT NULL,
  `order_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`order_item_id`),
  UNIQUE KEY `uk_order_item__order_item_id` (`order_item_id`),
  CONSTRAINT `fk_order_item__the_order__order_id` FOREIGN KEY (`order_id`) REFERENCES `the_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item__product__product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

--
-- Dumping data for table `order_item`
--
INSERT INTO `order_item` VALUES 
	(1,3,2.23,1,5),
	(2,1,32.32,1,3),
	(3,1,55.31,2,1),
	(4,1,74.98,2,2),
	(5,5,32.23,2,3),
	(6,3,375.90,3,4),
	(7,10,2.23,3,5);
