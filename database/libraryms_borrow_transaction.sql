-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: libraryms
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `borrow_transaction`
--

DROP TABLE IF EXISTS `borrow_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_copy_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  `issue_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `due_date` datetime NOT NULL,
  `return_date` datetime DEFAULT NULL,
  `fine_amount` decimal(10,2) DEFAULT '0.00',
  `status` enum('ISSUED','RETURNED','LOST') DEFAULT 'ISSUED',
  `issued_by` varchar(50) NOT NULL,
  `received_by` varchar(50) DEFAULT NULL,
  `is_fine_paid` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `book_copy_id` (`book_copy_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `borrow_transaction_ibfk_1` FOREIGN KEY (`book_copy_id`) REFERENCES `book_copy` (`id`) ON DELETE CASCADE,
  CONSTRAINT `borrow_transaction_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `calculate_fine_on_return` BEFORE UPDATE ON `borrow_transaction` FOR EACH ROW BEGIN
    DECLARE user_role VARCHAR(20);
    DECLARE days_late INT;
    
    -- 1. Only run this logic if the book is being returned (return_date changes from NULL to a Date)
    IF OLD.return_date IS NULL AND NEW.return_date IS NOT NULL THEN
    
        -- 2. Find out if the user is a STUDENT or TEACHER
        SELECT role INTO user_role 
        FROM student 
        WHERE id = NEW.student_id;
        
        -- 3. Calculate how many days late (Return Date - Due Date)
        SET days_late = DATEDIFF(NEW.return_date, OLD.due_date);
        
        -- 4. Apply the Rules
        IF days_late > 0 THEN
            IF user_role = 'TEACHER' THEN
                SET NEW.fine_amount = 0.00;  -- No fine for teachers
            ELSE
                SET NEW.fine_amount = days_late * 20.00; -- 20 per day for students
            END IF;
        ELSE
            SET NEW.fine_amount = 0.00; -- Returned on time or early
        END IF;
        
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-04 23:51:07
