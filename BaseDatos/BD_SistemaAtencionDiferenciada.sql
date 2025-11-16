CREATE DATABASE  IF NOT EXISTS `sistemaatenciondiferenciada` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sistemaatenciondiferenciada`;
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
-- Table structure for table `box_atencion`
--

DROP TABLE IF EXISTS `box_atencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `box_atencion` (
  `idBox` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'LIBRE',
  `id_colaborador` int DEFAULT NULL,
  PRIMARY KEY (`idBox`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box_atencion`
--

LOCK TABLES `box_atencion` WRITE;
/*!40000 ALTER TABLE `box_atencion` DISABLE KEYS */;
INSERT INTO `box_atencion` VALUES (1,'Atencion 1','Asignado',3),(2,'Atencion 2','Asignado',6),(3,'Atencion 3','Asignado',8),(4,'Atencion 4','Asignado',10),(5,'Atencion 5','LIBRE',12);
/*!40000 ALTER TABLE `box_atencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canal`
--

DROP TABLE IF EXISTS `canal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `canal` (
  `idCanal` int NOT NULL,
  `nombreCanal` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idCanal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canal`
--

LOCK TABLES `canal` WRITE;
/*!40000 ALTER TABLE `canal` DISABLE KEYS */;
INSERT INTO `canal` VALUES (1,'Sucursal'),(2,'Telefónico'),(3,'Mail'),(4,'Whatsapp'),(5,'Push');
/*!40000 ALTER TABLE `canal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` varchar(20) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `fechaIngreso` date DEFAULT NULL,
  `localidad` varchar(100) DEFAULT NULL,
  `Provincia` varchar(100) DEFAULT NULL,
  `dniCliente` int DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('1','Jorge','Luna','1980-05-03','2021-12-03','Cordoba','Cordoba',28114902),('10','Steven','Casillas','1981-01-02','2013-01-24','San Luis','San Luis',76142846),('100','Francesco','Marcelo','1988-09-11','2015-11-26','Córdoba','Córdoba',34561898),('101','Toni','Neymar','1980-09-18','2000-04-12','Resistencia','Chaco',13447889),('102','Gareth','Rivaldo','1981-09-06','2000-07-12','Córdoba','Córdoba',12847938),('103','Steven','Čech','2005-03-16','2023-10-27','Godoy Cruz','Mendoza',42205809),('104','Javier','Marcelo','1967-06-18','1989-09-06','Salta','Salta',38010513),('105','Johan','Tevez','1970-12-10','2006-09-11','Godoy Cruz','Mendoza',76856787),('106','Mario','Del Piero','1970-03-13','1994-08-20','Salta','Salta',75816314),('107','Marcelo','Higuaín','2000-08-25','2021-03-12','La Plata','Buenos Aires',63988933),('108','Toni','Higuaín','1977-08-31','2006-12-06','Godoy Cruz','Mendoza',29207321),('109','Giorgio','Seedorf','2003-12-16','2023-07-14','Mar del Plata','Buenos Aires',83492406),('11','Samuel','Tagliafico','1966-07-16','2006-11-25','CABA','Ciudad Autónoma de Buenos Aires',19880183),('110','Samuel','Benzema','1972-10-05','2009-08-08','Río Gallegos','Santa Cruz',21123183),('111','Andrea','Forlán','1987-11-12','2015-12-11','Bariloche','Río Negro',22335098),('112','Wesley','Ronaldo','1974-01-08','2000-05-13','San Luis','San Luis',62050564),('113','Ángel','Marcelo','1968-04-28','1989-10-17','Río Gallegos','Santa Cruz',28538406),('114','Esteban','Fernández','1979-07-02','2004-08-18','San Luis','San Luis',42716421),('115','Dani','Robben','1998-11-26','2021-05-19','Bariloche','Río Negro',95141910),('116','Andrea','Müller','1985-06-25','2015-11-09','CABA','Ciudad Autónoma de Buenos Aires',14777647),('117','Alessandro','Gaúcho','2007-05-08','2025-06-24','Bahía Blanca','Buenos Aires',59221925),('118','Xavi','Salah','1979-09-06','1998-12-20','Formosa','Formosa',69677754),('119','Casemiro','Totti','1977-06-22','2005-08-14','San Miguel de Tucumán','Tucumán',18653217),('12','Thierry','Aimar','1993-02-26','2012-11-02','Mar del Plata','Buenos Aires',59039965),('120','Gabriel','Dybala','1979-10-03','2006-07-26','Salta','Salta',54307160),('121','Gerard','Lahm','2002-11-14','2025-09-09','Río Gallegos','Santa Cruz',49729665),('122','Ronaldo','Henry','1985-10-25','2005-10-01','Bahía Blanca','Buenos Aires',47355505),('123','Virgil','Gómez','1975-04-25','2002-08-06','CABA','Ciudad Autónoma de Buenos Aires',11244575),('124','Arturo','Neuer','1987-10-18','2016-12-17','Santa Fe','Santa Fe',76982250),('125','Dimitar','Bale','2000-10-14','2023-02-09','Godoy Cruz','Mendoza',22606773),('126','Didier','Bale','1963-11-25','2023-01-09','Salta','Salta',80856478),('127','Ángel','van Dijk','2001-12-27','2024-12-07','Santa Fe','Santa Fe',28447759),('128','Leonardo','Di María','1968-06-04','2006-01-23','Bahía Blanca','Buenos Aires',52075812),('129','Keylor','Costa','1992-12-31','2015-01-10','Ushuaia','Tierra del Fuego',28590532),('13','Gennaro','Lo Celso','2006-02-07','2025-01-16','Río Gallegos','Santa Cruz',75468935),('130','Virgil','Silva','1963-10-28','2012-05-15','Villa Carlos Paz','Córdoba',81179609),('131','Juan','Gaúcho','1996-07-10','2024-04-06','La Plata','Buenos Aires',16292642),('132','Mohamed','Leite','1976-09-06','1996-09-07','Bariloche','Río Negro',27142648),('133','Philipp','Silva','1964-12-13','1988-05-30','Rawson','Chubut',81423393),('134','Javier','Drogba','1985-12-08','2021-10-05','Rosario','Santa Fe',99675703),('135','Philipp','Diogo','1985-04-20','2021-01-08','Bariloche','Río Negro',34453030),('136','Erling','Neuer','1984-08-03','2007-06-21','Santa Fe','Santa Fe',50489370),('137','Toni','Ayala','1998-10-28','2023-08-02','La Plata','Buenos Aires',92534365),('138','Dimitar','Modrić','2002-08-29','2021-05-11','Neuquén','Neuquén',80600791),('139','Tite','Pirlo','2003-04-19','2022-07-31','Posadas','Misiones',78851821),('14','Gabriel','Sneijder','1989-04-12','2009-03-22','Godoy Cruz','Mendoza',42356071),('140','Romario','Lampard','1969-12-27','2017-05-27','Godoy Cruz','Mendoza',36294355),('141','Ryan','Higuaín','1987-12-26','2020-06-26','Rosario','Santa Fe',58798609),('142','Marcelo','Del Piero','2005-09-30','2024-11-17','Corrientes','Corrientes',45010711),('143','Wayne','Zamorano','1961-04-24','2018-05-10','Salta','Salta',83229236),('144','Franck','Kahn','1971-02-10','2020-06-30','San Luis','San Luis',18497993),('145','Gianluigi','Zanetti','1989-10-25','2016-05-27','Mendoza','Mendoza',97037200),('146','Diego','Ramos','1999-06-10','2018-08-08','Corrientes','Corrientes',52052566),('147','Jair','Hernández','1960-12-22','1991-09-13','San Juan','San Juan',61528485),('148','Marco','Suárez','1998-09-05','2023-02-10','San Salvador de Jujuy','Jujuy',68813256),('149','Jude','Giggs','1966-08-11','2007-08-01','Santa Fe','Santa Fe',77692251),('15','Lionel','Casillas','1995-09-15','2015-03-13','San Miguel de Tucumán','Tucumán',38975679),('150','Toni','Sánchez','1960-12-28','2014-09-19','Mendoza','Mendoza',14603447),('151','Jorge','Lampard','1995-08-02','2015-07-10','Resistencia','Chaco',73557980),('152','Javier','Batistuta','1983-06-03','2017-07-01','Bariloche','Río Negro',79303649),('153','Andrés','Drogba','2005-02-26','2025-09-17','Posadas','Misiones',98100846),('154','Erling','Bale','1961-03-13','1996-10-26','CABA','Ciudad Autónoma de Buenos Aires',32459400),('155','Jair','Schweinsteiger','1961-06-25','2023-11-25','Bariloche','Río Negro',40207430),('156','Dani','Seedorf','1978-06-06','1999-06-01','Mar del Plata','Buenos Aires',96963442),('157','Gennaro','Crespo','1990-06-20','2012-04-16','Rawson','Chubut',92211970),('158','Mohamed','Viera','1996-12-19','2019-02-03','Formosa','Formosa',70612100),('159','Thiago','Pirlo','1965-07-25','1983-08-22','Rawson','Chubut',36122058),('16','Javier','Pirlo','1970-06-26','2000-06-19','Mendoza','Mendoza',39755627),('160','Ángel','Lampard','1999-04-26','2018-03-31','Villa Carlos Paz','Córdoba',20799285),('161','Jorge','Benzema','2003-03-01','2025-05-15','Corrientes','Corrientes',53798664),('162','Juan','Otamendi','1987-09-15','2023-09-17','Ushuaia','Tierra del Fuego',81936575),('163','Gabriel','Buffon','1965-01-18','1995-04-06','Rosario','Santa Fe',87107533),('164','Samuel','Júnior','1983-12-15','2023-02-27','Salta','Salta',27823325),('165','Rodrygo','Gómez','1960-12-08','2025-05-05','Río Gallegos','Santa Cruz',97214577),('166','Leonardo','Sánchez','1999-08-02','2022-08-08','Neuquén','Neuquén',43303475),('167','Virgil','Müller','2003-04-18','2024-05-13','Salta','Salta',88107810),('168','Walter','Ribéry','1975-02-23','2005-06-29','Rosario','Santa Fe',31330388),('169','Mohamed','van Dijk','1978-07-27','1998-02-19','Salta','Salta',98498465),('17','Thiago','Ronaldo','1995-03-04','2024-02-06','San Salvador de Jujuy','Jujuy',79042969),('170','Romario','Ribéry','2005-07-13','2023-09-27','Villa Carlos Paz','Córdoba',98295345),('171','Marcelo','Eto','2006-02-10','2024-05-04','Rawson','Chubut',16419567),('172','Karim','Salas','1999-08-11','2023-09-27','Mendoza','Mendoza',30288872),('173','Thomas','Lewandowski','1968-12-31','1987-12-13','Formosa','Formosa',80614373),('174','Francesco','Silva','1969-08-14','2023-10-10','Río Gallegos','Santa Cruz',87792872),('175','Andrés','Dybala','1984-04-10','2018-03-18','Corrientes','Corrientes',51982817),('176','Gerard','Beckham','1970-08-23','2016-12-23','Corrientes','Corrientes',16892498),('177','Frank','Ayala','1970-03-26','2017-07-18','Formosa','Formosa',87206494),('178','David','Modrić','2002-09-25','2022-06-02','San Salvador de Jujuy','Jujuy',21489702),('179','Arjen','Gómez','1975-06-19','2019-04-03','San Luis','San Luis',98355654),('18','Zico','De Bruyne','1962-05-19','2006-01-14','Rosario','Santa Fe',97462249),('180','Carlos','Sánchez','1970-04-29','2022-11-25','Formosa','Formosa',85306806),('181','Tite','Rivaldo','1992-03-16','2016-04-02','Resistencia','Chaco',72887454),('182','Carlos','Viera','2006-04-14','2025-09-21','La Plata','Buenos Aires',54786717),('183','Rafa','Gerrard','1984-08-26','2013-05-23','Bariloche','Río Negro',67868890),('184','Hugo','Costa','1991-12-26','2016-06-16','Villa Carlos Paz','Córdoba',83637859),('185','Javier','Giggs','1962-09-26','2015-10-10','Rawson','Chubut',11052086),('186','Luka','Chiellini','1973-09-02','1996-08-28','San Luis','San Luis',55574394),('187','Karim','Sánchez','2001-09-25','2023-01-27','CABA','Ciudad Autónoma de Buenos Aires',63668919),('188','Didier','Mbappé','1963-11-24','1994-06-15','Mendoza','Mendoza',19442183),('189','Francesco','Pirlo','1970-11-09','2022-10-09','Bahía Blanca','Buenos Aires',30086571),('19','Gonzalo','Di María','1997-09-12','2024-10-18','Formosa','Formosa',98661718),('190','Rafael','van Dijk','1968-01-28','2018-03-26','Bahía Blanca','Buenos Aires',87091345),('191','Didier','Casillas','2001-01-07','2020-10-06','Godoy Cruz','Mendoza',24588226),('192','Zico','Modrić','1986-12-28','2023-12-23','San Salvador de Jujuy','Jujuy',37565713),('193','Luis','Neymar','1961-07-24','1983-07-01','Santa Fe','Santa Fe',93904593),('194','Toni','Cavani','1988-01-27','2008-01-27','San Salvador de Jujuy','Jujuy',31439365),('195','Hugo','Henry','1973-06-06','2001-12-09','Resistencia','Chaco',14844869),('196','Juan','Buffon','1992-04-22','2015-10-08','Villa Carlos Paz','Córdoba',88385710),('197','Patrick','Dybala','1986-01-12','2008-01-24','Bahía Blanca','Buenos Aires',51043249),('198','Oscar','Ortega','1980-08-22','2016-06-09','Ushuaia','Tierra del Fuego',24291432),('199','Kaká','Chiellini','2002-08-08','2020-11-08','Corrientes','Corrientes',77051021),('2','Mohamed','Forlán','1964-11-19','2005-11-06','CABA','Ciudad Autónoma de Buenos Aires',8598766),('20','Paolo','Riquelme','1990-06-27','2018-02-09','Godoy Cruz','Mendoza',39161789),('200','Rivaldo','Hernández','1986-05-23','2012-07-21','Bariloche','Río Negro',28374104),('201','Francesco','Beckham','1985-12-17','2009-04-07','CABA','Ciudad Autónoma de Buenos Aires',74724265),('202','Andrea','Saviola','1975-02-12','2015-08-31','Rawson','Chubut',43631700),('203','Paulo','Aimar','2003-08-04','2024-04-25','Villa Carlos Paz','Córdoba',51808308),('204','Romario','Costa','1980-06-04','1999-07-23','San Salvador de Jujuy','Jujuy',48155023),('205','Bastian','Totti','1988-01-21','2024-08-10','Posadas','Misiones',24379513),('206','Andrea','Vidal','1982-11-16','2021-01-02','Salta','Salta',55575142),('207','Diego','Benzema','1978-06-22','2024-08-18','Formosa','Formosa',73789372),('208','Sergio','Schweinsteiger','1983-10-26','2011-03-25','Bariloche','Río Negro',42821698),('209','Leonardo','Leite','1995-02-09','2023-07-17','San Luis','San Luis',21265224),('21','Clarence','Ribéry','1979-11-18','2009-03-22','San Luis','San Luis',75515745),('210','Marcelo','Paredes','1979-10-19','2018-05-05','San Luis','San Luis',73921896),('211','Carlos','Ramos','1975-11-29','2004-01-08','Neuquén','Neuquén',43476815),('212','Radamel','Tevez','1970-06-07','2015-04-17','Salta','Salta',74152905),('213','Oscar','Leite','1990-02-25','2012-12-16','Rosario','Santa Fe',28897898),('214','Robert','Pepe','1963-10-31','2012-03-21','Villa Carlos Paz','Córdoba',65432814),('215','Steven','Milito','1969-07-13','2025-09-26','Mar del Plata','Buenos Aires',24717581),('216','Oscar','Goes','1990-11-14','2020-04-19','CABA','Ciudad Autónoma de Buenos Aires',67442417),('217','Juan','Fernández','1977-12-18','2024-09-18','Posadas','Misiones',46350034),('218','Arjen','Gaúcho','2007-03-18','2025-10-08','Mendoza','Mendoza',46348668),('219','Wesley','Pirlo','1965-05-05','1986-05-09','Bariloche','Río Negro',55034898),('22','Bastian','Falcao','1990-01-03','2017-07-10','Río Gallegos','Santa Cruz',71154996),('220','Juan Pablo','Marcelo','1970-02-27','2000-03-18','Córdoba','Córdoba',95519752),('221','Jair','Piqué','1992-02-01','2021-05-29','Resistencia','Chaco',91905489),('222','Arjen','Henry','1967-06-05','2015-11-09','Salta','Salta',39918234),('223','Ronaldo','Rivaldo','1993-10-22','2020-03-27','Neuquén','Neuquén',38207997),('224','Gennaro','Ribéry','1983-07-08','2007-06-14','San Salvador de Jujuy','Jujuy',73927865),('225','Claudio','Haaland','1963-10-04','2016-06-08','La Plata','Buenos Aires',33261277),('226','Sergio','Saviola','1983-11-05','2025-07-19','Neuquén','Neuquén',90496969),('227','Marcelo','Crespo','1976-08-06','2023-06-08','Bariloche','Río Negro',10190212),('228','Keylor','Sánchez','1979-08-13','2011-04-03','Posadas','Misiones',97829947),('229','Jair','Benzema','1984-06-29','2024-08-19','Villa Carlos Paz','Córdoba',40280719),('23','Johan','Milito','1986-10-23','2016-04-28','Formosa','Formosa',75283351),('230','Rivaldo','Crespo','1963-08-07','1991-09-21','Santa Fe','Santa Fe',12876840),('231','Robert','Moutinho','1964-01-19','2016-03-21','Posadas','Misiones',71066244),('232','Francesco','Gómez','1972-08-04','2022-10-27','Rawson','Chubut',31348219),('233','Ronaldo','van Dijk','1996-02-11','2018-07-22','Villa Carlos Paz','Córdoba',77436784),('234','Oliver','Diogo','1969-07-26','1995-11-08','Bariloche','Río Negro',16244611),('235','Paolo','Kroos','1984-02-13','2010-09-07','San Luis','San Luis',66793602),('236','Marco','Čech','2005-01-14','2025-06-16','Río Gallegos','Santa Cruz',43007665),('237','Jorge','Riquelme','1996-11-15','2023-05-17','Río Gallegos','Santa Cruz',67307538),('238','Javier','Benzema','1984-08-07','2021-12-20','San Salvador de Jujuy','Jujuy',16291075),('239','Bastian','Beckham','1989-07-20','2020-12-13','San Miguel de Tucumán','Tucumán',96061081),('24','Neymar','van Dijk','1988-12-14','2025-09-11','Godoy Cruz','Mendoza',49575763),('240','Kaká','van Dijk','1997-10-27','2019-11-19','Bariloche','Río Negro',87536747),('241','Ronaldo','Schweinsteiger','1993-03-12','2014-11-25','Río Gallegos','Santa Cruz',30842898),('242','Esteban','Giggs','1990-11-07','2018-07-24','CABA','Ciudad Autónoma de Buenos Aires',65616373),('243','Mohamed','Sánchez','1972-06-11','2005-12-29','Ushuaia','Tierra del Fuego',38701220),('244','Luis','Berbatov','1997-08-02','2021-06-09','San Salvador de Jujuy','Jujuy',25383850),('245','Virgil','Maldini','1977-04-15','2024-12-15','Salta','Salta',50088444),('246','Neymar','van Nistelrooy','1965-10-20','2017-05-11','Godoy Cruz','Mendoza',15811938),('247','James','Costa','1975-09-09','2018-07-11','Posadas','Misiones',59651281),('248','Frank','Aimar','1984-07-05','2022-03-02','Río Gallegos','Santa Cruz',86586736),('249','Gennaro','Costa','1969-01-26','1999-01-15','CABA','Ciudad Autónoma de Buenos Aires',78391444),('25','Esteban','Higuaín','1990-04-26','2025-07-21','Resistencia','Chaco',65945595),('250','Franck','Salah','1964-07-08','1990-02-24','San Luis','San Luis',84954169),('251','Xavi','Batistuta','1976-11-30','1995-04-02','Río Gallegos','Santa Cruz',64110577),('252','Jorge','Bale','1987-11-30','2017-01-06','Mendoza','Mendoza',10313526),('253','Edinson','Haaland','1969-05-12','2023-04-17','Corrientes','Corrientes',73108488),('254','Kylian','Tevez','1985-01-02','2016-09-05','Formosa','Formosa',50970341),('255','Ryan','Lahm','2000-11-15','2019-04-20','Santa Fe','Santa Fe',18484882),('256','Dimitar','Falcao','1986-08-18','2012-01-30','Mar del Plata','Buenos Aires',59524804),('257','Kevin','Messi','1997-10-09','2019-11-05','Bariloche','Río Negro',47707955),('258','Rafa','Fernández','2001-01-14','2020-07-09','Neuquén','Neuquén',89087290),('259','Frank','Leite','1991-04-25','2015-05-06','Formosa','Formosa',48889177),('26','Steven','Ramos','2000-09-06','2021-04-23','Rosario','Santa Fe',57864940),('260','Luka','Casillas','2005-08-13','2024-08-31','San Miguel de Tucumán','Tucumán',50760782),('261','Sadio','Robben','2001-08-12','2022-01-19','San Miguel de Tucumán','Tucumán',83628857),('262','Claudio','Paredes','1989-01-23','2008-11-07','Río Gallegos','Santa Cruz',54446715),('263','Renato','Moutinho','1982-05-09','2025-03-20','Ushuaia','Tierra del Fuego',79138148),('264','Claudio','Ayala','1985-08-14','2014-10-25','Posadas','Misiones',44816688),('265','Thomas','Rivaldo','1968-03-18','2025-01-22','Godoy Cruz','Mendoza',42912763),('266','David','Ribéry','1989-07-07','2019-05-25','San Juan','San Juan',78083437),('267','Thomas','Messi','1968-11-09','1996-11-11','San Salvador de Jujuy','Jujuy',93453439),('268','Mohamed','López','1991-05-02','2014-01-03','Posadas','Misiones',99624507),('269','Javier','Higuaín','1980-06-22','2023-11-16','Mendoza','Mendoza',16984243),('27','Zico','Aimar','2005-10-02','2023-11-09','San Luis','San Luis',63825726),('270','Rivaldo','Zamorano','1983-12-01','2017-06-18','Salta','Salta',51724832),('271','Javier','Vidal','2007-06-09','2025-09-27','San Salvador de Jujuy','Jujuy',22938824),('272','Javier','Salas','2001-08-09','2025-09-12','Ushuaia','Tierra del Fuego',53680367),('273','Petr','Haaland','1990-11-19','2018-08-26','Bahía Blanca','Buenos Aires',21670446),('274','Luis','Silva','1973-09-28','1995-10-09','Santa Fe','Santa Fe',51915117),('275','Jorge','Ortega','1969-02-12','2003-03-23','Rosario','Santa Fe',18465637),('276','Esteban','Beckham','2004-03-19','2025-08-31','San Miguel de Tucumán','Tucumán',92354193),('277','Cristiano','Alves','1974-08-13','2023-02-02','Bariloche','Río Negro',46354180),('278','Bastian','Salah','1969-10-28','2025-09-06','Posadas','Misiones',22504037),('279','Rivaldo','Totti','2001-04-11','2025-04-29','Salta','Salta',40068840),('28','Jude','Falcao','1990-03-16','2020-02-23','Godoy Cruz','Mendoza',17134505),('280','Edinson','Maldini','1995-07-26','2020-05-14','San Miguel de Tucumán','Tucumán',15284707),('281','Thomas','Di María','1976-07-07','1996-03-09','Villa Carlos Paz','Córdoba',46108661),('282','Tite','Alves','1964-12-24','2011-08-10','San Miguel de Tucumán','Tucumán',50326280),('283','Ángel','Casillas','1977-09-16','2001-07-14','CABA','Ciudad Autónoma de Buenos Aires',18839042),('284','Thierry','Sneijder','1992-07-11','2018-07-05','Bariloche','Río Negro',66949466),('285','Dani','Sánchez','1969-09-24','1996-10-23','Ushuaia','Tierra del Fuego',58898759),('286','Leonardo','Schweinsteiger','1988-02-28','2024-01-03','San Juan','San Juan',61823640),('287','Thomas','Zamorano','1972-01-03','1995-06-01','Formosa','Formosa',62590672),('288','Carlos','Moutinho','1995-01-20','2020-03-03','Rosario','Santa Fe',33400926),('289','Dimitar','Pepe','1993-05-03','2013-04-28','Mar del Plata','Buenos Aires',71111927),('29','Kevin','Paredes','1961-05-08','2009-09-18','Bahía Blanca','Buenos Aires',82725560),('290','Edinson','Vidal','2007-01-25','2025-03-31','Bariloche','Río Negro',14742614),('291','Ruud','Neymar','1966-05-17','2008-08-16','San Juan','San Juan',70636631),('292','Karim','Costa','1971-10-17','2016-07-03','Neuquén','Neuquén',74761132),('293','Giorgio','Modrić','1984-07-05','2024-06-03','Salta','Salta',87672695),('294','Oliver','Dybala','1967-02-20','1991-06-27','Bariloche','Río Negro',35304593),('295','Paolo','Rivaldo','1962-08-11','1984-05-15','Villa Carlos Paz','Córdoba',92006030),('296','Claudio','Marcelo','2002-02-04','2025-09-25','Córdoba','Córdoba',90724630),('297','Kevin','Ortega','1993-09-04','2016-04-22','Bariloche','Río Negro',90232296),('298','Luka','Müller','1988-05-18','2018-03-21','Resistencia','Chaco',35219594),('299','Marco','van Dijk','2000-10-05','2022-10-03','San Miguel de Tucumán','Tucumán',35954876),('3','Mohamed','Saviola','1982-02-21','2010-03-16','Santa Fe','Santa Fe',15780052),('30','Carlos','Dybala','1995-03-11','2017-11-04','San Salvador de Jujuy','Jujuy',96429899),('300','Juan Pablo','Messi','1974-10-06','2023-12-23','San Miguel de Tucumán','Tucumán',29881481),('301','Rafa','Lampard','1973-07-12','2014-11-01','San Luis','San Luis',95213543),('31','Marco','Beckham','1983-05-19','2004-01-25','San Miguel de Tucumán','Tucumán',93889175),('32','Gabriel','Benzema','1984-07-09','2002-12-15','Formosa','Formosa',68885917),('33','Arturo','Cavani','1965-06-26','2015-08-13','Posadas','Misiones',14044210),('34','Zico','Ortega','1966-05-06','1984-08-08','Neuquén','Neuquén',89597489),('35','Marco','Mascherano','1986-12-30','2009-11-24','Formosa','Formosa',93237425),('36','Gareth','Totti','1989-06-02','2014-02-08','Neuquén','Neuquén',61438377),('37','Keylor','Giggs','1965-12-26','2012-11-01','San Juan','San Juan',11156030),('38','Patrick','Falcao','1983-12-23','2013-02-14','San Miguel de Tucumán','Tucumán',60364386),('39','David','Lo Celso','2007-09-27','2025-10-25','La Plata','Buenos Aires',49575402),('4','Diego','Martínez','2003-12-13','2023-10-10','Mar del Plata','Buenos Aires',40807411),('40','Hugo','Salah','1967-10-15','2022-05-05','Villa Carlos Paz','Córdoba',79748620),('41','Juan','Batistuta','1975-02-24','1999-12-12','Ushuaia','Tierra del Fuego',46561467),('42','Lionel','Pepe','2004-02-18','2022-09-17','San Salvador de Jujuy','Jujuy',40627480),('43','Diego','Totti','1982-05-21','2003-03-18','Formosa','Formosa',45635316),('44','Oliver','Sánchez','1978-01-22','2016-07-23','Bariloche','Río Negro',17353849),('45','Bastian','Goes','1978-11-12','2005-06-18','Corrientes','Corrientes',74409723),('46','Sergio','Mascherano','1967-02-19','2017-01-01','Corrientes','Corrientes',32664003),('47','Renato','Schweinsteiger','1976-09-14','2020-01-27','Santa Fe','Santa Fe',28351157),('48','Alessandro','Aimar','1983-02-24','2014-11-11','La Plata','Buenos Aires',83010015),('49','Petr','Ayala','1989-01-11','2021-05-09','San Salvador de Jujuy','Jujuy',20085327),('5','Edinson','Casillas','1992-10-18','2023-07-24','La Plata','Buenos Aires',30737546),('50','Thierry','Drogba','1993-04-14','2013-12-30','San Miguel de Tucumán','Tucumán',36217751),('51','Lionel','Mbappé','1971-06-23','1991-05-21','Bahía Blanca','Buenos Aires',30457147),('52','Javier','Gattuso','1962-02-18','1982-04-12','Córdoba','Córdoba',69720648),('53','Paulo','Martínez','1982-03-23','2024-05-21','La Plata','Buenos Aires',25935180),('54','Diego','Batistuta','1961-03-18','2015-11-06','Santa Fe','Santa Fe',37086998),('55','Tite','Sneijder','1991-07-29','2018-11-22','Río Gallegos','Santa Cruz',34070084),('56','Giorgio','Lahm','1983-01-30','2002-11-26','Córdoba','Córdoba',14893865),('57','Esteban','Schweinsteiger','1988-11-04','2010-08-19','Bariloche','Río Negro',28826973),('58','Francesco','Pepe','2000-01-02','2018-09-14','Godoy Cruz','Mendoza',85247012),('59','Diego','Modrić','1979-04-12','2009-11-07','Mendoza','Mendoza',23121959),('6','Leonardo','Robben','2006-01-30','2024-03-15','Posadas','Misiones',24944644),('60','Paulo','Marcelo','2000-09-21','2021-04-23','Mar del Plata','Buenos Aires',36064928),('61','Jorge','Ramos','2002-10-03','2022-11-06','Formosa','Formosa',31437402),('62','Neymar','Milito','1984-05-21','2021-04-11','Mendoza','Mendoza',58723604),('63','Gabriel','Viera','1981-04-17','2001-05-28','Mendoza','Mendoza',62481944),('64','Kaká','Robben','1982-09-05','2017-01-02','Rosario','Santa Fe',78453708),('65','Neymar','Schweinsteiger','1991-03-31','2013-10-12','San Salvador de Jujuy','Jujuy',41281262),('66','Mohamed','Goes','1987-03-21','2013-08-17','Rosario','Santa Fe',82418808),('67','Antoine','Alves','1971-07-09','2007-01-15','Formosa','Formosa',27010109),('68','Paolo','Maldini','1965-05-11','2013-10-08','Neuquén','Neuquén',13379070),('69','Gerard','Bravo','1971-08-26','2025-04-28','Resistencia','Chaco',69818932),('7','Thiago','Seedorf','1976-02-11','2004-02-09','San Juan','San Juan',60395907),('70','Manuel','Otamendi','1962-04-13','1995-09-17','La Plata','Buenos Aires',44713918),('71','Wayne','Messi','1963-02-14','2014-10-31','Salta','Salta',73661114),('72','Radamel','Cavani','1969-08-18','1999-09-04','San Luis','San Luis',16669258),('73','Paulo','Kroos','1998-07-08','2021-08-20','Resistencia','Chaco',57189700),('74','Thierry','Schweinsteiger','1986-12-08','2018-12-06','Bahía Blanca','Buenos Aires',51505233),('75','Andrés','López','1963-06-21','1996-01-30','Corrientes','Corrientes',39702126),('76','Gareth','Ribéry','1991-08-29','2024-05-24','Formosa','Formosa',20057001),('77','Romario','Kahn','2000-04-28','2021-02-24','Córdoba','Córdoba',96508934),('78','Andrés','Vidal','1965-12-10','1993-09-03','Rawson','Chubut',24043254),('79','Tite','Marcelo','1991-06-01','2020-10-20','Salta','Salta',66087089),('8','Franck','Messi','1986-01-04','2018-01-03','Neuquén','Neuquén',95108713),('80','Sadio','Schweinsteiger','1961-05-18','2002-03-14','Posadas','Misiones',87790220),('81','Marcelo','Beckham','1973-06-01','2018-03-06','La Plata','Buenos Aires',32022560),('82','Wayne','Mascherano','1980-09-19','2014-06-26','San Juan','San Juan',65252438),('83','Dimitar','Tevez','1975-06-30','2013-03-22','Godoy Cruz','Mendoza',24114043),('84','Marcelo','Mascherano','1993-04-12','2014-06-22','Resistencia','Chaco',13678459),('85','Paolo','Falcao','1965-09-09','2010-08-11','Santa Fe','Santa Fe',41426054),('86','Oscar','Costa','1983-02-26','2021-11-28','Bariloche','Río Negro',48130655),('87','Ruud','Benzema','1999-05-22','2023-03-20','Godoy Cruz','Mendoza',47074484),('88','Paulo','Bonucci','1984-07-12','2004-09-10','Santa Fe','Santa Fe',43109672),('89','Frank','Buffon','1999-03-14','2023-09-18','Córdoba','Córdoba',42546173),('9','Petr','Tevez','1999-10-11','2018-05-12','Córdoba','Córdoba',84696547),('90','Gabriel','Leite','1970-03-21','1989-07-09','La Plata','Buenos Aires',88541498),('91','Sergio','Benzema','1974-02-11','2013-12-04','San Miguel de Tucumán','Tucumán',52807482),('92','Thomas','Neuer','1972-10-16','2022-01-09','Neuquén','Neuquén',74062548),('93','Mario','Paredes','1979-03-08','2003-04-20','San Miguel de Tucumán','Tucumán',20049478),('94','Rivaldo','Viera','1994-01-10','2016-07-09','Santa Fe','Santa Fe',59542108),('95','Jude','Milito','1970-11-08','2000-12-31','Salta','Salta',12646340),('96','Juan Pablo','De Bruyne','1990-12-16','2012-12-28','Bahía Blanca','Buenos Aires',47959526),('97','Javier','Falcao','2006-09-12','2025-01-03','San Luis','San Luis',10973792),('98','Neymar','Pepe','1972-06-04','2002-06-11','La Plata','Buenos Aires',73264945),('99','Lionel','Lewandowski','1989-03-31','2021-05-29','Bahía Blanca','Buenos Aires',78225614);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaborador`
--

DROP TABLE IF EXISTS `colaborador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaborador` (
  `id_colaborador` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `fechaIngreso` date DEFAULT NULL,
  `idRol` int DEFAULT NULL,
  `legajo` int DEFAULT NULL,
  PRIMARY KEY (`id_colaborador`),
  KEY `idx_colaborador_idRol` (`idRol`),
  CONSTRAINT `fk_colaborador_rol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaborador`
--

LOCK TABLES `colaborador` WRITE;
/*!40000 ALTER TABLE `colaborador` DISABLE KEYS */;
INSERT INTO `colaborador` VALUES (1,'Roger','Arias','1981-08-08','2005-01-10',6,1001),(2,'Rafael','Ardiles','1986-06-03','2007-03-15',6,1002),(3,'Novak','Rajek','1987-05-22','2010-04-12',1,1003),(4,'Andy','Murray','1987-05-15','2012-09-01',1,1004),(5,'Serena','Moreno','1981-09-26','2004-02-11',2,1005),(6,'Marianela','Stich','1980-06-17','2003-08-19',2,1006),(7,'Andre','Sonart','1970-04-29','1998-03-10',3,1007),(8,'Pedro','Fernandez','1971-08-12','1999-01-20',3,1008),(9,'Steffi','Camargo','1969-06-14','1995-01-01',4,1009),(10,'Martina','Lopez','1956-10-18','1990-09-10',4,1010),(11,'Maria','Campos','1987-04-19','2014-11-21',5,1011),(12,'Simona','Galarza','1991-09-27','2016-03-13',5,1012),(13,'Gustavo','Kuerten','1976-09-10','2001-07-30',2,1013),(14,'Juan Martin','Caligari','1988-09-23','2014-10-05',3,1014),(15,'Carlos','Fernandez','2003-05-05','2022-03-17',4,1015),(16,'Iga','Swiatek','2001-05-31','2021-08-02',5,1016),(17,'Leylah','Fernandez','2002-09-06','2023-06-10',4,1017),(18,'Naomi','Osaka','1997-10-16','2018-05-12',3,1018),(19,'Stan','Wawrinka','1985-03-28','2010-09-18',2,1019),(20,'Dominic','Thiem','1993-09-03','2019-02-10',1,1020);
/*!40000 ALTER TABLE `colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestiones`
--

DROP TABLE IF EXISTS `gestiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestiones` (
  `idGestion` int NOT NULL,
  `nombreGestion` varchar(100) DEFAULT NULL,
  `prioridad` int DEFAULT NULL,
  `orden` int DEFAULT NULL,
  `esDiferencial` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idGestion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestiones`
--

LOCK TABLES `gestiones` WRITE;
/*!40000 ALTER TABLE `gestiones` DISABLE KEYS */;
INSERT INTO `gestiones` VALUES (1,'Reclamo',1,1,'S'),(2,'Consulta',5,5,'N'),(3,'Préstamos',3,2,'N'),(4,'Otros',6,6,'N'),(5,'Activación de tarjeta',4,4,'N'),(6,'Bloqueo por robo o extravío',1,1,'S'),(7,'Desconocimiento de consumos',2,2,'S'),(8,'Aumento de límite',3,3,'S'),(9,'Cambio de PIN',5,5,'N'),(10,'Problemas con transferencia',2,2,'S'),(11,'Problemas con carga de saldo',3,3,'N'),(12,'Reverso de pago',4,4,'S'),(13,'Rechazo de compra',4,4,'N'),(14,'Adhesión a débito automático',6,6,'N'),(15,'Baja de tarjeta',5,5,'N'),(16,'Modificación de datos personales',8,8,'N'),(17,'Gestión de seguridad / phishing',2,2,'S'),(18,'Reclamo por comisiones',7,7,'N'),(19,'Devolución de compra',5,5,'N'),(20,'Problemas con CVU / alias',4,4,'N'),(21,'Gestión de tarjetas adicionales',6,6,'N'),(22,'Solicitud de resumen',7,7,'N'),(23,'Error en cierre / facturación',3,3,'S'),(24,'Habilitación para viajar al exterior',6,6,'N');
/*!40000 ALTER TABLE `gestiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelo`
--

DROP TABLE IF EXISTS `modelo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modelo` (
  `idModelo` int NOT NULL,
  `periodo` varchar(50) DEFAULT NULL,
  `rangoFrecuencia` varchar(50) DEFAULT NULL,
  `rangoRecencia` varchar(50) DEFAULT NULL,
  `rangoMonto` varchar(50) DEFAULT NULL,
  `cantSegmentos` int DEFAULT NULL,
  `rangoSegmentos` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idModelo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelo`
--

LOCK TABLES `modelo` WRITE;
/*!40000 ALTER TABLE `modelo` DISABLE KEYS */;
INSERT INTO `modelo` VALUES (1,'202501','10','9','8',1,'ALTO');
/*!40000 ALTER TABLE `modelo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modeloatenciondiferenciada`
--

DROP TABLE IF EXISTS `modeloatenciondiferenciada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modeloatenciondiferenciada` (
  `idModeloAtencion` int NOT NULL AUTO_INCREMENT,
  `frecuencia` int DEFAULT NULL,
  `recencia` int DEFAULT NULL,
  `monto` decimal(18,2) DEFAULT NULL,
  `idModelo` int DEFAULT NULL,
  `idCliente` varchar(20) DEFAULT NULL,
  `segmento` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idModeloAtencion`),
  KEY `idx_mad_idModelo` (`idModelo`),
  KEY `idx_mad_dniCliente` (`idCliente`),
  CONSTRAINT `fk_mad_cliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_mad_modelo` FOREIGN KEY (`idModelo`) REFERENCES `modelo` (`idModelo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modeloatenciondiferenciada`
--

LOCK TABLES `modeloatenciondiferenciada` WRITE;
/*!40000 ALTER TABLE `modeloatenciondiferenciada` DISABLE KEYS */;
INSERT INTO `modeloatenciondiferenciada` VALUES (1,2,3,150.00,1,'1','ALTO'),(2,17,82,21965.86,1,'1','MEDIO'),(3,29,4,196245.12,1,'2','ALTO'),(4,10,67,23699.39,1,'3','MEDIO BAJO'),(5,0,132,5841.78,1,'4','BAJO'),(6,14,87,22084.47,1,'5','MEDIO'),(7,50,9,89613.38,1,'6','ALTO'),(8,17,12,87602.81,1,'7','MEDIO ALTO'),(9,16,50,33169.90,1,'8','MEDIO'),(10,5,134,19862.98,1,'9','MEDIO BAJO'),(11,15,30,33865.12,1,'10','MEDIO'),(12,15,86,29236.64,1,'11','MEDIO'),(13,6,86,21743.15,1,'12','MEDIO BAJO'),(14,2,181,2656.15,1,'13','BAJO'),(15,9,100,28225.46,1,'15','MEDIO BAJO'),(16,0,122,4582.86,1,'16','BAJO'),(17,4,145,732.61,1,'17','BAJO'),(18,5,124,11630.01,1,'18','MEDIO BAJO'),(19,7,172,6719.78,1,'19','MEDIO BAJO'),(20,27,6,114198.49,1,'20','ALTO'),(21,9,166,18580.45,1,'21','MEDIO BAJO'),(22,10,87,15352.04,1,'22','MEDIO'),(23,10,98,28293.90,1,'23','MEDIO BAJO'),(24,3,180,26953.17,1,'24','MEDIO BAJO'),(25,28,2,186765.52,1,'25','ALTO'),(26,10,79,11808.71,1,'26','MEDIO BAJO'),(27,18,32,67430.42,1,'27','MEDIO ALTO'),(28,16,53,37743.41,1,'28','MEDIO'),(29,6,175,27703.98,1,'29','MEDIO BAJO'),(30,2,71,20857.11,1,'30','MEDIO BAJO'),(31,10,25,15159.56,1,'31','MEDIO'),(32,18,36,43670.10,1,'32','MEDIO'),(33,28,3,168947.71,1,'33','ALTO'),(34,14,74,40240.81,1,'34','MEDIO'),(35,15,12,115604.08,1,'35','MEDIO ALTO'),(36,4,129,5900.70,1,'36','MEDIO BAJO'),(37,7,134,18813.13,1,'37','MEDIO BAJO'),(38,33,3,85019.46,1,'38','ALTO'),(39,31,15,191970.56,1,'39','ALTO'),(40,2,175,13945.23,1,'40','MEDIO BAJO'),(41,5,145,7569.84,1,'41','MEDIO BAJO'),(42,10,173,26867.82,1,'42','MEDIO BAJO'),(43,16,39,114390.48,1,'43','MEDIO ALTO'),(44,4,162,25269.29,1,'44','MEDIO BAJO'),(45,3,126,1793.61,1,'45','BAJO'),(46,11,72,51100.58,1,'46','MEDIO'),(47,19,18,52735.83,1,'47','MEDIO ALTO'),(48,5,147,3825.42,1,'48','BAJO'),(49,13,37,77655.20,1,'49','MEDIO ALTO'),(50,5,164,27961.30,1,'50','MEDIO BAJO'),(51,11,59,51924.14,1,'51','MEDIO'),(52,9,48,16064.90,1,'52','MEDIO'),(53,1,222,3282.62,1,'53','BAJO'),(54,3,158,11978.12,1,'54','MEDIO BAJO'),(55,10,111,21987.95,1,'55','MEDIO BAJO'),(56,29,20,115138.97,1,'56','MEDIO ALTO'),(57,3,172,29260.02,1,'57','MEDIO BAJO'),(58,1,268,9625.34,1,'58','BAJO'),(59,6,64,7710.06,1,'59','MEDIO BAJO'),(60,8,104,23213.63,1,'60','MEDIO BAJO'),(61,11,75,42279.40,1,'61','MEDIO'),(62,14,34,32333.79,1,'62','MEDIO'),(63,5,92,6109.66,1,'63','MEDIO BAJO'),(64,8,60,17998.01,1,'64','MEDIO BAJO'),(65,10,147,22985.75,1,'65','MEDIO BAJO'),(66,18,21,74503.79,1,'66','MEDIO ALTO'),(67,16,62,43041.99,1,'67','MEDIO'),(68,3,152,27501.17,1,'68','MEDIO BAJO'),(69,10,99,21672.32,1,'69','MEDIO BAJO'),(70,7,111,22430.21,1,'70','MEDIO BAJO'),(71,14,36,23632.80,1,'71','MEDIO'),(72,5,360,3791.45,1,'72','BAJO'),(73,17,42,42698.17,1,'73','MEDIO'),(74,21,22,83834.98,1,'74','MEDIO ALTO'),(75,2,98,12172.84,1,'75','MEDIO BAJO'),(76,8,160,19499.62,1,'76','MEDIO BAJO'),(77,16,61,35924.47,1,'77','MEDIO'),(78,13,47,38002.83,1,'78','MEDIO'),(79,18,41,44648.28,1,'79','MEDIO'),(80,2,251,6638.54,1,'80','BAJO'),(81,7,71,25460.59,1,'81','MEDIO BAJO'),(82,1,292,3104.19,1,'82','BAJO'),(83,18,45,21630.93,1,'83','MEDIO'),(84,2,91,29598.40,1,'84','MEDIO BAJO'),(85,15,29,35493.73,1,'85','MEDIO'),(86,16,44,47325.57,1,'86','MEDIO'),(87,32,8,127958.34,1,'87','ALTO'),(88,4,143,22190.56,1,'88','MEDIO BAJO'),(89,18,33,50028.92,1,'89','MEDIO'),(90,27,3,176493.18,1,'90','ALTO'),(91,10,119,6255.46,1,'91','MEDIO BAJO'),(92,9,35,35540.78,1,'92','MEDIO'),(93,9,145,18278.30,1,'93','MEDIO BAJO'),(94,10,136,12932.45,1,'94','MEDIO BAJO'),(95,9,138,25375.16,1,'95','MEDIO BAJO'),(96,14,74,52375.61,1,'96','MEDIO'),(97,14,77,55374.36,1,'97','MEDIO'),(98,9,117,11479.79,1,'98','MEDIO BAJO'),(99,5,167,20941.54,1,'99','MEDIO BAJO'),(100,18,86,36806.87,1,'100','MEDIO'),(101,19,18,75189.81,1,'101','MEDIO ALTO'),(102,5,193,2344.94,1,'102','BAJO'),(103,7,100,27325.67,1,'103','MEDIO BAJO'),(104,22,3,98099.68,1,'104','ALTO'),(105,12,39,46788.86,1,'105','MEDIO'),(106,14,23,72607.95,1,'106','MEDIO ALTO'),(107,37,8,129892.91,1,'107','ALTO'),(108,18,36,73611.48,1,'108','MEDIO ALTO'),(109,2,169,27016.42,1,'109','MEDIO BAJO'),(110,8,121,5147.40,1,'110','MEDIO BAJO'),(111,2,196,7534.75,1,'111','BAJO'),(112,3,257,7475.16,1,'112','BAJO'),(113,4,324,6032.53,1,'113','BAJO'),(114,19,25,57553.73,1,'114','MEDIO ALTO'),(115,3,244,290.25,1,'115','BAJO'),(116,22,31,94331.35,1,'116','MEDIO ALTO'),(117,8,152,9126.12,1,'117','MEDIO BAJO'),(118,9,177,8190.69,1,'118','MEDIO BAJO'),(119,10,63,27673.22,1,'119','MEDIO BAJO'),(120,4,264,6630.00,1,'120','BAJO'),(121,22,11,131433.53,1,'121','ALTO'),(122,13,43,17262.81,1,'122','MEDIO'),(123,12,61,24524.71,1,'123','MEDIO'),(124,22,20,100898.38,1,'124','MEDIO ALTO'),(125,12,55,48840.44,1,'125','MEDIO'),(126,3,184,8349.11,1,'126','BAJO'),(127,9,62,23725.61,1,'127','MEDIO BAJO'),(128,2,104,10605.55,1,'128','MEDIO BAJO'),(129,7,25,48940.49,1,'129','MEDIO'),(130,9,45,52765.30,1,'130','MEDIO'),(131,4,90,8155.52,1,'131','MEDIO BAJO'),(132,15,28,115820.56,1,'132','MEDIO ALTO'),(133,26,32,60499.50,1,'133','MEDIO ALTO'),(134,31,3,152707.12,1,'134','ALTO'),(135,17,34,50000.63,1,'135','MEDIO'),(136,4,99,7702.47,1,'136','MEDIO BAJO'),(137,2,178,12798.93,1,'137','MEDIO BAJO'),(138,8,110,28536.76,1,'138','MEDIO BAJO'),(139,9,29,41644.40,1,'139','MEDIO'),(140,19,13,95775.44,1,'140','MEDIO ALTO'),(141,2,337,6842.59,1,'141','BAJO'),(142,3,161,29596.11,1,'142','MEDIO BAJO'),(143,0,208,5327.45,1,'143','BAJO'),(144,7,68,17649.02,1,'144','MEDIO BAJO'),(145,30,1,181949.08,1,'145','ALTO'),(146,9,73,15838.09,1,'146','MEDIO BAJO'),(147,11,78,46828.89,1,'147','MEDIO'),(148,8,82,23344.88,1,'148','MEDIO BAJO'),(149,16,54,42716.06,1,'149','MEDIO'),(150,10,159,17087.44,1,'150','MEDIO BAJO'),(151,8,165,23275.66,1,'151','MEDIO BAJO'),(152,10,61,53327.09,1,'152','MEDIO'),(153,3,95,27040.78,1,'153','MEDIO BAJO'),(154,19,34,77176.68,1,'154','MEDIO ALTO'),(155,15,68,30137.85,1,'155','MEDIO'),(156,9,168,13125.35,1,'156','MEDIO BAJO'),(157,13,47,30967.12,1,'157','MEDIO'),(158,10,63,27584.63,1,'158','MEDIO'),(159,39,12,185612.88,1,'159','ALTO'),(160,29,10,81330.18,1,'160','MEDIO ALTO'),(161,5,70,11033.90,1,'161','MEDIO BAJO'),(162,12,82,39982.93,1,'162','MEDIO'),(163,5,148,16902.33,1,'163','MEDIO BAJO'),(164,9,117,24823.32,1,'164','MEDIO BAJO'),(165,3,97,10539.91,1,'165','MEDIO BAJO'),(166,5,99,21599.00,1,'166','MEDIO BAJO'),(167,7,120,18836.82,1,'167','MEDIO BAJO'),(168,7,114,29915.79,1,'168','MEDIO BAJO'),(169,14,62,30831.47,1,'169','MEDIO'),(170,9,94,12665.57,1,'170','MEDIO BAJO'),(171,9,35,47457.79,1,'171','MEDIO'),(172,7,75,23572.03,1,'172','MEDIO BAJO'),(173,4,84,10409.62,1,'173','MEDIO BAJO'),(174,35,5,166950.20,1,'174','ALTO'),(175,4,272,2829.99,1,'175','BAJO'),(176,3,166,9852.94,1,'176','MEDIO BAJO'),(177,5,106,9486.04,1,'177','MEDIO BAJO'),(178,6,88,20696.23,1,'178','MEDIO'),(179,6,26,39901.15,1,'179','MEDIO'),(180,4,141,26711.53,1,'180','MEDIO BAJO'),(181,9,73,26817.99,1,'181','MEDIO BAJO'),(182,30,19,77551.76,1,'182','MEDIO ALTO'),(183,9,103,9609.09,1,'183','MEDIO BAJO'),(184,2,92,28526.59,1,'184','MEDIO BAJO'),(185,9,74,25552.89,1,'185','MEDIO BAJO'),(186,8,122,6852.05,1,'186','MEDIO BAJO'),(187,2,79,8729.94,1,'187','MEDIO BAJO'),(188,6,70,29847.95,1,'188','MEDIO BAJO'),(189,3,131,24111.09,1,'189','MEDIO BAJO'),(190,19,34,81805.80,1,'190','MEDIO ALTO'),(191,26,39,75417.68,1,'191','MEDIO ALTO'),(192,15,74,28742.11,1,'192','MEDIO'),(193,39,1,153155.71,1,'193','ALTO'),(194,3,157,10194.70,1,'194','MEDIO BAJO'),(195,5,93,21510.70,1,'195','MEDIO BAJO'),(196,8,50,22821.61,1,'196','MEDIO'),(197,3,80,5066.89,1,'197','MEDIO BAJO'),(198,9,148,19845.28,1,'198','MEDIO BAJO'),(199,6,64,10786.91,1,'199','MEDIO BAJO'),(200,6,149,26490.17,1,'200','MEDIO BAJO'),(201,3,147,10835.60,1,'201','MEDIO BAJO'),(202,6,160,24781.19,1,'202','MEDIO BAJO'),(203,5,114,7869.41,1,'203','MEDIO BAJO'),(204,5,142,8724.74,1,'204','MEDIO BAJO'),(205,10,38,18213.44,1,'205','MEDIO'),(206,25,13,116913.42,1,'206','ALTO'),(207,43,14,148304.85,1,'207','ALTO'),(208,21,24,49948.29,1,'208','MEDIO ALTO'),(209,21,32,72200.68,1,'209','MEDIO ALTO'),(210,10,84,39299.17,1,'210','MEDIO'),(211,13,30,41911.65,1,'211','MEDIO'),(212,8,154,13058.04,1,'212','MEDIO BAJO'),(213,10,23,19109.71,1,'213','MEDIO'),(214,2,157,21807.04,1,'214','MEDIO BAJO'),(215,2,267,402.32,1,'215','BAJO'),(216,18,42,36172.54,1,'216','MEDIO'),(217,5,233,9160.73,1,'217','BAJO'),(218,17,28,74870.80,1,'218','MEDIO ALTO'),(219,9,71,16750.26,1,'219','MEDIO BAJO'),(220,12,62,29447.62,1,'220','MEDIO'),(221,23,14,99298.98,1,'221','ALTO'),(222,8,148,17386.73,1,'222','MEDIO BAJO'),(223,16,71,51610.47,1,'223','MEDIO'),(224,14,24,35467.17,1,'224','MEDIO'),(225,30,5,118792.85,1,'225','ALTO'),(226,44,7,183789.27,1,'226','ALTO'),(227,6,89,35788.94,1,'227','MEDIO'),(228,13,16,81475.15,1,'228','MEDIO ALTO'),(229,27,30,75363.28,1,'229','MEDIO ALTO'),(230,0,172,2670.56,1,'230','BAJO'),(231,4,178,12200.73,1,'231','MEDIO BAJO'),(232,27,13,42309.56,1,'232','MEDIO ALTO'),(233,19,32,52670.25,1,'233','MEDIO ALTO'),(234,29,10,84180.11,1,'234','MEDIO ALTO'),(235,3,88,29806.43,1,'235','MEDIO BAJO'),(236,3,119,28676.52,1,'236','MEDIO BAJO'),(237,16,25,114612.24,1,'237','MEDIO ALTO'),(238,6,125,22635.29,1,'238','MEDIO BAJO'),(239,12,81,59111.01,1,'239','MEDIO'),(240,27,8,146151.81,1,'240','ALTO'),(241,8,84,28046.70,1,'241','MEDIO BAJO'),(242,14,37,53881.21,1,'242','MEDIO'),(243,20,34,103200.49,1,'243','MEDIO ALTO'),(244,25,20,114739.87,1,'244','MEDIO ALTO'),(245,14,54,51924.73,1,'245','MEDIO'),(246,29,12,115826.70,1,'246','ALTO'),(247,38,10,199868.28,1,'247','ALTO'),(248,13,39,35094.63,1,'248','MEDIO'),(249,9,104,13311.00,1,'249','MEDIO BAJO'),(250,18,89,31974.45,1,'250','MEDIO'),(251,49,6,184352.20,1,'251','ALTO'),(252,42,4,148615.72,1,'252','ALTO'),(253,19,37,102043.56,1,'253','MEDIO ALTO'),(254,13,20,99576.41,1,'254','MEDIO ALTO'),(255,17,68,32373.54,1,'255','MEDIO'),(256,16,39,37289.79,1,'256','MEDIO'),(257,13,14,80183.29,1,'257','MEDIO ALTO'),(258,7,171,7509.12,1,'258','MEDIO BAJO'),(259,3,145,5259.51,1,'259','BAJO'),(260,9,61,23061.10,1,'260','MEDIO BAJO'),(261,12,39,18367.27,1,'261','MEDIO'),(262,20,20,89860.75,1,'262','MEDIO ALTO'),(263,8,143,7007.88,1,'263','MEDIO BAJO'),(264,7,169,21858.94,1,'264','MEDIO BAJO'),(265,10,108,28853.49,1,'265','MEDIO BAJO'),(266,9,171,18525.40,1,'266','MEDIO BAJO'),(267,15,28,25564.66,1,'267','MEDIO'),(268,16,56,59884.17,1,'268','MEDIO'),(269,14,23,118267.02,1,'269','MEDIO ALTO'),(270,15,24,53309.18,1,'270','MEDIO ALTO'),(271,21,38,42315.78,1,'271','MEDIO ALTO'),(272,22,35,44489.54,1,'272','MEDIO ALTO'),(273,7,107,15767.17,1,'273','MEDIO BAJO'),(274,5,127,15301.29,1,'274','MEDIO BAJO'),(275,4,81,9376.73,1,'275','MEDIO BAJO'),(276,15,68,42886.97,1,'276','MEDIO'),(277,19,25,113026.32,1,'277','MEDIO ALTO'),(278,1,179,4611.50,1,'278','BAJO'),(279,6,118,11383.17,1,'279','MEDIO BAJO'),(280,0,350,8046.33,1,'280','BAJO'),(281,10,89,22108.90,1,'281','MEDIO'),(282,13,64,59444.33,1,'282','MEDIO'),(283,6,141,29050.73,1,'283','MEDIO BAJO'),(284,20,24,107624.31,1,'284','MEDIO ALTO'),(285,18,22,108266.84,1,'285','MEDIO ALTO'),(286,3,90,14534.11,1,'286','MEDIO BAJO'),(287,7,133,12396.81,1,'287','MEDIO BAJO'),(288,6,62,29448.61,1,'288','MEDIO BAJO'),(289,16,70,27351.62,1,'289','MEDIO'),(290,2,176,20160.16,1,'290','MEDIO BAJO'),(291,3,333,9041.55,1,'291','BAJO'),(292,29,13,175863.21,1,'292','ALTO'),(293,4,325,3523.21,1,'293','BAJO'),(294,5,139,11265.67,1,'294','MEDIO BAJO'),(295,4,140,7429.10,1,'295','MEDIO BAJO'),(296,40,11,84730.71,1,'296','ALTO'),(297,18,76,16501.40,1,'297','MEDIO'),(298,31,12,95769.42,1,'298','ALTO'),(299,6,101,23687.02,1,'299','MEDIO BAJO'),(300,8,45,20947.19,1,'300','MEDIO');
/*!40000 ALTER TABLE `modeloatenciondiferenciada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registraratencion`
--

DROP TABLE IF EXISTS `registraratencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registraratencion` (
  `idAtencion` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `idCliente` varchar(20) DEFAULT NULL,
  `idCanal` int DEFAULT NULL,
  `idModeloAtencion` int DEFAULT NULL,
  `legajo` int DEFAULT NULL,
  `observaciones` text,
  `idGestion` int DEFAULT NULL,
  PRIMARY KEY (`idAtencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registraratencion`
--

LOCK TABLES `registraratencion` WRITE;
/*!40000 ALTER TABLE `registraratencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `registraratencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int NOT NULL,
  `descripcionRol` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Experto'),(2,'Senior'),(3,'Semi Senior'),(4,'Junior'),(5,'Trainee'),(6,'Encargado');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnero`
--

DROP TABLE IF EXISTS `turnero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnero` (
  `idTurnero` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `idCliente` varchar(20) DEFAULT NULL,
  `codigoTurno` varchar(20) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `idGestion` int DEFAULT NULL,
  `prioridad` int DEFAULT NULL,
  `segmentoScore` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  `idBox` int DEFAULT NULL,
  `orden` int DEFAULT NULL,
  PRIMARY KEY (`idTurnero`),
  KEY `idx_turnero_score` (`score`),
  KEY `idx_turnero_estado_fecha` (`estado`,`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnero`
--

LOCK TABLES `turnero` WRITE;
/*!40000 ALTER TABLE `turnero` DISABLE KEYS */;
/*!40000 ALTER TABLE `turnero` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-16 11:15:52
