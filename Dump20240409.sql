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
  CONSTRAINT `FK1n4e8ccan2bcks3eoid8w2kc4` FOREIGN KEY (`quiz_id`) REFERENCES `quizs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'đáp án A',_binary '\0',1),(2,'đáp án B',_binary '\0',1),(3,'đáp án C',_binary '',1),(4,'đáp án D',_binary '\0',1),(5,'đáp án 2A',_binary '',2),(6,'đáp án 2B',_binary '',2),(7,'đáp án 2C',_binary '\0',2),(10,'abc def',_binary '',5),(11,'Abc Def',_binary '',5),(12,'ABC def',_binary '',5),(13,'đáp án 2G',_binary '',2),(16,'NGUYEN PHUONG NAM',_binary '',7),(17,'VAN tu thanh',_binary '',8),(18,'Các dịch vụ được kết nối thông qua một cổng giao tiếp duy nhất.',_binary '\0',9),(19,'Các dịch vụ chia sẻ cùng một cơ sở dữ liệu.',_binary '\0',9),(20,'Các dịch vụ được triển khai và quản lý độc lập.',_binary '',9),(21,'Các dịch vụ chạy trên cùng một máy chủ.',_binary '\0',9),(22,'Tính phân tán.',_binary '',10),(23,'Độc lập và tự quản lý.',_binary '',10),(24,'Sự chia sẻ dữ liệu đồng bộ.',_binary '',10),(25,'Tính sẵn sàng',_binary '\0',10),(26,'phân phối',_binary '',11),(27,'RestTemplate.getForObject()',_binary '',12),(28,'RestTemplate.postForObject()',_binary '\0',12),(29,'RestTemplate.delete()',_binary '\0',12),(30,'RestTemplate.put()',_binary '\0',12),(31,'get()',_binary '\0',13),(32,'exchange()',_binary '\0',13),(33,'getForObject()',_binary '',13),(34,'getForEntity()',_binary '\0',13);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `slug` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`),
  UNIQUE KEY `UK_hqknmjh5423vchi4xkyhxlhg2` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Kiến thức cơ sở','kien-thuc-co-so'),(2,'Lập trình cơ bản','lap-trinh-co-ban');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
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
  `is_coming_soon` bit(1) DEFAULT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rbsoo34muf0lcryn4q2i0h3vc` (`slug`),
  UNIQUE KEY `UK_pag0ngrmsyx23ii8bnu9k0438` (`title`),
  KEY `FKqbllephdg8tp1tltr3opj0y0` (`category_id`),
  CONSTRAINT `FKqbllephdg8tp1tltr3opj0y0` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Python cơ bản','python-co-ban','Python là một ngôn ngữ bậc cao, thông dịch, ngôn ngữ kịch bản tương tác và hướng đối tượng','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710207811/tg8ty4o9urchx7s8yhea.jpg',1200000,0,0,NULL,_binary '\0',_binary '\0',2),(2,'Truyền thông và Mạng máy tính','truyen-thong-va-mang-may-tinh','Khóa học cung cấp cho lập trình viên những kiến thức cơ bản và dễ hiểu về mạng máy tính và truyền thông dữ liệu.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710208416/fpdr6vdbtohfalvrnax5.jpg',1200000,0,0,NULL,_binary '\0',_binary '\0',1),(3,'C++ cho người mới bắt đầu','c++-cho-nguoi-moi-bat-dau','Khóa học lập trình C++ cơ bản cho người mới bắt đầu. Khóa học này sẽ cung cấp những kiến thức cơ bản, dễ hiểu nhất về ngôn ngữ lập trình C++.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710209042/zqagnpsgsenejrssbiuv.jpg',900000,0,0,NULL,_binary '\0',_binary '\0',2),(18,'Spring Boot & Angular The Full Stack Developer Guide','spring-boot-&-angular-the-full-stack-developer-guide','Mastering Spring and Spring Boot + Developing Role-Based Full-Stack App (Spring Boot, Angular, JWT, JPA, Rest, MySql)','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710406870/xaqztbqxr3yp8fbytfbs.jpg',499000,0.5,1,'2024-04-03 11:39:24.132000',_binary '',_binary '',2),(19,'Developing Role Based Full Stack App Spring Boot & Thymeleaf','developing-role-based-full-stack-app-spring-boot-&-thymeleaf','Mastering Spring Boot (Includes Spring Boot 3) + Developing a Role-Based Full-Stack App using Spring Boot + Thymeleaf','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710318814/iytqcbgxtdohlvf6hzjc.jpg',399000,0.25,1,'2024-04-03 11:41:02.681000',_binary '',_binary '',2),(20,'Spring Boot Microservices with Spring Cloud Beginner to Guru','spring-boot-microservices-with-spring-cloud-beginner-to-guru','Learn to Master Spring Boot Microservices with Spring Cloud and Docker','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712553179/njiwmxc6pdvp7gxgk4w8.jpg',1499000,0.25,1,'2024-04-08 12:15:12.978000',_binary '',_binary '',1);
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
  `lesson_type` enum('VIDEO','QUIZ','CODE','BLOG') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `chapter_id` int NOT NULL,
  `video_id` int DEFAULT NULL,
  `orders` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4j46wplmpx5lr71ugp7engt51` (`video_id`),
  KEY `FKbmvsfsb079cs19554dfl2m1id` (`chapter_id`),
  CONSTRAINT `FK9xcabosqsbjf9eo79xye6xv2c` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`),
  CONSTRAINT `FKbmvsfsb079cs19554dfl2m1id` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'Overview Spring Framework','VIDEO','2024-03-17 10:53:28.998000',1,6,3),(2,'History of Spring Framework','VIDEO','2024-03-17 10:58:26.589000',1,7,1),(4,'Spring Framework Architecture','VIDEO','2024-03-18 15:54:48.312000',1,8,2),(7,'Exercise IoC Containers Types','QUIZ','2024-03-19 11:13:34.909000',2,NULL,1),(8,'Welcome to Spring Boot Microservices!','VIDEO','2024-04-08 12:54:55.246000',7,14,1),(9,'What you will build in the Spring Boot Microservices Course','VIDEO','2024-04-08 12:57:20.024000',7,15,2),(10,'Getting the Most out of this Course','VIDEO','2024-04-08 12:59:42.585000',7,16,3),(11,'Slack Group for Spring Boot Microservices with Spring Cloud B2G','VIDEO','2024-04-08 13:02:02.463000',7,17,5),(12,'Setting up your Development Environment','VIDEO','2024-04-08 15:55:13.730000',7,18,4),(13,'Test character 01','QUIZ','2024-04-08 15:57:20.139000',7,NULL,6),(14,'The Traditional Monolith Application','VIDEO','2024-04-08 16:02:22.884000',8,19,2),(15,'What is the Cloud?','VIDEO','2024-04-08 16:03:48.414000',8,20,4),(16,'Introduction to Intro to Microservices Section','VIDEO','2024-04-08 16:04:46.778000',8,21,1),(17,'What are Microservices?','VIDEO','2024-04-08 16:05:34.480000',8,22,3),(18,'Test character 02','QUIZ','2024-04-08 16:07:03.184000',8,NULL,5),(19,'Introduction','VIDEO','2024-04-08 16:18:30.955000',9,23,1),(20,'HTTP Protocol','VIDEO','2024-04-08 16:21:56.081000',9,24,2),(21,'Introducing SFG Beer Works','VIDEO','2024-04-08 16:25:32.298000',10,25,1),(22,'HTTP GET with Spring MVC','VIDEO','2024-04-08 16:28:06.604000',10,26,2),(23,'HTTP GET with Spring RestTemplate','VIDEO','2024-04-08 16:33:10.803000',12,27,1),(24,'Test character 05','QUIZ','2024-04-08 16:34:44.467000',12,NULL,2),(25,'Java Bean Validation','VIDEO','2024-04-08 16:38:45.248000',13,28,2);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (6,20,26,'2024-04-08 17:34:27.824000',1125000);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizs`
