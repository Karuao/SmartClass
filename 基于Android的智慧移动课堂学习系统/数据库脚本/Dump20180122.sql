CREATE DATABASE  IF NOT EXISTS `smartclass` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `smartclass`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: smartclass
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `ask`
--

DROP TABLE IF EXISTS `ask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ask` (
  `ask_id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text,
  `question_url` tinytext,
  `answer` text,
  `answer_url` tinytext,
  `user_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ask_id`),
  KEY `user_id` (`user_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `ask_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `ask_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ask`
--

LOCK TABLES `ask` WRITE;
/*!40000 ALTER TABLE `ask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL AUTO_INCREMENT,
  `if_open` varchar(32) DEFAULT NULL,
  `count` smallint(6) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_user`
--

DROP TABLE IF EXISTS `attendance_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance_user` (
  `attendance_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `attendance_status` varchar(32) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `attendance_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`attendance_user_id`),
  KEY `user_id` (`user_id`),
  KEY `attendance_id` (`attendance_id`),
  CONSTRAINT `attendance_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `attendance_user_ibfk_2` FOREIGN KEY (`attendance_id`) REFERENCES `attendance` (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_user`
--

LOCK TABLES `attendance_user` WRITE;
/*!40000 ALTER TABLE `attendance_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatroom`
--

DROP TABLE IF EXISTS `chatroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatroom` (
  `chatroom_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `detail` tinytext,
  `exp` tinyint(4) DEFAULT NULL,
  `if_open` varchar(32) DEFAULT NULL,
  `if_repository` varchar(32) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`chatroom_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `chatroom_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatroom`
--

LOCK TABLES `chatroom` WRITE;
/*!40000 ALTER TABLE `chatroom` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatroom_message`
--

DROP TABLE IF EXISTS `chatroom_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatroom_message` (
  `chatroom_message_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` text,
  `url` tinytext,
  `user_id` int(11) DEFAULT NULL,
  `chatroom_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`chatroom_message_id`),
  KEY `chatroom_id` (`chatroom_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `chatroom_message_ibfk_1` FOREIGN KEY (`chatroom_id`) REFERENCES `chatroom` (`chatroom_id`),
  CONSTRAINT `chatroom_message_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatroom_message`
--

LOCK TABLES `chatroom_message` WRITE;
/*!40000 ALTER TABLE `chatroom_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatroom_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `invite_code` int(11) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `university` varchar(32) DEFAULT NULL,
  `department` varchar(32) DEFAULT NULL,
  `textbook` varchar(32) DEFAULT NULL,
  `detail` tinytext,
  `exam_shedule` tinytext,
  `population` smallint(6) DEFAULT NULL,
  `if_open` varchar(32) DEFAULT NULL,
  `if_allow_to_join` varchar(32) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_user`
--

DROP TABLE IF EXISTS `class_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_user` (
  `class_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `if_in_class` varchar(32) DEFAULT NULL,
  `unread_information_num` int(11) DEFAULT '0',
  `exp` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`class_user_id`),
  KEY `class_id_idx` (`class_id`),
  KEY `s_idx` (`user_id`),
  CONSTRAINT `class_user_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `class_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=armscii8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_user`
--

LOCK TABLES `class_user` WRITE;
/*!40000 ALTER TABLE `class_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_user_exp`
--

DROP TABLE IF EXISTS `class_user_exp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_user_exp` (
  `class_user_exp_id` int(11) NOT NULL,
  `class_user_id` int(11) DEFAULT NULL,
  `exp` int(11) DEFAULT NULL,
  `detail` tinyint(4) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`class_user_exp_id`),
  KEY `class_user_exp_ibfk_1_idx` (`class_user_id`),
  CONSTRAINT `class_user_exp_ibfk_1` FOREIGN KEY (`class_user_id`) REFERENCES `class_user` (`class_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_user_exp`
--

LOCK TABLES `class_user_exp` WRITE;
/*!40000 ALTER TABLE `class_user_exp` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_user_exp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directory`
--

DROP TABLE IF EXISTS `directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `directory` (
  `directory_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `name_detail` varchar(32) DEFAULT NULL,
  `code` smallint(6) DEFAULT NULL,
  `code_detail` varchar(32) DEFAULT NULL,
  `remark` text,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`directory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directory`
--

LOCK TABLES `directory` WRITE;
/*!40000 ALTER TABLE `directory` DISABLE KEYS */;
/*!40000 ALTER TABLE `directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homework`
--

DROP TABLE IF EXISTS `homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homework` (
  `homework_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `detail` tinytext,
  `url` tinytext CHARACTER SET big5,
  `if_repository` varchar(32) DEFAULT NULL,
  `homework_status` varchar(32) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `exp` tinyint(4) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`homework_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homework`
--

LOCK TABLES `homework` WRITE;
/*!40000 ALTER TABLE `homework` DISABLE KEYS */;
/*!40000 ALTER TABLE `homework` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homework_answer`
--

DROP TABLE IF EXISTS `homework_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homework_answer` (
  `homework_answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `homework_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `detail` text,
  `url` tinytext,
  `remark` text,
  `remark_url` tinytext,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`homework_answer_id`),
  KEY `homework_id` (`homework_id`),
  KEY `user_id` (`user_id`),
  KEY `homework_answer_ibfk_3_idx` (`class_id`),
  CONSTRAINT `homework_answer_ibfk_1` FOREIGN KEY (`homework_id`) REFERENCES `homework` (`homework_id`),
  CONSTRAINT `homework_answer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `homework_answer_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homework_answer`
--

LOCK TABLES `homework_answer` WRITE;
/*!40000 ALTER TABLE `homework_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `homework_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inform`
--

DROP TABLE IF EXISTS `inform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inform` (
  `inform_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` tinytext,
  `read_num` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`inform_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `inform_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inform`
--

LOCK TABLES `inform` WRITE;
/*!40000 ALTER TABLE `inform` DISABLE KEYS */;
/*!40000 ALTER TABLE `inform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inform_user`
--

DROP TABLE IF EXISTS `inform_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inform_user` (
  `inform_id` int(11) NOT NULL,
  `detail` tinytext,
  `if_unread` varchar(32) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`inform_id`),
  KEY `inform_user_ibfk_1_idx` (`class_id`),
  KEY `inform_user_ibfk_2_idx` (`user_id`),
  CONSTRAINT `inform_user_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `inform_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inform_user`
--

LOCK TABLES `inform_user` WRITE;
/*!40000 ALTER TABLE `inform_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `inform_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material` (
  `material_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `url` varchar(32) DEFAULT NULL,
  `if_repository` varchar(32) DEFAULT NULL,
  `detail` tinytext,
  `exp` tinyint(4) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`material_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `material_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` tinytext,
  `score` int(11) DEFAULT NULL,
  `answer_type` varchar(32) DEFAULT NULL,
  `standard_answer` tinytext,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_option`
--

DROP TABLE IF EXISTS `question_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_option` (
  `question_option_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` tinytext,
  `question_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`question_option_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `question_option_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_option`
--

LOCK TABLES `question_option` WRITE;
/*!40000 ALTER TABLE `question_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey` (
  `survey_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `if_open` varchar(32) DEFAULT NULL,
  `if_repository` varchar(32) DEFAULT NULL,
  `exp` tinyint(4) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`survey_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `survey_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_question`
--

DROP TABLE IF EXISTS `survey_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_question` (
  `survey_question_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` tinytext,
  `answer_type` varchar(32) DEFAULT NULL,
  `survey_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`survey_question_id`),
  KEY `survey_id` (`survey_id`),
  CONSTRAINT `survey_question_ibfk_1` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_question`
--

LOCK TABLES `survey_question` WRITE;
/*!40000 ALTER TABLE `survey_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_question_option`
--

DROP TABLE IF EXISTS `survey_question_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_question_option` (
  `survey_question_option_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` tinytext,
  `votes` smallint(6) DEFAULT NULL,
  `survey_question_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`survey_question_option_id`),
  KEY `survey_question_id` (`survey_question_id`),
  CONSTRAINT `survey_question_option_ibfk_1` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`survey_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_question_option`
--

LOCK TABLES `survey_question_option` WRITE;
/*!40000 ALTER TABLE `survey_question_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey_question_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `deadline` tinyint(4) DEFAULT NULL,
  `check_type` varchar(32) DEFAULT NULL,
  `exp` int(11) DEFAULT NULL,
  `max_score` int(11) DEFAULT NULL,
  `if_open` varchar(32) DEFAULT NULL,
  `if_repository` varchar(32) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `test_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_question`
--

DROP TABLE IF EXISTS `test_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_question` (
  `test_question_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`test_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_question`
--

LOCK TABLES `test_question` WRITE;
/*!40000 ALTER TABLE `test_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `think`
--

DROP TABLE IF EXISTS `think`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `think` (
  `think_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `detail` tinytext,
  `exp` tinytext,
  `if_open` varchar(32) DEFAULT NULL,
  `if_repository` varchar(32) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`think_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `think_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `think`
--

LOCK TABLES `think` WRITE;
/*!40000 ALTER TABLE `think` DISABLE KEYS */;
/*!40000 ALTER TABLE `think` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `think_answer`
--

DROP TABLE IF EXISTS `think_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `think_answer` (
  `think_answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` text,
  `think_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`think_answer_id`),
  KEY `think_id` (`think_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `think_answer_ibfk_1` FOREIGN KEY (`think_id`) REFERENCES `think` (`think_id`),
  CONSTRAINT `think_answer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `think_answer`
--

LOCK TABLES `think_answer` WRITE;
/*!40000 ALTER TABLE `think_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `think_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `avatar` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `gender` varchar(32) DEFAULT NULL,
  `status_message` varchar(32) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `university` smallint(6) DEFAULT NULL,
  `department` smallint(6) DEFAULT NULL,
  `if_show_closed_classes` varchar(32) DEFAULT NULL,
  `security_question` varchar(32) DEFAULT NULL,
  `security_answer` varchar(32) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123456@qq.com','123456','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'最爱吃的食物','香蕉',NULL,NULL),(2,'123456','123456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'最爱喝的饮料','橙汁',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_test`
--

DROP TABLE IF EXISTS `user_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_test` (
  `user_test_id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `test_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_test_id`),
  KEY `user_id` (`user_id`),
  KEY `test_id` (`test_id`),
  CONSTRAINT `user_test_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_test_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_test`
--

LOCK TABLES `user_test` WRITE;
/*!40000 ALTER TABLE `user_test` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_test_question_answer`
--

DROP TABLE IF EXISTS `user_test_question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_test_question_answer` (
  `user_test_question_answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` text,
  `score` int(11) DEFAULT NULL,
  `user_test_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_test_question_answer_id`),
  KEY `user_test_id` (`user_test_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `user_test_question_answer_ibfk_1` FOREIGN KEY (`user_test_id`) REFERENCES `user_test` (`user_test_id`),
  CONSTRAINT `user_test_question_answer_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_test_question_answer`
--

LOCK TABLES `user_test_question_answer` WRITE;
/*!40000 ALTER TABLE `user_test_question_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_test_question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `whisper_message`
--

DROP TABLE IF EXISTS `whisper_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `whisper_message` (
  `whisper_message_id` int(11) NOT NULL,
  `detail` text,
  `url` tinyint(4) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `whisper_user_id` int(11) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `modify_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`whisper_message_id`),
  KEY `whisper_message_user_user_id_fk1` (`user_id`),
  KEY `whisper_message_user_user_id_fk2` (`whisper_user_id`),
  CONSTRAINT `whisper_message_user_user_id_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `whisper_message_user_user_id_fk2` FOREIGN KEY (`whisper_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `whisper_message`
--

LOCK TABLES `whisper_message` WRITE;
/*!40000 ALTER TABLE `whisper_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `whisper_message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-22 22:36:55
