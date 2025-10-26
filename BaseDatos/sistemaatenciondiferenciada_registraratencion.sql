-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: sistemaatenciondiferenciada
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `registraratencion`
--

DROP TABLE IF EXISTS `registraratencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registraratencion` (
  `idAtencion` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `dniCliente` varchar(20) DEFAULT NULL,
  `idCanal` int DEFAULT NULL,
  `idModeloAtencion` int DEFAULT NULL,
  `legajo` int DEFAULT NULL,
  `observaciones` text,
  `idGestion` int DEFAULT NULL,
  PRIMARY KEY (`idAtencion`),
  KEY `idx_regatt_dniCliente` (`dniCliente`),
  KEY `idx_regatt_idCanal` (`idCanal`),
  KEY `idx_regatt_idGestion` (`idGestion`),
  KEY `idx_regatt_idModeloAtencion` (`idModeloAtencion`),
  KEY `idx_regatt_legajo` (`legajo`),
  CONSTRAINT `fk_regatt_canal` FOREIGN KEY (`idCanal`) REFERENCES `canal` (`idCanal`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_regatt_cliente` FOREIGN KEY (`dniCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_regatt_colaborador` FOREIGN KEY (`legajo`) REFERENCES `colaborador` (`legajo`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_regatt_gestiones` FOREIGN KEY (`idGestion`) REFERENCES `gestiones` (`idGestion`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_regatt_mad` FOREIGN KEY (`idModeloAtencion`) REFERENCES `modeloatenciondiferenciada` (`idModeloAtencion`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registraratencion`
--

LOCK TABLES `registraratencion` WRITE;
/*!40000 ALTER TABLE `registraratencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `registraratencion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-26 20:31:52