--

DROP TABLE IF EXISTS `quizs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL,
  `quiz_type` enum('ONE_CHOICE','MULTIPLE_CHOICE','PERFORATE') DEFAULT NULL,
  `lesson_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmjow4dfilxsngm5n27sc44w1o` (`lesson_id`),
  CONSTRAINT `FKmjow4dfilxsngm5n27sc44w1o` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizs`
--

LOCK TABLES `quizs` WRITE;
/*!40000 ALTER TABLE `quizs` DISABLE KEYS */;
INSERT INTO `quizs` VALUES (1,'câu hỏi 1: ','ONE_CHOICE',7),(2,'Câu hỏi 2:','MULTIPLE_CHOICE',7),(5,'Câu hỏi 3:','PERFORATE',7),(7,'Câu hỏi 1:','PERFORATE',13),(8,'Câu hỏi 2:','PERFORATE',13),(9,'Trong kiến trúc Microservices, điều gì làm cho các dịch vụ được phân phối và triển khai độc lập?','ONE_CHOICE',18),(10,'Các nguyên tắc cơ bản của kiến trúc Microservices bao gồm:','MULTIPLE_CHOICE',18),(11,'Trong kiến trúc Microservices, việc ____ các dịch vụ cho phép chúng được triển khai và quản lý độc lập.','PERFORATE',18),(12,'Trong HTTP GET với Spring RestTemplate, phương thức nào được sử dụng để thực hiện một yêu cầu GET đến một tài nguyên?','ONE_CHOICE',24),(13,'Trong Spring RestTemplate, để thực hiện một yêu cầu GET và nhận dữ liệu trả về dưới dạng một đối tượng cụ thể, phương thức nào sẽ được sử dụng?','ONE_CHOICE',24);
/*!40000 ALTER TABLE `quizs` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `FKmtsrhuk85yo77lx55h9qoumqs` (`chapter_id`),
  KEY `FK5vaycdixusus3w4kod8gwr60k` (`course_id`),
  KEY `FKmav0ebl2kucnmofpncotj1tgx` (`lesson_id`),
  KEY `FKikusfmwuj6n9sy36b9nvltv5q` (`user_id`),
  CONSTRAINT `FK5vaycdixusus3w4kod8gwr60k` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKikusfmwuj6n9sy36b9nvltv5q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmav0ebl2kucnmofpncotj1tgx` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FKmtsrhuk85yo77lx55h9qoumqs` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_courses`
--

LOCK TABLES `track_courses` WRITE;
/*!40000 ALTER TABLE `track_courses` DISABLE KEYS */;
INSERT INTO `track_courses` VALUES (1,NULL,_binary '\0',_binary '',20,7,8,26),(2,NULL,_binary '\0',_binary '\0',20,7,9,26),(3,NULL,_binary '\0',_binary '\0',20,7,10,26),(4,NULL,_binary '\0',_binary '\0',20,7,11,26),(5,NULL,_binary '\0',_binary '\0',20,7,12,26),(6,NULL,_binary '\0',_binary '\0',20,7,13,26),(7,NULL,_binary '\0',_binary '\0',20,8,14,26),(8,NULL,_binary '\0',_binary '\0',20,8,15,26),(9,NULL,_binary '\0',_binary '\0',20,8,16,26),(10,NULL,_binary '\0',_binary '\0',20,8,17,26),(11,NULL,_binary '\0',_binary '\0',20,8,18,26),(12,NULL,_binary '\0',_binary '\0',20,9,19,26),(13,NULL,_binary '\0',_binary '\0',20,9,20,26),(14,NULL,_binary '\0',_binary '\0',20,10,21,26),(15,NULL,_binary '\0',_binary '\0',20,10,22,26),(16,NULL,_binary '\0',_binary '\0',20,12,23,26),(17,NULL,_binary '\0',_binary '\0',20,12,24,26),(18,NULL,_binary '\0',_binary '\0',20,13,25,26);
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Diệp Đại Minh','minhdiep','minhdiep@gmail.com','0123456787','https://res.cloudinary.com/dqnoopa0x/image/upload/v1709912731/swdonce265xvjppzrjwd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-08 22:45:31.933000',_binary '',NULL,NULL,1),(5,'Trinh Duy An','antrinh','antrinh@gmail.com','0123456786','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710040052/a3wnhhivnyjdlhwedhmb.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-09 08:51:35.622000',_binary '',NULL,NULL,1),(7,'Phan Truong Duy','duyphan','duyphan@gmail.com','0123456780','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710042018/spiechns072u2cp2griy.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-10 10:37:42.228000',_binary '',NULL,NULL,1),(9,'Phan Truong Bach','bachphan','bachphan@gmail.com','0123456793','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710128315/ttmcsxjccaotdoq0xdgd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 10:34:56.882000',_binary '',NULL,NULL,1),(11,'Phan Truong Dong','dongphan','dongphan@gmail.com','0323456743','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710129632/qceyiwmvfovwfu0uqlb4.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 11:00:31.875000',_binary '',NULL,NULL,1),(14,'Van Tu Thanh','thanhking','thanhking@gmail.com','0323456749','https://res.cloudinary.com/dqnoopa0x/image/upload/v1711948126/pblrqdiuudc0rpxhreob.jpg','$2a$10$H7wYpbz981GDtaKcICS/YOvr8DdZH55jpoiAvcR6ABCJu7X/CINdi','2024-04-01 12:08:46.488000',_binary '',NULL,NULL,1),(18,'Luu Ba On','onluu','21h1120046@ut.edu.vn','0323466776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712119788/ilzlxnetpbhrt5uuzjc8.jpg','$2a$10$XdUElz0EOPKOzj.UHB1g5Oai/KU2tCRKSI4glYa4tgSOwlRc4bmxC','2024-04-03 11:49:45.680000',_binary '',NULL,NULL,2),(19,'Chu Nguyen Chuong','thanhto','nguyenri986@gmail.com','0523466776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712120001/q1okkd2ydoiagdzkpnfo.jpg','$2a$10$JXXXGS3i6u.vcXekzuINneSuYXxkF.wl8vsdNX7DiLHuUbgseChtS','2024-04-03 11:53:19.511000',_binary '',NULL,NULL,2),(20,'Nguy Trung Hien','trunghien','namnguyenuth12012002@gmail.com','0523469776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712120216/cln4j496sivf3uobwiz8.jpg','$2a$10$Fa0Ron.NFgDIwBAloEhvAOgG1J9jRD6OysKi.Zf22iQ67CxjKkI/u','2024-04-03 11:56:53.759000',_binary '',NULL,NULL,2),(26,'Nguyen Phuong Nam','ringuyen','phuongnama32112002@gmail.com','0323456776','https://res.cloudinary.com/dqnoopa0x/image/upload/v1712482876/ooozzfj7t7p1zokgonni.jpg','$2a$10$LduKK0/9m.WpEgvAX50f0ev27xsiQoG4jU/rjRl66zd7jZ569O1Ly','2024-04-07 16:48:24.513000',_binary '',NULL,NULL,2);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (6,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597751/wzvpwpjsfiwh1woyh7fi.mp4','00:02:21.000000'),(7,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597779/yjxcjo7hvkbjucg2whgb.mp4','00:04:43.000000'),(8,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597812/hxbake5dvhygvzy1kjar.mp4','00:03:53.000000'),(14,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555481/wxblsi60ehws5sene54v.mp4','00:03:07.000000'),(15,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555730/wk8xzjpwnqiheic2mxnb.mp4','00:02:02.000000'),(16,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555884/qdem0r0wsveiwputamom.mp4','00:01:24.000000'),(17,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712556089/srobmpx7pwqfhpvaqowv.mp4','00:00:01.000000'),(18,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566482/a6kograxfxscnxhyshgf.mp4','00:00:26.000000'),(19,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566919/bsidyrtzyor2fxvupgw3.mp4','00:01:39.000000'),(20,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567018/wt3ccvwiby77p3vjlqqb.mp4','00:01:12.000000'),(21,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567075/gzk8psbinifezucesgnu.mp4','00:00:24.000000'),(22,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567127/jizysfqfpdrd0m0lckq1.mp4','00:01:30.000000'),(23,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567824/ju7dv8b4qq6vjiasuepb.mp4','00:00:10.000000'),(24,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568084/n9ql1gxe0sobe0eyuile.mp4','00:03:31.000000'),(25,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568309/c9jzpqjwjzrvdxqpf5mh.mp4','00:03:53.000000'),(26,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568466/fjl50mr4zmo0ebdzhbhr.mp4','00:04:43.000000'),(27,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568598/pglcufliizx40hkdpa4o.mp4','00:02:21.000000'),(28,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1712569069/he6w39biizysgme3ceqd.mp4','00:00:26.000000');
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

-- Dump completed on 2024-04-09  8:50:17
