CREATE DATABASE  IF NOT EXISTS `db_sistema_web` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_sistema_web`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_sistema_web
-- ------------------------------------------------------
-- Server version	9.5.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '14b55bc9-d7c9-11f0-b13f-202b20fa5abe:1-1223';

--
-- Table structure for table `tb_admin`
--

DROP TABLE IF EXISTS `tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_admin` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `email_admin` varchar(100) NOT NULL,
  `senha_admin` varchar(100) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `email_admin` (`email_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_admin`
--

LOCK TABLES `tb_admin` WRITE;
/*!40000 ALTER TABLE `tb_admin` DISABLE KEYS */;
INSERT INTO `tb_admin` VALUES (1,'admin@gmail.com','admin1010');
/*!40000 ALTER TABLE `tb_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_cliente`
--

DROP TABLE IF EXISTS `tb_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(60) NOT NULL,
  `email_cliente` varchar(100) NOT NULL,
  `endereco_cliente` varchar(120) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `email_cliente` (`email_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cliente`
--

LOCK TABLES `tb_cliente` WRITE;
/*!40000 ALTER TABLE `tb_cliente` DISABLE KEYS */;
INSERT INTO `tb_cliente` VALUES (1,'Kaue','kaue@email.com','Rua das Flores, 123'),(2,'Fabio','fabio@email.com','Av. Brasil, 456'),(3,'Fernando','fernando@email.com','Rua XV de Novembro 67'),(4,'Lincoln','lincoln@email.com','Rua Paraná, 852'),(5,'Rodrigo','beatrice@email.com','Av. Tiradentes, 1024');
/*!40000 ALTER TABLE `tb_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_encomenda`
--

DROP TABLE IF EXISTS `tb_encomenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_encomenda` (
  `id_encomenda` int NOT NULL AUTO_INCREMENT,
  `codigo_rastreio_encomenda` varchar(20) NOT NULL,
  `endereco_atual_encomenda` varchar(120) NOT NULL,
  `status_encomenda` enum('em separação','em transporte','em rota de entrega','entregue') NOT NULL,
  `atribuicao_encomenda` enum('atribuída','não atribuída') NOT NULL,
  `id_cliente` int NOT NULL,
  `id_operador_logistico` int NOT NULL,
  PRIMARY KEY (`id_encomenda`),
  UNIQUE KEY `codigo_rastreio_encomenda` (`codigo_rastreio_encomenda`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_operador_logistico` (`id_operador_logistico`),
  CONSTRAINT `tb_encomenda_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`),
  CONSTRAINT `tb_encomenda_ibfk_2` FOREIGN KEY (`id_operador_logistico`) REFERENCES `tb_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_encomenda`
--

LOCK TABLES `tb_encomenda` WRITE;
/*!40000 ALTER TABLE `tb_encomenda` DISABLE KEYS */;
INSERT INTO `tb_encomenda` VALUES (1,'67674','Centro de Distribuição de Origem','em separação','não atribuída',1,1),(2,'77778','Rua são goinhas - Maringa','em transporte','atribuída',1,1),(3,'33317','Centro de Distribuição de Origem','em separação','não atribuída',2,2),(4,'99881','Rua Joao Batista - Londrina','em transporte','atribuída',2,2),(5,'35783','Centro de Distribuição de Origem','em separação','não atribuída',3,3),(6,'25386','Rua Jaco Figueiredo - Cambé','em transporte','atribuída',3,3),(7,'23729','Centro de Distribuição de Origem','em rota de entrega','atribuída',4,4),(8,'12895','Rua Gustavo Lorenzo - Arapiraca','entregue','atribuída',4,4),(9,'38942','Centro de Distribuição de Origem','em rota de entrega','atribuída',5,5),(10,'84743','Rua Matheus Martins - Joinville','entregue','atribuída',5,5);
/*!40000 ALTER TABLE `tb_encomenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_entrega`
--

DROP TABLE IF EXISTS `tb_entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_entrega` (
  `id_entrega` int NOT NULL AUTO_INCREMENT,
  `data_hora_entrega` datetime DEFAULT NULL,
  `codigo_otp_entrega` varchar(20) DEFAULT NULL,
  `id_encomenda` int NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_entrega`),
  KEY `id_encomenda` (`id_encomenda`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `tb_entrega_ibfk_1` FOREIGN KEY (`id_encomenda`) REFERENCES `tb_encomenda` (`id_encomenda`),
  CONSTRAINT `tb_entrega_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `tb_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_entrega`
--

LOCK TABLES `tb_entrega` WRITE;
/*!40000 ALTER TABLE `tb_entrega` DISABLE KEYS */;
INSERT INTO `tb_entrega` VALUES (1,NULL,NULL,2,6),(2,NULL,NULL,4,7),(3,NULL,'456852',7,8),(4,'2026-08-11 14:30:00','357159',8,9),(5,NULL,'456852',9,8),(6,'2026-08-12 10:15:00','951753',10,10);
/*!40000 ALTER TABLE `tb_entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_usuario`
--

DROP TABLE IF EXISTS `tb_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(60) NOT NULL,
  `email_usuario` varchar(100) NOT NULL,
  `senha_usuario` varchar(100) NOT NULL,
  `role_usuario` enum('operador logistico','entregador') NOT NULL,
  `disponibilidade_usuario` enum('disponível','indisponível') NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email_usuario` (`email_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuario`
--

LOCK TABLES `tb_usuario` WRITE;
/*!40000 ALTER TABLE `tb_usuario` DISABLE KEYS */;
INSERT INTO `tb_usuario` VALUES (1,'Daniel','operador1@gmail.com','operador1010','operador logistico','disponível'),(2,'João','operador2@gmail.com','operador1020','operador logistico','disponível'),(3,'Gabriel','operador3@gmail.com','operador1030','operador logistico','disponível'),(4,'Matheus','operador4@gmail.com','operador1040','operador logistico','disponível'),(5,'Luiz','operador5@gmail.com','operador1050','operador logistico','disponível'),(6,'Gustavo','gustavo.m.silva87@aluno.senai.br','entregador1010','entregador','disponível'),(7,'Estevão','entregador2@gmail.com','entregador1020','entregador','disponível'),(8,'Pedro','entregador3@gmail.com','entregador1030','entregador','disponível'),(9,'Ananias','entregador4@gmail.com','entregador1040','entregador','disponível'),(10,'José','entregador5@gmail.com','entregador1050','entregador','disponível');
/*!40000 ALTER TABLE `tb_usuario` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-12 12:07:20
