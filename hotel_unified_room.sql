CREATE DATABASE  IF NOT EXISTS `hotel` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hotel`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	5.7.16

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
-- Table structure for table `unified_room`
--

DROP TABLE IF EXISTS `unified_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unified_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) NOT NULL COMMENT '外键：酒店id',
  `roomtype` varchar(255) DEFAULT 'null' COMMENT '房间类型：大床房，双床房等等',
  `decription` varchar(255) DEFAULT NULL COMMENT '房型描述：面积，有无窗户等等',
  `roomservice` varchar(255) DEFAULT NULL COMMENT '房间服务用分号隔开：wifi；早餐供应；空调',
  `price` double DEFAULT '0' COMMENT '房间价格',
  PRIMARY KEY (`id`),
  KEY `hotel_id_idx` (`hid`),
  CONSTRAINT `hotel_id` FOREIGN KEY (`hid`) REFERENCES `unified_hotel` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unified_room`
--

LOCK TABLES `unified_room` WRITE;
/*!40000 ALTER TABLE `unified_room` DISABLE KEYS */;
INSERT INTO `unified_room` VALUES (1,1,'静雅商务大床房-预付特惠-含双早','面积25㎡;位于1-7层;大床;独立卫浴;无窗','宽带上网;免费市内电话;空调;吹风机;24小时热水',209),(2,1,'静雅商务双床房-预付特惠-含双早','面积30-40㎡;位于2-4层;双床;独立卫浴;无窗','宽带上网;免费市内电话;空调;吹风机;24小时热水',239);
/*!40000 ALTER TABLE `unified_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-10 13:01:16
