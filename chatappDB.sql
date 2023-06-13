-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: chatappDB
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit_table`
--

DROP TABLE IF EXISTS `audit_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_table` (
  `user_id` int DEFAULT NULL,
  `table_name` varchar(50) NOT NULL,
  `action_type` varchar(20) NOT NULL,
  `old_values` varchar(255) DEFAULT NULL,
  `new_values` varchar(255) DEFAULT NULL,
  `session_id` varchar(150) DEFAULT NULL,
  `ip_address` varchar(100) DEFAULT NULL,
  `audit_time` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_table`
--

LOCK TABLES `audit_table` WRITE;
/*!40000 ALTER TABLE `audit_table` DISABLE KEYS */;
INSERT INTO `audit_table` VALUES (1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"5526b2d17c6785e0c6d22e5a1c3bbee914b4cdae\",\"Expiry\":1683760707846}',NULL,'0:0:0:0:0:0:0:1',1683730707965),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"5526b2d17c6785e0c6d22e5a1c3bbee914b4cdae\",\"Expiry\":1683760707846}',NULL,'5526b2d17c6785e0c6d22e5a1c3bbee914b4cdae','0:0:0:0:0:0:0:1',1683730713351),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"16fa8b85d307dfadfa58c4ea31854aad988cb2f9\",\"Expiry\":1683756560614}',NULL,NULL,'0:0:0:0:0:0:0:1',1683786708067),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-725568977ba26b7bf4e0c473d4bbc419d332e500\",\"Expiry\":1683816708095}',NULL,'0:0:0:0:0:0:0:1',1683786708098),(1,'userinfo','UPDATE','{\"user_name\":\"VARUN\"}','{\"user_name\":\"VARUN S\"}','-725568977ba26b7bf4e0c473d4bbc419d332e500','0:0:0:0:0:0:0:1',1683786715275),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-725568977ba26b7bf4e0c473d4bbc419d332e500\",\"Expiry\":1683816708095}',NULL,'-725568977ba26b7bf4e0c473d4bbc419d332e500','0:0:0:0:0:0:0:1',1683786720453),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"8dbee4926de56ecc3f6e008f6132fd91c86a5b9\",\"Expiry\":1683818682536}',NULL,'0:0:0:0:0:0:0:1',1683788682674),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"8dbee4926de56ecc3f6e008f6132fd91c86a5b9\",\"Expiry\":1683818682536}',NULL,'8dbee4926de56ecc3f6e008f6132fd91c86a5b9','0:0:0:0:0:0:0:1',1683788686985),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"6ee1b77a820c480082201415983276bdbd8a4f34\",\"Expiry\":1683835610928}',NULL,'0:0:0:0:0:0:0:1',1683805611063),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"6ee1b77a820c480082201415983276bdbd8a4f34\",\"Expiry\":1683835610928}',NULL,'6ee1b77a820c480082201415983276bdbd8a4f34','0:0:0:0:0:0:0:1',1683805612002),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"5f6b337a6fd227dfe0790eada751e29a260446\",\"Expiry\":1683914615327}',NULL,'0:0:0:0:0:0:0:1',1683884615413),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"5f6b337a6fd227dfe0790eada751e29a260446\",\"Expiry\":1683914615327}',NULL,'5f6b337a6fd227dfe0790eada751e29a260446','0:0:0:0:0:0:0:1',1683884617574),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-c6dac3acb6a0df78e8a01d6bc746ba6fc63dbe6\",\"Expiry\":1683914654680}',NULL,'0:0:0:0:0:0:0:1',1683884654772),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-c6dac3acb6a0df78e8a01d6bc746ba6fc63dbe6\",\"Expiry\":1683914654680}',NULL,'-c6dac3acb6a0df78e8a01d6bc746ba6fc63dbe6','0:0:0:0:0:0:0:1',1683884683834),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-7338e25511d19331bc350e3795f950fc440ffc47\",\"Expiry\":1683914750106}',NULL,'0:0:0:0:0:0:0:1',1683884750199),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-7338e25511d19331bc350e3795f950fc440ffc47\",\"Expiry\":1683914750106}',NULL,'-7338e25511d19331bc350e3795f950fc440ffc47','0:0:0:0:0:0:0:1',1683884755396),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-6d3284da0288206cf74a99fcaf42ad10ec3ef971\",\"Expiry\":1683915674366}',NULL,'0:0:0:0:0:0:0:1',1683885674460),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-6d3284da0288206cf74a99fcaf42ad10ec3ef971\",\"Expiry\":1683915674366}',NULL,NULL,'0:0:0:0:0:0:0:1',1684137707979),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"150573069ac7a3c0289a3decfe106fdc1b51c3f4\",\"Expiry\":1684167707998}',NULL,'0:0:0:0:0:0:0:1',1684137707999),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"150573069ac7a3c0289a3decfe106fdc1b51c3f4\",\"Expiry\":1684167707998}',NULL,'150573069ac7a3c0289a3decfe106fdc1b51c3f4','0:0:0:0:0:0:0:1',1684137741789),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-14d7e7599ed31028dccea9acabdeea0dd53998ed\",\"Expiry\":1684184701915}',NULL,'0:0:0:0:0:0:0:1',1684154702028),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"57c027b358ad62751bbf892cbc291cdc4411021d\",\"Expiry\":1684185969998}',NULL,'0:0:0:0:0:0:0:1',1684155970127),(1,'mobile','UPDATE','{\"mobileno\":932394343}','{\"mobileno\":9323943431}','57c027b358ad62751bbf892cbc291cdc4411021d','0:0:0:0:0:0:0:1',1684155991215),(1,'userinfo','UPDATE','{\"user_name\":\"VARUN S\"}','{\"user_name\":\"VARUN\"}','57c027b358ad62751bbf892cbc291cdc4411021d','0:0:0:0:0:0:0:1',1684156718546),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-6b10e3c1abef0d614041f34ee2c38603bd20aedd\",\"Expiry\":1684186933343}',NULL,'0:0:0:0:0:0:0:1',1684156933463),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-6b10e3c1abef0d614041f34ee2c38603bd20aedd\",\"Expiry\":1684186933343}',NULL,'-6b10e3c1abef0d614041f34ee2c38603bd20aedd','0:0:0:0:0:0:0:1',1684156976918),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"22abb9f622b9d67c028a39b0bfea0bd8bbde4ddb\",\"Expiry\":1684187711354}',NULL,'0:0:0:0:0:0:0:1',1684157711490),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"22abb9f622b9d67c028a39b0bfea0bd8bbde4ddb\",\"Expiry\":1684187711354}',NULL,'22abb9f622b9d67c028a39b0bfea0bd8bbde4ddb','0:0:0:0:0:0:0:1',1684157716258),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-1bab3750a2284d8948abb39b723a76afbc34c898\",\"Expiry\":1684188580727}',NULL,'0:0:0:0:0:0:0:1',1684158580812),(1,'userinfo','UPDATE','{\"user_name\":\"VARUN\"}','{\"user_name\":\"VARUN S\"}','-1bab3750a2284d8948abb39b723a76afbc34c898','0:0:0:0:0:0:0:1',1684158589893),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-1bab3750a2284d8948abb39b723a76afbc34c898\",\"Expiry\":1684188580727}',NULL,'-1bab3750a2284d8948abb39b723a76afbc34c898','0:0:0:0:0:0:0:1',1684158593036),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"3de4e85d8e2582c2e814c38bed624afff11041e3\",\"Expiry\":1684189671100}',NULL,'0:0:0:0:0:0:0:1',1684159671192),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"3de4e85d8e2582c2e814c38bed624afff11041e3\",\"Expiry\":1684189671100}',NULL,'3de4e85d8e2582c2e814c38bed624afff11041e3','0:0:0:0:0:0:0:1',1684159673297),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-14d7e7599ed31028dccea9acabdeea0dd53998ed\",\"Expiry\":1684184701915}',NULL,NULL,'0:0:0:0:0:0:0:1',1684304079555),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"57c027b358ad62751bbf892cbc291cdc4411021d\",\"Expiry\":1684185969998}',NULL,NULL,'0:0:0:0:0:0:0:1',1684304079595),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"2ebb4c73775e6908eb81bc67ae529cec46063e69\",\"Expiry\":1684334079617}',NULL,'0:0:0:0:0:0:0:1',1684304079619),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"2ebb4c73775e6908eb81bc67ae529cec46063e69\",\"Expiry\":1684334079617}',NULL,'2ebb4c73775e6908eb81bc67ae529cec46063e69','0:0:0:0:0:0:0:1',1684304272679),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-78a0bbe358a8a4818369d05ef98af66670220849\",\"Expiry\":1685206619755}',NULL,'0:0:0:0:0:0:0:1',1685176619890),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-faef6a75cc6d9ad656b2f592d2cf923d931a33c\",\"Expiry\":1685206946144}',NULL,'127.0.0.1',1685176946277),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-faef6a75cc6d9ad656b2f592d2cf923d931a33c\",\"Expiry\":1685206946144}',NULL,'-faef6a75cc6d9ad656b2f592d2cf923d931a33c','127.0.0.1',1685177044675),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-1eaa90676fedee81f7d211b0c3089e295b249b97\",\"Expiry\":1685207056515}',NULL,'127.0.0.1',1685177056517),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-1eaa90676fedee81f7d211b0c3089e295b249b97\",\"Expiry\":1685207056515}',NULL,'-1eaa90676fedee81f7d211b0c3089e295b249b97','127.0.0.1',1685177205843),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-78a0bbe358a8a4818369d05ef98af66670220849\",\"Expiry\":1685206619755}',NULL,NULL,'127.0.0.1',1685255460229),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"79d472214573afe6e4eb63268d51f4ec813b059f\",\"Expiry\":1685285460247}',NULL,'127.0.0.1',1685255460250),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"-5c2d5b07000626827e9a57b71a293daf9e55c2d2\",\"Expiry\":1685285899059}',NULL,'127.0.0.1',1685255899170),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"-5c2d5b07000626827e9a57b71a293daf9e55c2d2\",\"Expiry\":1685285899059}',NULL,'-5c2d5b07000626827e9a57b71a293daf9e55c2d2','127.0.0.1',1685255913843),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"79d472214573afe6e4eb63268d51f4ec813b059f\",\"Expiry\":1685285460247}',NULL,NULL,'0:0:0:0:0:0:0:1',1686137534043),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"1d958d1833a9795f2ae46a6518fbd1b0cdc9f75a\",\"Expiry\":1686167534061}',NULL,'0:0:0:0:0:0:0:1',1686137534064),(1,'session_info','INSERT',NULL,'{\"user_id\":1,\"session_id\":\"4199c96ab0c92cee67a39f25d25a6dad420405cf\",\"Expiry\":1686170619603}',NULL,'0:0:0:0:0:0:0:1',1686140619700),(1,'session_info','DELETE','{\"user_id\":1,\"session_id\":\"4199c96ab0c92cee67a39f25d25a6dad420405cf\",\"Expiry\":1686170619603}',NULL,'4199c96ab0c92cee67a39f25d25a6dad420405cf','0:0:0:0:0:0:0:1',1686140634277);
/*!40000 ALTER TABLE `audit_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email` (
  `user_id` int NOT NULL,
  `emailid` varchar(30) NOT NULL,
  `is_primary` tinyint(1) DEFAULT '0',
  `is_verified` tinyint(1) DEFAULT '0',
  `created_time` timestamp NULL DEFAULT (now()),
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `emailid` (`emailid`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `email_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (41,'Gnaneswar@2001.com',0,0,'2023-02-06 06:20:54',NULL),(23,'karthick@gmail.com',0,0,'2023-04-11 06:22:38',NULL),(21,'pranav@gmail.com',0,0,'2023-03-06 06:33:26',NULL),(2,'ramesh@gmail.com',0,0,'2023-01-10 05:59:50',NULL),(3,'suresh@gmail.com',0,0,'2023-01-15 10:33:53',NULL),(1,'varunsashi@gmail.com',0,1,'2023-01-08 08:39:03','2023-05-02 15:40:42');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_info`
--

DROP TABLE IF EXISTS `group_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_info` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `admin_id` int NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT (now()),
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_id` (`group_id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `group_info_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_info`
--

LOCK TABLES `group_info` WRITE;
/*!40000 ALTER TABLE `group_info` DISABLE KEYS */;
INSERT INTO `group_info` VALUES (4,'family',1,'2023-01-16 16:54:07',NULL),(5,'rock',1,'2023-01-17 07:23:38',NULL),(6,'IAM',3,'2023-02-15 17:48:38',NULL);
/*!40000 ALTER TABLE `group_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_members`
--

DROP TABLE IF EXISTS `group_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_members` (
  `member_id` int NOT NULL,
  `group_id` int NOT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `member_id` (`member_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `group_members_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `userinfo` (`user_id`),
  CONSTRAINT `group_members_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `group_info` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_members`
--

LOCK TABLES `group_members` WRITE;
/*!40000 ALTER TABLE `group_members` DISABLE KEYS */;
INSERT INTO `group_members` VALUES (1,1,'2023-01-15 10:30:21',NULL),(2,1,'2023-01-15 10:30:21',NULL),(1,2,'2023-01-15 10:55:37',NULL),(3,2,'2023-01-15 10:55:37',NULL),(2,2,'2023-01-15 10:55:37',NULL),(1,3,'2023-01-16 09:43:57',NULL),(3,3,'2023-01-16 09:43:57',NULL),(2,3,'2023-01-16 09:43:57',NULL),(1,4,'2023-01-16 16:54:07',NULL),(3,4,'2023-01-16 16:54:07',NULL),(2,4,'2023-01-16 16:54:07',NULL),(1,5,'2023-01-17 07:23:38',NULL),(3,5,'2023-01-17 07:23:38',NULL),(2,5,'2023-01-17 07:23:38',NULL),(3,6,'2023-02-15 17:48:38',NULL),(1,6,'2023-02-15 17:48:38',NULL),(2,6,'2023-02-15 17:48:38',NULL);
/*!40000 ALTER TABLE `group_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_messages`
--

DROP TABLE IF EXISTS `group_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_messages` (
  `groupchat_id` int NOT NULL AUTO_INCREMENT,
  `senderid` int NOT NULL,
  `groupid` int NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `chattime` bigint NOT NULL,
  PRIMARY KEY (`groupchat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_messages`
--

LOCK TABLES `group_messages` WRITE;
/*!40000 ALTER TABLE `group_messages` DISABLE KEYS */;
INSERT INTO `group_messages` VALUES (3,1,4,'This is varun',1679638007602),(4,1,4,'hey',1679638510326),(5,1,5,'hi rockzz',1679638944346),(6,1,4,'j',1679655615212),(7,1,6,'Hi Guys',1680159085235),(8,1,4,'la la la',1680178534352),(9,1,6,'qwe',1680178568190),(10,3,4,'hi all',1680178791037),(11,3,4,'This is suresh',1680178810986),(12,3,5,'hi guys',1680192637181),(13,1,4,'hey all',1680718366585),(14,1,4,'hi',1680758339211),(15,1,6,'abc',1683609947960);
/*!40000 ALTER TABLE `group_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `senderid` int NOT NULL,
  `recieverid` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `chattime` bigint NOT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `senderid` (`senderid`),
  KEY `recieverid` (`recieverid`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`senderid`) REFERENCES `userinfo` (`user_id`),
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`recieverid`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (42,1,2,'hi',NULL,NULL,NULL,1674813697258),(44,1,3,'hi',NULL,NULL,NULL,1674814129456),(45,1,3,'hi suresh',NULL,NULL,NULL,1674814533582),(48,1,3,'asd',NULL,NULL,NULL,1675155747636),(49,1,3,'asd dfgj sdijf',NULL,NULL,NULL,1675155754736),(51,1,3,'yo',NULL,NULL,NULL,1675157065373),(52,2,1,'hi',NULL,NULL,NULL,1675231419460),(53,1,2,'qaws',NULL,NULL,NULL,1676445344000),(54,1,2,'abc',NULL,NULL,NULL,1676474063184),(62,1,2,'a',NULL,NULL,NULL,1676612850266),(64,1,2,'hi',NULL,NULL,NULL,1677148586769),(65,1,2,'hi',NULL,NULL,NULL,1677323940039),(66,1,2,'This is varun from IAM ',NULL,NULL,NULL,1677323967767),(67,1,3,'hi',NULL,NULL,NULL,1677827362446),(73,1,2,'helloo',NULL,NULL,NULL,1679031573280),(74,1,21,'Hi pranav. This is varun',NULL,NULL,NULL,1679656692762),(75,1,21,'hi',NULL,NULL,NULL,1679676908983),(76,3,1,'hi varun',NULL,NULL,NULL,1680178826754),(77,3,1,'suresh here',NULL,NULL,NULL,1680192301019),(78,1,3,'hi sure',NULL,NULL,NULL,1680192671429),(79,3,1,'hi varun',NULL,NULL,NULL,1680192683850),(80,3,1,'dei',NULL,NULL,NULL,1680192715371),(81,21,1,'hi varun',NULL,NULL,NULL,1680192838552),(82,2,1,'hi varun',NULL,NULL,NULL,1680193700481),(83,1,2,'hi ramesh',NULL,NULL,NULL,1680193715553),(84,1,21,'hi',NULL,NULL,NULL,1680718315669),(85,1,21,'fjelsjkgjkls',NULL,NULL,NULL,1680758357866),(86,23,1,'hi',NULL,NULL,NULL,1681194234268),(87,1,23,'bye',NULL,NULL,NULL,1683042068437),(88,1,23,'.',NULL,NULL,NULL,1683726757382);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile`
--

DROP TABLE IF EXISTS `mobile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobile` (
  `user_id` int NOT NULL,
  `mobileno` bigint NOT NULL,
  `is_primary` tinyint(1) DEFAULT '0',
  `is_verified` tinyint(1) DEFAULT '0',
  `created_time` timestamp NOT NULL DEFAULT (now()),
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
  CONSTRAINT `mobile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile`
--

LOCK TABLES `mobile` WRITE;
/*!40000 ALTER TABLE `mobile` DISABLE KEYS */;
INSERT INTO `mobile` VALUES (1,1234567892,0,1,'2023-01-08 15:45:06','2023-05-10 13:52:46'),(2,1234123412,0,0,'2023-01-10 05:59:50',NULL),(3,1212121212,0,0,'2023-01-15 10:33:53',NULL),(1,9323943431,0,1,'2023-02-17 11:37:45','2023-05-15 13:06:31'),(21,9635463785,0,1,'2023-03-06 06:33:26','2023-03-06 09:18:13'),(23,2047492747,0,1,'2023-04-11 06:22:38',NULL);
/*!40000 ALTER TABLE `mobile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seen_list`
--

DROP TABLE IF EXISTS `seen_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seen_list` (
  `chat_id` int NOT NULL,
  `user_id` int NOT NULL,
  `seen_time` timestamp NOT NULL DEFAULT (now()),
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `chat_id` (`chat_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `seen_list_ibfk_1` FOREIGN KEY (`chat_id`) REFERENCES `messages` (`chat_id`),
  CONSTRAINT `seen_list_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seen_list`
--

LOCK TABLES `seen_list` WRITE;
/*!40000 ALTER TABLE `seen_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `seen_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_info`
--

DROP TABLE IF EXISTS `session_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_info` (
  `user_id` int NOT NULL,
  `session_id` varchar(50) NOT NULL,
  `Expiry` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_info`
--

LOCK TABLES `session_info` WRITE;
/*!40000 ALTER TABLE `session_info` DISABLE KEYS */;
INSERT INTO `session_info` VALUES (1,'1d958d1833a9795f2ae46a6518fbd1b0cdc9f75a',1686167534061);
/*!40000 ALTER TABLE `session_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_pass`
--

DROP TABLE IF EXISTS `user_pass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_pass` (
  `user_id` int NOT NULL,
  `pass_salt` varchar(30) NOT NULL,
  `pass_hash` varchar(50) NOT NULL,
  `pass_status` tinyint(1) DEFAULT '1',
  `created_time` bigint DEFAULT ((unix_timestamp() * 1000)),
  `modif_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_pass_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `userinfo` (`user_id`),
  CONSTRAINT `user_pass_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `userinfo` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_pass`
--

LOCK TABLES `user_pass` WRITE;
/*!40000 ALTER TABLE `user_pass` DISABLE KEYS */;
INSERT INTO `user_pass` VALUES (1,'8nox/Yloz7XVFpqI9yRDEA','M2kQCAnyBpw6y9FZbT5StFbiEmFmpmmut+LkNtwrcfc',1,1681913177000,'2023-04-19 14:06:17'),(2,'O9umfhEHvaETiWloZoSOgw','NSzWPIMXdV43AHeHpW/VHQ3RfS+SHxqmUfZKO3DKlJc',1,1676353429000,NULL),(3,'gXkRl1wQb/XQKEo9yMW03g','LLFejkRZzPU9w7RH82CnSt+Hgq2Sx0ZSZlqQUjUvl5s',1,1676353483000,NULL),(21,'rLXb/sJmzAZxwc2sJZVjvA','5Bwbkg1IIJmL1RwofWcRDbMKP+nq/HTC5IUaAkqLHWM',1,1678084406597,NULL),(23,'kPzRSrqcvkFi2pMfxjV82Q','SXjMyDIsFj0wLkT2cfojaGf9J5KEI5nmNYSZxiZQ3WM',1,1681194158703,NULL);
/*!40000 ALTER TABLE `user_pass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `picfile` varchar(255) DEFAULT NULL,
  `created_time` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id` (`user_id`),
  UNIQUE KEY `id_2` (`user_id`),
  UNIQUE KEY `id_3` (`user_id`),
  UNIQUE KEY `id_4` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES (1,'VARUN S','MALE','INDIA',NULL,0),(2,'ramesh',NULL,NULL,NULL,20230110112950),(3,'suresh',NULL,NULL,NULL,20230115160353),(21,'pranav','null','null',NULL,20230306120326),(23,'karthick',NULL,NULL,NULL,20230411115238);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-13 10:38:10
