CREATE DATABASE  IF NOT EXISTS `onlinecourse_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `onlinecourse_db`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinecourse_db
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `is_correct` bit(1) NOT NULL,
  `quiz_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1n4e8ccan2bcks3eoid8w2kc4` (`quiz_id`),
  CONSTRAINT `FK1n4e8ccan2bcks3eoid8w2kc4` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=396 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'đáp án A',_binary '\0',1),(2,'đáp án B',_binary '\0',1),(3,'đáp án C',_binary '',1),(4,'đáp án D',_binary '\0',1),(5,'đáp án 2A',_binary '',2),(6,'đáp án 2B',_binary '',2),(7,'đáp án 2C',_binary '\0',2),(10,'abc def',_binary '',5),(11,'Abc Def',_binary '',5),(12,'ABC def',_binary '',5),(13,'đáp án 2G',_binary '',2),(16,'NGUYEN PHUONG NAM',_binary '',7),(17,'VAN tu thanh',_binary '',8),(18,'Các dịch vụ được kết nối thông qua một cổng giao tiếp duy nhất.',_binary '\0',9),(19,'Các dịch vụ chia sẻ cùng một cơ sở dữ liệu.',_binary '\0',9),(20,'Các dịch vụ được triển khai và quản lý độc lập.',_binary '',9),(21,'Các dịch vụ chạy trên cùng một máy chủ.',_binary '\0',9),(22,'Tính phân tán.',_binary '',10),(23,'Độc lập và tự quản lý.',_binary '',10),(24,'Sự chia sẻ dữ liệu đồng bộ.',_binary '',10),(25,'Tính sẵn sàng',_binary '\0',10),(26,'phân phối',_binary '',11),(27,'RestTemplate.getForObject()',_binary '',12),(28,'RestTemplate.postForObject()',_binary '\0',12),(29,'RestTemplate.delete()',_binary '\0',12),(30,'RestTemplate.put()',_binary '\0',12),(31,'get()',_binary '\0',13),(32,'exchange()',_binary '\0',13),(33,'getForObject()',_binary '',13),(34,'getForEntity()',_binary '\0',13),(85,'Answer A',_binary '',27),(86,'Answer B',_binary '\0',27),(87,'Answer C',_binary '\0',27),(93,'True',_binary '',29),(94,'Answer A',_binary '',30),(95,'Answer B',_binary '',30),(96,'Answer C',_binary '\0',30),(97,'Answer E',_binary '\0',30),(98,'False',_binary '\0',29),(106,'Answer E',_binary '\0',27),(107,'Answer Nam',_binary '',32),(314,'1234',_binary '',77),(315,'56789',_binary '\0',77),(316,'1010`11',_binary '\0',77),(317,'22222222',_binary '\0',77),(366,'Java là ngôn ngữ lập trình',_binary '',90),(367,'Java là ngôn ngữ máy',_binary '\0',90),(368,'Java là ngôn ngữ dùng để giao tiếp',_binary '\0',90),(369,'Java là hiện tượng do con ngừoi tạo ra.',_binary '\0',90),(374,'ko quan tam',_binary '\0',92),(375,'chắc có ha',_binary '',92),(376,'có cc j chứ',_binary '',92),(377,'no no',_binary '\0',92),(378,'Cpp is Cpp',_binary '\0',93),(379,'Cpp is fuck',_binary '\0',93),(380,'Cpp is conket',_binary '',93),(381,'Cpp is qq',_binary '\0',93),(382,'ko quan tam',_binary '\0',94),(383,'chắc có ha',_binary '',94),(384,'có cc j chứ',_binary '',94),(385,'no no',_binary '\0',94),(386,'cout<<helloworld!',_binary '',95),(387,'yes yes oki oki',_binary '',92),(388,'System.out.println(999);',_binary '',96),(394,'java java',_binary '',92),(395,'System.out.println(9);',_binary '',99);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `slug` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`),
  UNIQUE KEY `UK_hqknmjh5423vchi4xkyhxlhg2` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Kiến thức cơ sở','kien-thuc-co-so'),(2,'Lập trình cơ bản','lap-trinh-co-ban');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `achieved_time` datetime(6) NOT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK13t46rtyipt6ayvme7crsdvs4` (`course_id`),
  KEY `FKtnnj9ktwn18vtvap4yuptwxhg` (`user_id`),
  CONSTRAINT `FK13t46rtyipt6ayvme7crsdvs4` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKtnnj9ktwn18vtvap4yuptwxhg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapters`
--

DROP TABLE IF EXISTS `chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `course_id` int DEFAULT NULL,
  `orders` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6h1m0nrtdwj37570c0sp2tdcs` (`course_id`),
  CONSTRAINT `FK6h1m0nrtdwj37570c0sp2tdcs` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapters`
--

LOCK TABLES `chapters` WRITE;
/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` VALUES (1,'Part 1: Introduction to Spring Framework',18,1),(2,'Part 2: Introduction to Spring Core',18,3),(3,'Part 3: Introduction to Spring Boot',18,2),(7,'Spring Boot Microservices Introduction',20,1),(8,'Introduction to Microservices',20,2),(9,'Introduction to Restful Web Services',20,3),(10,'Spring MVC Rest Services',20,4),(12,'Spring Boot RestTemplate',20,5),(13,'Spring MVC Validation',20,6);
/*!40000 ALTER TABLE `chapters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contests`
--

DROP TABLE IF EXISTS `contests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `period` int NOT NULL,
  `enabled` bit(1) NOT NULL,
  `times` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g1nm0irnykh69s8m1fdu9fg64` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contests`
