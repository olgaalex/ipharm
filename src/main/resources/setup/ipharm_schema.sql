CREATE DATABASE  IF NOT EXISTS `ipharm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ipharm`;
-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (i686)
--
-- Host: 127.0.0.1    Database: ipharm
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `the_order`
--

DROP TABLE IF EXISTS `the_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `the_order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int(10) unsigned NOT NULL,
  `payment_method` varchar(45) NOT NULL,
  `shipping_method` varchar(45) NOT NULL,
  `comment` varchar(300),
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_the_order__order_id` (`order_id`),
  KEY `customer_idx` (`customer_id`),
  CONSTRAINT `fk_the_order__customer__customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item` (
  `order_item_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_quantity` int(10) unsigned NOT NULL,
  `product_price` decimal(6,2) unsigned NOT NULL,
  `order_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`order_item_id`),
  UNIQUE KEY `uk_order_item__order_item_id` (`order_item_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `product_id_idx` (`product_id`),
  CONSTRAINT `fk_order_item__the_order__order_id` FOREIGN KEY (`order_id`) REFERENCES `the_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item__product__product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pharmaceutical_form`
--

DROP TABLE IF EXISTS `pharmaceutical_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmaceutical_form` (
  `pharmaceutical_form_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pharmaceutical_form_name` varchar(45) NOT NULL,
  PRIMARY KEY (`pharmaceutical_form_id`),
  UNIQUE KEY `uk_pharmaceutical_form__pharmaceutical_form_id` (`pharmaceutical_form_id`),
  UNIQUE KEY `uk_pharmaceutical_form__pharmaceutical_form_name` (`pharmaceutical_form_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producer`
--

DROP TABLE IF EXISTS `producer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producer` (
  `producer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `producer_name` varchar(60) NOT NULL,
  `producer_address` varchar(120) NOT NULL,
  PRIMARY KEY (`producer_id`),
  UNIQUE KEY `uk_producer__producer_id` (`producer_id`),
  UNIQUE KEY `uk_producer__producer_name` (`producer_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  KEY `producer_id_idx` (`producer_id`),
  KEY `pharmaceutical_form_id_idx` (`pharmaceutical_form_id`),
  KEY `product_subgroup_id_idx` (`product_subgroup_id`),
  CONSTRAINT `fk_product__pharmaceutical_form__pharmaceutical_form_id` FOREIGN KEY (`pharmaceutical_form_id`) REFERENCES `pharmaceutical_form` (`pharmaceutical_form_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product__producer__producer_id` FOREIGN KEY (`producer_id`) REFERENCES `producer` (`producer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product__product_subgroup__product_subgroup_id` FOREIGN KEY (`product_subgroup_id`) REFERENCES `product_subgroup` (`product_subgroup_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_group`
--

DROP TABLE IF EXISTS `product_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_group` (
  `product_group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_name` varchar(60) NOT NULL,
  PRIMARY KEY (`product_group_id`),
  UNIQUE KEY `uk_product_group__product_group_id` (`product_group_id`),
  UNIQUE KEY `uk_product_group__product_group_name` (`product_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_subgroup`
--

DROP TABLE IF EXISTS `product_subgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_subgroup` (
  `product_subgroup_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_id` int(10) unsigned NOT NULL,
  `product_subgroup_name` varchar(120) NOT NULL,
  PRIMARY KEY (`product_subgroup_id`),
  UNIQUE KEY `uk_product_subgroup__product_subgroup_id` (`product_subgroup_id`),
  UNIQUE KEY `uk_product_subgroup__product_subgroup_name` (`product_subgroup_name`),
  KEY `product_group_id_idx` (`product_group_id`),
  CONSTRAINT `fk_product_subgroup__product_group__product_group_id` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`product_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-26  3:29:35
