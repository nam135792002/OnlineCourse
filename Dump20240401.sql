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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'đáp án A',_binary '\0',1),(2,'đáp án B',_binary '\0',1),(3,'đáp án C',_binary '',1),(4,'đáp án D',_binary '\0',1),(5,'đáp án 2A',_binary '',2),(6,'đáp án 2B',_binary '',2),(7,'đáp án 2C',_binary '\0',2),(10,'abc def',_binary '',5),(11,'Abc Def',_binary '',5),(12,'ABC def',_binary '',5),(13,'đáp án 2G',_binary '',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapters`
--

LOCK TABLES `chapters` WRITE;
/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` VALUES (1,'Part 1: Introduction to Spring Framework',18,1),(2,'Part 2: Introduction to Spring Core',18,3),(3,'Part 3: Introduction to Spring Boot',18,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_informations`
--

LOCK TABLES `course_informations` WRITE;
/*!40000 ALTER TABLE `course_informations` DISABLE KEYS */;
INSERT INTO `course_informations` VALUES (6,'Develop role-based full-stack applications, covering both back-end and front-end development.','TARGET',18),(8,'80 hours on-demand video','DETAIL',18),(9,'300 exercise regularly','DETAIL',18),(11,'Knowledge about Java 11','REQUIREMENT',18),(14,'Comprehensive Course for Spring and Spring Boot','TARGET',19),(15,'Hands-on Expereince for Developing Role-Based Full-Stack Application (JavaCorner-Admin)','TARGET',19),(16,'Implement Business Layer using Spring Service.','TARGET',19),(17,'Implmenet Intrgration Testing using Test Containers','TARGET',19),(18,'23 hours on-demand video','DETAIL',19),(19,'Certificate of completion','DETAIL',19),(20,'Access on mobile and TV','DETAIL',19),(21,'Knowledge about Java 8','REQUIREMENT',19),(22,'Spring Core Framework (DI and IoC)','REQUIREMENT',19),(23,'Have laptop, internet','REQUIREMENT',19),(29,'Have a personal laptop','REQUIREMENT',18);
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Python cơ bản','python-co-ban','Python là một ngôn ngữ bậc cao, thông dịch, ngôn ngữ kịch bản tương tác và hướng đối tượng','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710207811/tg8ty4o9urchx7s8yhea.jpg',1200000,0,0,NULL,_binary '\0',_binary '\0',2),(2,'Truyền thông và Mạng máy tính','truyen-thong-va-mang-may-tinh','Khóa học cung cấp cho lập trình viên những kiến thức cơ bản và dễ hiểu về mạng máy tính và truyền thông dữ liệu.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710208416/fpdr6vdbtohfalvrnax5.jpg',1200000,0,0,NULL,_binary '\0',_binary '\0',1),(3,'C++ cho người mới bắt đầu','c++-cho-nguoi-moi-bat-dau','Khóa học lập trình C++ cơ bản cho người mới bắt đầu. Khóa học này sẽ cung cấp những kiến thức cơ bản, dễ hiểu nhất về ngôn ngữ lập trình C++.','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710209042/zqagnpsgsenejrssbiuv.jpg',900000,0,0,NULL,_binary '\0',_binary '\0',2),(18,'Spring Boot & Angular The Full Stack Developer Guide','spring-boot-&-angular-the-full-stack-developer-guide','Mastering Spring and Spring Boot + Developing Role-Based Full-Stack App (Spring Boot, Angular, JWT, JPA, Rest, MySql)','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710406870/xaqztbqxr3yp8fbytfbs.jpg',499000,0.5,0,NULL,_binary '',_binary '\0',2),(19,'Developing Role Based Full Stack App Spring Boot & Thymeleaf','developing-role-based-full-stack-app-spring-boot-&-thymeleaf','Mastering Spring Boot (Includes Spring Boot 3) + Developing a Role-Based Full-Stack App using Spring Boot + Thymeleaf','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710318814/iytqcbgxtdohlvf6hzjc.jpg',399000,0.25,0,NULL,_binary '\0',_binary '\0',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'Overview Spring Framework','VIDEO','2024-03-17 10:53:28.998000',1,6,3),(2,'History of Spring Framework','VIDEO','2024-03-17 10:58:26.589000',1,7,1),(4,'Spring Framework Architecture','VIDEO','2024-03-18 15:54:48.312000',1,8,2),(7,'Exercise IoC Containers Types','QUIZ','2024-03-19 11:13:34.909000',2,NULL,1);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizs`
--

LOCK TABLES `quizs` WRITE;
/*!40000 ALTER TABLE `quizs` DISABLE KEYS */;
INSERT INTO `quizs` VALUES (1,'câu hỏi 1: ','ONE_CHOICE',7),(2,'Câu hỏi 2:','MULTIPLE_CHOICE',7),(5,'Câu hỏi 3:','PERFORATE',7);
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
INSERT INTO `roles` VALUES (1,'Admin'),(2,'Customer');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Diệp Đại Minh','','minhdiep@gmail.com','0123456787','https://res.cloudinary.com/dqnoopa0x/image/upload/v1709912731/swdonce265xvjppzrjwd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-08 22:45:31.933000',_binary '',NULL,NULL,1),(5,'Trinh Duy An','','antrinh@gmail.com','0123456786','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710040052/a3wnhhivnyjdlhwedhmb.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-09 08:51:35.622000',_binary '',NULL,NULL,1),(7,'Phan Truong Duy','','duyphan@gmail.com','0123456780','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710042018/spiechns072u2cp2griy.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-10 10:37:42.228000',_binary '',NULL,NULL,1),(9,'Phan Truong Bach','','bachphan@gmail.com','0123456793','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710128315/ttmcsxjccaotdoq0xdgd.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 10:34:56.882000',_binary '',NULL,NULL,1),(11,'Phan Truong Dong','','dongphan@gmail.com','0323456743','https://res.cloudinary.com/dqnoopa0x/image/upload/v1710129632/qceyiwmvfovwfu0uqlb4.jpg','$2a$10$cCpm1lq/68NA35hZY5Fm.emwE9NZCgbxDTZGnFPDBQGnyYGdOTTdW','2024-03-11 11:00:31.875000',_binary '',NULL,NULL,1),(14,'Van Tu Thanh','thanhking','thanhking@gmail.com','0323456749','https://res.cloudinary.com/dqnoopa0x/image/upload/v1711948126/pblrqdiuudc0rpxhreob.jpg','$2a$10$H7wYpbz981GDtaKcICS/YOvr8DdZH55jpoiAvcR6ABCJu7X/CINdi','2024-04-01 12:08:46.488000',_binary '',NULL,NULL,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (6,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597751/wzvpwpjsfiwh1woyh7fi.mp4','00:02:21.000000'),(7,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597779/yjxcjo7hvkbjucg2whgb.mp4','00:04:43.000000'),(8,'https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597812/hxbake5dvhygvzy1kjar.mp4','00:03:53.000000');
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

-- Dump completed on 2024-04-01 16:04:00