--

LOCK TABLES `contests` WRITE;
/*!40000 ALTER TABLE `contests` DISABLE KEYS */;
INSERT INTO `contests` VALUES (10,'Contest: Test Knowledge Basic Java Core Week 01','2024-05-05 23:04:42.770000',50,_binary '',3),(11,'Contest Cpp Week 01','2024-05-06 10:00:09.489000',50,_binary '',2);
/*!40000 ALTER TABLE `contests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_informations`
--

DROP TABLE IF EXISTS `course_informations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_informations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` text NOT NULL,
  `type` enum('TARGET','DETAIL','REQUIREMENT') DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4d564c488r0guro83j36wpyts` (`course_id`),
  CONSTRAINT `FK4d564c488r0guro83j36wpyts` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_informations`
--

LOCK TABLES `course_informations` WRITE;
/*!40000 ALTER TABLE `course_informations` DISABLE KEYS */;
INSERT INTO `course_informations` VALUES (6,'Develop role-based full-stack applications, covering both back-end and front-end development.','TARGET',18),(11,'Knowledge about Java 11','REQUIREMENT',18),(14,'Comprehensive Course for Spring and Spring Boot','TARGET',19),(15,'Hands-on Expereince for Developing Role-Based Full-Stack Application (JavaCorner-Admin)','TARGET',19),(16,'Implement Business Layer using Spring Service.','TARGET',19),(17,'Implmenet Intrgration Testing using Test Containers','TARGET',19),(21,'Knowledge about Java 8','REQUIREMENT',19),(22,'Spring Core Framework (DI and IoC)','REQUIREMENT',19),(23,'Have laptop, internet','REQUIREMENT',19),(29,'Have a personal laptop','REQUIREMENT',18),(37,'Learn how to develop Microservices with Spring Boot','TARGET',20),(38,'How to deconstruct a monolith into Spring Boot Microservices','TARGET',20),(39,'How to consume RESTFul APIs using Spring RestTemplate','TARGET',20),(40,'How to use Project Lombok and MapStruct to reduce boiler plate code','TARGET',20),(41,'Must know Java and Spring Framework','REQUIREMENT',20),(42,'Basic Docker Skills','REQUIREMENT',20),(43,'SQL Skills - MySQL is used in course','REQUIREMENT',20);
/*!40000 ALTER TABLE `course_informations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL,
  `slug` varchar(70) NOT NULL,
  `description` text NOT NULL,
  `thumbnail` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `discount` float NOT NULL,
  `student_count` int DEFAULT NULL,
  `published_at` datetime(6) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `is_finished` bit(1) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rbsoo34muf0lcryn4q2i0h3vc` (`slug`),
  UNIQUE KEY `UK_pag0ngrmsyx23ii8bnu9k0438` (`title`),
  KEY `FKqbllephdg8tp1tltr3opj0y0` (`category_id`),
  CONSTRAINT `FKqbllephdg8tp1tltr3opj0y0` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Python cơ bản','python-co-ban','Python là một ngôn ngữ bậc cao, thông dịch, ngôn ngữ kịch bản tương tác và hướng đối tượng','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710207811/tg8ty4o9urchx7s8yhea.jpg',1200000,0,0,NULL,_binary '',_binary '\0',_binary '\0',2),(2,'Truyền thông và Mạng máy tính','truyen-thong-va-mang-may-tinh','Khóa học cung cấp cho lập trình viên những kiến thức cơ bản và dễ hiểu về mạng máy tính và truyền thông dữ liệu.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710208416/fpdr6vdbtohfalvrnax5.jpg',1200000,0,0,NULL,_binary '',_binary '\0',_binary '\0',1),(3,'C++ cho người mới bắt đầu','c++-cho-nguoi-moi-bat-dau','Khóa học lập trình C++ cơ bản cho người mới bắt đầu. Khóa học này sẽ cung cấp những kiến thức cơ bản, dễ hiểu nhất về ngôn ngữ lập trình C++.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710209042/zqagnpsgsenejrssbiuv.jpg',900000,0,0,NULL,_binary '',_binary '\0',_binary '\0',2),(18,'Spring Boot and Angular The Full Stack Developer Guide','spring-boot-and-angular-the-full-stack-developer-guide','Mastering Spring and Spring Boot + Developing Role-Based Full-Stack App (Spring Boot, Angular, JWT, JPA, Rest, MySql)','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710406870/xaqztbqxr3yp8fbytfbs.jpg',499000,0.5,2,'2024-04-03 11:39:24.132000',_binary '',_binary '',_binary '\0',2),(19,'Developing Role Based Full Stack App Spring Boot & Thymeleaf','developing-role-based-full-stack-app-spring-boot-&-thymeleaf','Mastering Spring Boot (Includes Spring Boot 3) + Developing a Role-Based Full-Stack App using Spring Boot + Thymeleaf','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710318814/iytqcbgxtdohlvf6hzjc.jpg',399000,0.25,1,'2024-04-03 11:41:02.681000',_binary '',_binary '',_binary '\0',2),(20,'Spring Boot Microservices with Spring Cloud Beginner to Guru','spring-boot-microservices-with-spring-cloud-beginner-to-guru','Learn to Master Spring Boot Microservices with Spring Cloud and Docker','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712553179/njiwmxc6pdvp7gxgk4w8.jpg',1499000,0.25,2,'2024-04-08 12:15:12.978000',_binary '',_binary '',_binary '\0',1);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `lesson_type` enum('VIDEO','QUIZ','CODE','TEXT') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `chapter_id` int NOT NULL,
  `video_id` int DEFAULT NULL,
  `text_id` int DEFAULT NULL,
  `orders` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4j46wplmpx5lr71ugp7engt51` (`video_id`),
  UNIQUE KEY `UK_22txxbpxqvovgp05l3r3g1k9c` (`text_id`),
  KEY `FKbmvsfsb079cs19554dfl2m1id` (`chapter_id`),
  CONSTRAINT `FK48my8s72slh0uu28lu3svaqk2` FOREIGN KEY (`text_id`) REFERENCES `text_lessons` (`id`),
  CONSTRAINT `FK9xcabosqsbjf9eo79xye6xv2c` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`),
  CONSTRAINT `FKbmvsfsb079cs19554dfl2m1id` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'Overview Spring Framework','VIDEO','2024-03-17 10:53:28.998000',1,6,NULL,3),(2,'History of Spring Framework','VIDEO','2024-03-17 10:58:26.589000',1,7,NULL,1),(4,'Spring Framework Architecture','VIDEO','2024-03-18 15:54:48.312000',1,8,NULL,2),(7,'Exercise IoC Containers Types','QUIZ','2024-03-19 11:13:34.909000',2,NULL,NULL,1),(8,'Welcome to Spring Boot Microservices!','VIDEO','2024-04-08 12:54:55.246000',7,14,NULL,1),(9,'What you will build in the Spring Boot Microservices Course','VIDEO','2024-04-08 12:57:20.024000',7,15,NULL,2),(10,'Getting the Most out of this Course','VIDEO','2024-04-08 12:59:42.585000',7,16,NULL,3),(11,'Slack Group for Spring Boot Microservices with Spring Cloud B2G','VIDEO','2024-04-08 13:02:02.463000',7,17,NULL,5),(12,'Setting up your Development Environment','VIDEO','2024-04-08 15:55:13.730000',7,18,NULL,4),(13,'Test character 01','QUIZ','2024-04-08 15:57:20.139000',7,NULL,NULL,6),(14,'The Traditional Monolith Application','VIDEO','2024-04-08 16:02:22.884000',8,19,NULL,2),(15,'What is the Cloud?','VIDEO','2024-04-08 16:03:48.414000',8,20,NULL,4),(16,'Introduction to Intro to Microservices Section','VIDEO','2024-04-08 16:04:46.778000',8,21,NULL,1),(17,'What are Microservices?','VIDEO','2024-04-08 16:05:34.480000',8,22,NULL,3),(18,'Test character 02','QUIZ','2024-04-08 16:07:03.184000',8,NULL,NULL,5),(19,'Introduction','VIDEO','2024-04-08 16:18:30.955000',9,23,NULL,1),(20,'HTTP Protocol','VIDEO','2024-04-08 16:21:56.081000',9,24,NULL,2),(21,'Introducing SFG Beer Works','VIDEO','2024-04-08 16:25:32.298000',10,25,NULL,1),(22,'HTTP GET with Spring MVC','VIDEO','2024-04-08 16:28:06.604000',10,26,NULL,2),(23,'HTTP GET with Spring RestTemplate','VIDEO','2024-04-08 16:33:10.803000',12,27,NULL,1),(24,'Test character 05','QUIZ','2024-04-08 16:34:44.467000',12,NULL,NULL,2),(25,'Java Bean Validation','VIDEO','2024-04-08 16:38:45.248000',13,28,NULL,2),(29,'Note #1: Slack Group for Spring Boot Microservices with Spring Cloud B2G','TEXT','2024-04-14 11:37:12.129000',7,NULL,4,7),(30,'Bean Validation Implementation','VIDEO','2024-04-27 11:10:37.343000',13,30,NULL,3),(31,'Note #02: Tham gia cộng đồng.','TEXT','2024-04-27 11:21:44.239000',13,NULL,5,4),(33,'Spring Boot Method Validation and diffrence between Valid and Valided','VIDEO','2024-04-27 12:35:58.367000',13,31,NULL,6),(34,'Note #04: Position Apply Intern/Fresher','TEXT','2024-04-27 12:42:37.841000',13,NULL,6,7),(40,'Quiz: Spring Boot Method Validation(High Level)!','QUIZ','2024-04-27 19:00:14.409000',13,NULL,NULL,10),(41,'Quiz: Spring Boot Method Validation(continue)','QUIZ','2024-04-27 19:02:06.962000',13,NULL,NULL,11),(42,'Quiz: Spring Boot Method Validation Continue0124','QUIZ','2024-04-27 19:03:13.639000',13,NULL,NULL,12),(43,'Quiz: Tes Continue0124','QUIZ','2024-05-05 22:57:33.581000',13,NULL,NULL,13);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `current_times` time(6) NOT NULL,
  `lesson_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5c3nije94u992vpo71887270m` (`lesson_id`),
  KEY `FKechaouoa6kus6k1dpix1u91c` (`user_id`),
  CONSTRAINT `FK5c3nije94u992vpo71887270m` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FKechaouoa6kus6k1dpix1u91c` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (2,'con cac','2024-04-15 21:23:45.070000','00:00:12.000000',8,27),(3,'dm','2024-04-15 21:24:03.659000','00:01:12.000000',8,27),(5,'note qq j','2024-04-15 21:33:39.762000','00:50:00.000000',9,27),(6,'note qq j','2024-04-19 16:02:19.471000','00:50:00.000000',1,27);
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `created_time` datetime(6) NOT NULL,
  `total_price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK68snkj0g5gsjxllhjc3v5lm0r` (`course_id`),
  KEY `FKsjfs85qf6vmcurlx43cnc16gy` (`customer_id`),
  CONSTRAINT `FK68snkj0g5gsjxllhjc3v5lm0r` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKsjfs85qf6vmcurlx43cnc16gy` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (7,20,27,'2024-04-12 09:51:17.051000',1125000),(8,18,27,'2024-04-14 10:40:38.807000',249000);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_answers`
--

DROP TABLE IF EXISTS `questions_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions_answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `lesson_id` int DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf2cs1p5oxux1pjb2a19hwonx5` (`lesson_id`),
  KEY `FK83wnbesjvrx50d1ykcmof0hba` (`parent_id`),
  KEY `FKje750h01n2nvmvb566i8gl32m` (`user_id`),
  CONSTRAINT `FK83wnbesjvrx50d1ykcmof0hba` FOREIGN KEY (`parent_id`) REFERENCES `questions_answers` (`id`),
  CONSTRAINT `FKf2cs1p5oxux1pjb2a19hwonx5` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FKje750h01n2nvmvb566i8gl32m` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_answers`
--

LOCK TABLES `questions_answers` WRITE;
/*!40000 ALTER TABLE `questions_answers` DISABLE KEYS */;
INSERT INTO `questions_answers` VALUES (1,'hi guy!','2024-04-18 16:22:22.848000',8,NULL,27),(4,'Welcome to the first lesson','2024-04-18 16:29:48.472000',8,NULL,27),(5,'Welcome to the second lesson','2024-04-18 16:32:47.964000',9,NULL,27),(6,'I have some questions','2024-04-18 16:39:45.305000',9,NULL,27),(7,'I have some questions','2024-04-18 17:09:45.905000',8,NULL,27),(8,'I can answer anything questions','2024-04-18 17:14:48.776000',8,NULL,27),(10,'Do you study about Spring Frameword before?','2024-04-18 17:23:54.253000',8,8,19),(11,'Woa, That\'s great with me','2024-04-18 17:24:25.852000',8,8,20),(13,'Yes, I have studied about Spring Core and Spring Boot since 2023','2024-04-19 10:20:48.018000',8,10,27),(14,'Yes, nice to meet you','2024-04-19 10:21:41.482000',8,11,27),(16,'I think that he quite well-know it','2024-04-19 10:25:08.762000',8,10,20),(17,'ohh I understand!','2024-04-19 10:27:00.291000',8,16,19);
/*!40000 ALTER TABLE `questions_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes`
--

DROP TABLE IF EXISTS `quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL,
  `quiz_type` enum('ONE_CHOICE','MULTIPLE_CHOICE','PERFORATE') DEFAULT NULL,
  `lesson_id` int DEFAULT NULL,
  `contest_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmjow4dfilxsngm5n27sc44w1o` (`lesson_id`),
  KEY `FKpjwnb7wdueyg306xhxshr36u2` (`contest_id`),
  CONSTRAINT `FKmjow4dfilxsngm5n27sc44w1o` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FKpjwnb7wdueyg306xhxshr36u2` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes`
--

LOCK TABLES `quizzes` WRITE;
/*!40000 ALTER TABLE `quizzes` DISABLE KEYS */;
INSERT INTO `quizzes` VALUES (1,'câu hỏi 1: ','ONE_CHOICE',7,NULL),(2,'Câu hỏi 2:','MULTIPLE_CHOICE',7,NULL),(5,'Câu hỏi 3:','PERFORATE',7,NULL),(7,'Câu hỏi 1:','PERFORATE',13,NULL),(8,'Câu hỏi 2:','PERFORATE',13,NULL),(9,'Trong kiến trúc Microservices, điều gì làm cho các dịch vụ được phân phối và triển khai độc lập?','ONE_CHOICE',18,NULL),(10,'Các nguyên tắc cơ bản của kiến trúc Microservices bao gồm:','MULTIPLE_CHOICE',18,NULL),(11,'Trong kiến trúc Microservices, việc ____ các dịch vụ cho phép chúng được triển khai và quản lý độc lập.','PERFORATE',18,NULL),(12,'Trong HTTP GET với Spring RestTemplate, phương thức nào được sử dụng để thực hiện một yêu cầu GET đến một tài nguyên?','ONE_CHOICE',24,NULL),(13,'Trong Spring RestTemplate, để thực hiện một yêu cầu GET và nhận dữ liệu trả về dưới dạng một đối tượng cụ thể, phương thức nào sẽ được sử dụng?','ONE_CHOICE',24,NULL),(27,'Choose one choice?','ONE_CHOICE',40,NULL),(29,'Choose answer true/false?','ONE_CHOICE',41,NULL),(30,'Multiple choice','MULTIPLE_CHOICE',42,NULL),(32,'Nguyen Phuong _________','PERFORATE',40,NULL),(77,'1234567890','ONE_CHOICE',43,NULL),(90,'Hãy cho biết Java Core là gì?','ONE_CHOICE',NULL,10),(92,'Java Core quan trọng như thế nào?','MULTIPLE_CHOICE',NULL,10),(93,'Cpp là gì?','ONE_CHOICE',NULL,11),(94,'Cpp quan trọng như thế nào?','MULTIPLE_CHOICE',NULL,11),(95,'Điền vào dòng code sau: _________','PERFORATE',NULL,11),(96,'Hoàn thành đoạn code sau: _________','PERFORATE',NULL,10),(99,'Hoàn thành đoạn code sau đây: _________','PERFORATE',NULL,10);
/*!40000 ALTER TABLE `quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record_detail`
--

DROP TABLE IF EXISTS `record_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `record_id` int DEFAULT NULL,
  `quiz_id` int DEFAULT NULL,
  `content_perforate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi492cvghnbsnywv4a9wbc9lk6` (`quiz_id`),
  KEY `FKhhgtssn1mgxccai4l2xwhj2fj` (`record_id`),
  CONSTRAINT `FKhhgtssn1mgxccai4l2xwhj2fj` FOREIGN KEY (`record_id`) REFERENCES `records` (`id`),
  CONSTRAINT `FKi492cvghnbsnywv4a9wbc9lk6` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record_detail`
--

LOCK TABLES `record_detail` WRITE;
/*!40000 ALTER TABLE `record_detail` DISABLE KEYS */;
INSERT INTO `record_detail` VALUES (1,1,90,NULL),(2,1,92,NULL),(3,1,96,'System.out.println(999);'),(4,1,99,'System.out.println(999);'),(5,2,90,NULL),(6,2,92,NULL),(7,2,96,'System.out.println(999);'),(8,2,99,'System.out.println(9);'),(9,3,90,NULL),(10,3,92,NULL),(11,3,96,'System.out.println(999);'),(12,3,99,'System.out.println(9);');
/*!40000 ALTER TABLE `record_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `records`
--

DROP TABLE IF EXISTS `records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade` float NOT NULL,
  `joined_at` datetime(6) NOT NULL,
  `period` int NOT NULL,
  `contest_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt6px0rn2n2xghn9mt497emlt5` (`contest_id`),
  KEY `FK6p95uajgka0j0dc9vlbjw1sf1` (`user_id`),
  CONSTRAINT `FK6p95uajgka0j0dc9vlbjw1sf1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt6px0rn2n2xghn9mt497emlt5` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `records`
--

LOCK TABLES `records` WRITE;
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
INSERT INTO `records` VALUES (1,3.75,'2024-05-10 10:38:27.907000',45,10,27),(2,8.75,'2024-05-10 10:43:44.911000',45,10,27),(3,8.13,'2024-05-10 11:00:40.332000',45,10,27);
/*!40000 ALTER TABLE `records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `records_answers`
--

DROP TABLE IF EXISTS `records_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `records_answers` (
  `record_detail_id` int NOT NULL,
  `answer_id` int NOT NULL,
  PRIMARY KEY (`record_detail_id`,`answer_id`),
  KEY `FKiq5c402iuxggxgxcyg27f2dyh` (`answer_id`),
  CONSTRAINT `FKiq5c402iuxggxgxcyg27f2dyh` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`),
  CONSTRAINT `FKrgaqktogxv3bc8ehbxnqqfnwi` FOREIGN KEY (`record_detail_id`) REFERENCES `record_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `records_answers`
--

LOCK TABLES `records_answers` WRITE;
/*!40000 ALTER TABLE `records_answers` DISABLE KEYS */;
INSERT INTO `records_answers` VALUES (5,366),(9,366),(1,367),(2,375),(6,375),(2,376),(6,376),(10,376),(2,377),(6,377),(10,377),(2,394),(6,394),(10,394);
/*!40000 ALTER TABLE `records_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` text NOT NULL,
  `rating` int NOT NULL,
  `review_time` datetime(6) NOT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa76dd0r1lr1k0cuah145lib3r` (`course_id`),
  KEY `FK6cpw2nlklblpvc7hyt7ko6v3e` (`user_id`),
  CONSTRAINT `FK6cpw2nlklblpvc7hyt7ko6v3e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKa76dd0r1lr1k0cuah145lib3r` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Khóa học rất là oke, đáng đồng tiền bát gạo',5,'2024-04-21 10:34:53.498000',18,18),(2,'Học cũng tạm!',4,'2024-04-21 10:35:38.904000',18,19),(3,'Khóa học rất xịn, phù hợp cho những người bị mất gốc',5,'2024-04-21 10:36:28.170000',18,20),(4,'không ổn lắm',3,'2024-04-21 10:37:00.618000',18,27);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_CUSTOMER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_lessons`
--

DROP TABLE IF EXISTS `text_lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `text_lessons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_lessons`
--

LOCK TABLES `text_lessons` WRITE;
/*!40000 ALTER TABLE `text_lessons` DISABLE KEYS */;
INSERT INTO `text_lessons` VALUES (4,'<hr>\n<p><span style=\"font-size:18px;\"><strong>&nbsp;</strong></span><span style=\"font-family:Aptos, sans-serif;font-size:18px;\"><strong>Chào mọi người</strong>,</span><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">\n        <o:p></o:p>\n    </span><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">Chúng ta rất vui mừng thông báo về việc tạo ra một <strong>Slack Group</strong> mới dành riêng cho việc thảo luận về <strong>Spring Boot Microservices</strong> với <strong>Spring Cloud</strong>. Slack Group này sẽ cung cấp một nền tảng để chia sẻ kiến thức, trao đổi ý tưởng và giải đáp thắc mắc về việc phát triển ứng dụng sử dụng Spring Boot và Spring Cloud.<o:p></o:p></span></p>\n<figure class=\"image\"><img style=\"aspect-ratio:850/425;\" src=\"https://res.cloudinary.com/dqnoopa0x/image/upload/v1712917143/gsw40qu812xtzkaekhps.jpg\" width=\"850\" height=\"425\"></figure>\n<hr>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:22px;\"><strong>MỤC TIÊU CỦA SLACK GROUP:</strong></span><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">\n        <o:p></o:p>\n    </span><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Chia sẻ kiến thức: Thảo luận và chia sẻ kiến thức về Spring Boot Microservices và Spring Cloud.<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Hỗ trợ cộng đồng: Cung cấp sự giúp đỡ và hỗ trợ cho những người mới bắt đầu và những người có kinh nghiệm trong việc phát triển ứng dụng sử dụng Spring framework.<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Giải đáp thắc mắc: Đặt câu hỏi và nhận câu trả lời từ cộng đồng về các vấn đề liên quan đến Spring Boot và Spring Cloud.<o:p></o:p></span></p>\n<h3 style=\"margin-left:0in;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"><strong>Cách tham gia:</strong>\n        <o:p></o:p>\n    </span></h3>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Đăng ký: Đăng ký tham gia <strong>Slack Group</strong> bằng cách gửi email đến </span><span style=\"background-color:rgb(233,238,246);color:rgb(31,31,31);font-family:&quot;Google Sans&quot;, Roboto;font-size:14px;\"><span style=\"-webkit-text-stroke-width:0px;display:inline !important;float:none;font-style:normal;font-variant-caps:normal;font-variant-ligatures:normal;font-weight:500;letter-spacing:normal;orphans:2;text-align:center;text-decoration-color:initial;text-decoration-style:initial;text-decoration-thickness:initial;text-indent:0px;text-transform:none;white-space:normal;widows:2;word-spacing:0px;\">tech.courses.895@gmail.com</span></span><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"> với tiêu đề \"Tham gia Slack Group - [Họ và tên]\".<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">Mời thành viên: Mọi thành viên trong Slack Group đều được mời để thảo luận và chia sẻ kiến thức.<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"><i><strong>Lưu ý:<o:p></o:p></strong></i></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Slack Group sẽ tuân thủ các quy định và điều khoản sử dụng được quy định trong [quy định sử dụng].<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Khi đặt câu hỏi hoặc chia sẻ kiến thức, hãy đảm bảo tuân thủ nguyên tắc tôn trọng và tích cực góp ý.<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">→ Hy vọng Slack Group sẽ trở thành một cộng đồng sôi nổi và hữu ích cho tất cả mọi người trong việc học hỏi và phát triển kỹ năng về Spring Boot Microservices và Spring Cloud.<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\"></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">Trân trọng,<o:p></o:p></span></p>\n<p style=\"line-height:107%;margin:0in 0in 8pt;\"><span style=\"font-family:Aptos, sans-serif;font-size:11pt;\">Admin<o:p></o:p></span></p>'),(5,'<p><span style=\"font-size:22px;\"><strong>THÔNG BÁO</strong></span></p>\n<hr>\n<p style=\"margin-left:0px;\">Tham gia các cộng đồng để cùng học hỏi, chia sẻ và \"thám thính\" xem F8 sắp có gì mới nhé!</p>\n<ul>\n    <li>\n        <p style=\"margin-left:0px;\">Fanpage: <u>https://www.facebook.com/f8vnofficial</u></p>\n    </li>\n    <li>\n        <p style=\"margin-left:0px;\">Group: <u>https://www.facebook.com/groups/649972919142215</u></p>\n    </li>\n    <li>\n        <p style=\"margin-left:0px;\">Youtube: <u>https://www.youtube.com/F8VNOfficial</u></p>\n    </li>\n</ul>'),(6,'Hello everyone.');
/*!40000 ALTER TABLE `text_lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track_courses`
--

DROP TABLE IF EXISTS `track_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `duration_video` time(6) DEFAULT NULL,
  `is_completed` bit(1) DEFAULT NULL,
  `is_unlock` bit(1) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `chapter_id` int DEFAULT NULL,
  `lesson_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `is_current` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmtsrhuk85yo77lx55h9qoumqs` (`chapter_id`),
  KEY `FK5vaycdixusus3w4kod8gwr60k` (`course_id`),
  KEY `FKmav0ebl2kucnmofpncotj1tgx` (`lesson_id`),
  KEY `FKikusfmwuj6n9sy36b9nvltv5q` (`user_id`),
  CONSTRAINT `FK5vaycdixusus3w4kod8gwr60k` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKikusfmwuj6n9sy36b9nvltv5q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmav0ebl2kucnmofpncotj1tgx` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FKmtsrhuk85yo77lx55h9qoumqs` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_courses`
--

LOCK TABLES `track_courses` WRITE;
/*!40000 ALTER TABLE `track_courses` DISABLE KEYS */;
INSERT INTO `track_courses` VALUES (19,NULL,_binary '',_binary '',20,7,8,27,_binary '\0'),(20,NULL,_binary '',_binary '',20,7,9,27,_binary '\0'),(21,NULL,_binary '',_binary '',20,7,10,27,_binary '\0'),(22,NULL,_binary '',_binary '',20,7,11,27,_binary '\0'),(23,NULL,_binary '',_binary '',20,7,12,27,_binary '\0'),(24,NULL,_binary '',_binary '',20,7,13,27,_binary '\0'),(25,NULL,_binary '',_binary '',20,8,14,27,_binary '\0'),(26,NULL,_binary '',_binary '',20,8,15,27,_binary '\0'),(27,NULL,_binary '',_binary '',20,8,16,27,_binary '\0'),(28,NULL,_binary '',_binary '',20,8,17,27,_binary '\0'),(29,NULL,_binary '',_binary '',20,8,18,27,_binary '\0'),(30,NULL,_binary '\0',_binary '',20,9,19,27,_binary ''),(31,NULL,_binary '\0',_binary '\0',20,9,20,27,_binary '\0'),(32,NULL,_binary '\0',_binary '\0',20,10,21,27,_binary '\0'),(33,NULL,_binary '\0',_binary '\0',20,10,22,27,_binary '\0'),(34,NULL,_binary '\0',_binary '\0',20,12,23,27,_binary '\0'),(35,NULL,_binary '\0',_binary '\0',20,12,24,27,_binary '\0'),(36,NULL,_binary '\0',_binary '\0',20,13,25,27,_binary '\0'),(37,NULL,_binary '\0',_binary '\0',18,1,1,27,_binary '\0'),(38,'00:00:40.000000',_binary '\0',_binary '',18,1,2,27,_binary ''),(39,NULL,_binary '\0',_binary '\0',18,1,4,27,_binary '\0'),(40,NULL,_binary '\0',_binary '\0',18,2,7,27,_binary '\0'),(41,NULL,_binary '',_binary '',20,7,29,27,_binary '\0'),(42,NULL,_binary '\0',_binary '\0',20,13,30,27,_binary '\0'),(43,NULL,_binary '\0',_binary '\0',20,13,31,27,_binary '\0'),(45,NULL,_binary '\0',_binary '\0',20,13,33,27,_binary '\0'),(46,NULL,_binary '\0',_binary '\0',20,13,34,27,_binary '\0'),(49,NULL,_binary '\0',_binary '\0',20,13,40,27,_binary '\0'),(50,NULL,_binary '\0',_binary '\0',20,13,41,27,_binary '\0'),(51,NULL,_binary '\0',_binary '\0',20,13,42,27,_binary '\0'),(52,NULL,_binary '\0',_binary '\0',20,13,43,27,_binary '\0');
/*!40000 ALTER TABLE `track_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_9q63snka3mdh91as4io72espi` (`phone_number`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Diệp Đại Minh','minhdiep','minhdiep@gmail.com','0123456787','https://res.cloudinary.com/dqnoopa0x/image/upload/v1709912731/swdonce265xvjppzrjwd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-08 22:45:31.933000',_binary '',NULL,NULL,1),(5,'Trinh Duy An','antrinh','antrinh@gmail.com','0123456786','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710040052/a3wnhhivnyjdlhwedhmb.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-09 08:51:35.622000',_binary '',NULL,NULL,1),(7,'Phan Truong Duy','duyphan','duyphan@gmail.com','0123456780','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710042018/spiechns072u2cp2griy.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-10 10:37:42.228000',_binary '',NULL,NULL,1),(9,'Phan Truong Bach','bachphan','bachphan@gmail.com','0123456793','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710128315/ttmcsxjccaotdoq0xdgd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 10:34:56.882000',_binary '',NULL,NULL,1),(11,'Phan Truong Dong','dongphan','dongphan@gmail.com','0323456743','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710129632/qceyiwmvfovwfu0uqlb4.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 11:00:31.875000',_binary '',NULL,NULL,1),(14,'Van Tu Thanh','thanhking','thanhking@gmail.com','0323456749','https://res.cloudinary.com/dqnoopa0x/image/upload/v1711948126/pblrqdiuudc0rpxhreob.jpg','$2a$10$H7wYpbz981GDtaKcICS/YOvr8DdZH55jpoiAvcR6ABCJu7X/CINdi','2024-04-01 12:08:46.488000',_binary '',NULL,NULL,1),(18,'Luu Ba On','onluu','21h1120046@ut.edu.vn','0323466776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712119788/ilzlxnetpbhrt5uuzjc8.jpg','$2a$10$XdUElz0EOPKOzj.UHB1g5Oai/KU2tCRKSI4glYa4tgSOwlRc4bmxC','2024-04-03 11:49:45.680000',_binary '',NULL,NULL,2),(19,'Chu Nguyen Chuong','thanhto','nguyenri986@gmail.com','0523466776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712120001/q1okkd2ydoiagdzkpnfo.jpg','$2a$10$JXXXGS3i6u.vcXekzuINneSuYXxkF.wl8vsdNX7DiLHuUbgseChtS','2024-04-03 11:53:19.511000',_binary '',NULL,NULL,2),(20,'Nguy Trung Hien','trunghien','namnguyenuth12012002@gmail.com','0523469776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712120216/cln4j496sivf3uobwiz8.jpg','$2a$10$Fa0Ron.NFgDIwBAloEhvAOgG1J9jRD6OysKi.Zf22iQ67CxjKkI/u','2024-04-03 11:56:53.759000',_binary '',NULL,NULL,2),(27,'Nguyen Phuong Nam','ringuyen','phuongnama32112002@gmail.com','0323456776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712482876/ooozzfj7t7p1zokgonni.jpg','$2a$10$Y80X36Qc6cDlsSuqkO.mwOhsPTiWyK7Hx04Ilrh10VeRFv4fPEN4C','2024-04-12 09:37:36.192000',_binary '',NULL,NULL,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(150) NOT NULL,
  `duration` time(6) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (6,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597751/wzvpwpjsfiwh1woyh7fi.mp4','00:02:21.000000','Spring makes it easy to create Java enterprise applications. It provides everything you need to embrace the Java language in an enterprise environment, with support for Groovy and Kotlin as alternative languages on the JVM, and with the flexibility to create many kinds of architectures depending on an application’s needs. As of Spring Framework 6.0, Spring requires Java 17+.'),(7,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597779/yjxcjo7hvkbjucg2whgb.mp4','00:04:43.000000','Spring came into being in 2003 as a response to the complexity of the early J2EE specifications. While some consider Java EE and its modern-day successor Jakarta EE to be in competition with Spring, they are in fact complementary. The Spring programming model does not embrace the Jakarta EE platform specification; rather, it integrates with carefully selected individual specifications from the traditional EE umbrella.'),(8,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597812/hxbake5dvhygvzy1kjar.mp4','00:03:53.000000','The Spring Framework provides about 20 modules which can be used based on an application requirement.'),(14,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555481/wxblsi60ehws5sene54v.mp4','00:03:07.000000','Welcome to the Spring Boot Microservices course!'),(15,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555730/wk8xzjpwnqiheic2mxnb.mp4','00:02:02.000000','Learn about the applications you will build in your Spring Boot Microservices course.'),(16,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555884/qdem0r0wsveiwputamom.mp4','00:01:24.000000','In this lecture you will get tips and advice on how to get the most out of your Spring Boot Microservices with Spring Cloud course.'),(17,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712556089/srobmpx7pwqfhpvaqowv.mp4','00:00:01.000000','Join the Slack Group for the Spring Boot Microserviecs course! '),(18,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566482/a6kograxfxscnxhyshgf.mp4','00:00:26.000000','In this lecture you will learn about setting up your development environment for your Spring Boot Microservices with Spring Cloud course.'),(19,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566919/bsidyrtzyor2fxvupgw3.mp4','00:01:39.000000','Learn about traditional monolithic applications.'),(20,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567018/wt3ccvwiby77p3vjlqqb.mp4','00:01:12.000000','The cloud is a distributed collection of servers that host software and infrastructure, and it is accessed over the Internet.'),(21,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567075/gzk8psbinifezucesgnu.mp4','00:00:24.000000','Preview of the section of the course Introducing Microservices, and road map overview of upcoming sections.'),(22,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567127/jizysfqfpdrd0m0lckq1.mp4','00:01:30.000000','Learn what microservices actually are and how microservices are different from traditional monolithic applications'),(23,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567824/ju7dv8b4qq6vjiasuepb.mp4','00:00:10.000000','overview of developing WebLogic Web services that conform to the Representational State Transfer (REST) architectural style using Java API for RESTful Web Services (JAX-RS)..'),(24,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568084/n9ql1gxe0sobe0eyuile.mp4','00:03:31.000000','HTTP is a protocol for fetching resources such as HTML documents. It is the foundation of any data exchange on the Web and it is a client-server protocol, which means requests are initiated by the recipient, usually the Web browser.'),(25,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568309/c9jzpqjwjzrvdxqpf5mh.mp4','00:03:53.000000','This application mimics a beer distribution pipeline. Beer consumers order beers from a pub. The pub, when needed will order more beer from a beer distributor.'),(26,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568466/fjl50mr4zmo0ebdzhbhr.mp4','00:04:43.000000','Spring MVC will give you the HttpRequest if you just add it to your controller method signature.'),(27,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568598/pglcufliizx40hkdpa4o.mp4','00:02:21.000000','In this, Spring Boot RestTemplate GET request example, learn to use RestTemplate to invoke HTTP GET API and verify the response status code and the response entity body.'),(28,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712569069/he6w39biizysgme3ceqd.mp4','00:00:26.000000','we’ll cover the basics of validating a Java bean with the standard JSR-380 framework and its specification of Jakarta Bean Validation 3.0, which builds upon the features of the Bean Validation API introduced in Java EE 7.'),(30,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1714191035/tydqyrp0uyp8elfjveka.mp4','00:00:01.000000','Learn how to validate domain objects in Spring Boot using Hibernate Validator, the reference implementation of the Bean Validation framework.'),(31,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1714278963/wp1dcbjxjidpzilmy7gd.mp4','00:00:10.000000','Spring Boot has several validation features not found in Spring MVC. Learn about Spring Boot\'s method validation in this lecture.');
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-10 16:48:27
