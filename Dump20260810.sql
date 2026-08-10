CREATE DATABASE  IF NOT EXISTS `db_sistema_web` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `db_sistema_web`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_sistema_web
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `tb_admin`
--

DROP TABLE IF EXISTS `tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `email_admin` varchar(255) NOT NULL,
  `senha_admin` varchar(255) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `email_admin` (`email_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(255) NOT NULL,
  `email_cliente` varchar(255) NOT NULL,
  `endereco_cliente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `email_cliente` (`email_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `id_encomenda` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_rastreio_encomenda` varchar(255) NOT NULL,
  `endereco_atual_encomenda` varchar(255) NOT NULL,
  `status_encomenda` varchar(255) NOT NULL,
  `atribuicao_encomenda` varchar(255) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_operador_logistico` int(11) NOT NULL,
  PRIMARY KEY (`id_encomenda`),
  UNIQUE KEY `codigo_rastreio_encomenda` (`codigo_rastreio_encomenda`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_operador_logistico` (`id_operador_logistico`),
  CONSTRAINT `tb_encomenda_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_cliente`),
  CONSTRAINT `tb_encomenda_ibfk_2` FOREIGN KEY (`id_operador_logistico`) REFERENCES `tb_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_encomenda`
--

LOCK TABLES `tb_encomenda` WRITE;
/*!40000 ALTER TABLE `tb_encomenda` DISABLE KEYS */;
INSERT INTO `tb_encomenda` VALUES (1,'67674','rua jose bairro olimpia','em transporte','atribuída',1,1),(2,'77778','Rua são goinhas - Maringa','em transporte','não atribuída',1,1),(3,'33317','Rua Jose Pereira - São Paulo','em separação','não atribuída',2,2),(4,'99881','Rua Joao Batista - Londrina','em transporte','não atribuída',2,2),(5,'35783','Galpão Principal - Setor A','em separação','não atribuída',3,3),(6,'25386','Rua Jaco Figueiredo - Cambé','em transporte','não atribuída',3,3),(7,'23729','Rua Pedro Lima','em separação','não atribuída',4,4),(8,'12895','Rua Gustavo Lorenzo - Arapiraca','em transporte','não atribuída',4,4),(9,'38942','Galpão Principal - Setor A','em separação','não atribuída',5,5),(10,'84743','Rua Matheus Martins - Joinville','em transporte','não atribuída',5,5);
/*!40000 ALTER TABLE `tb_encomenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_entrega`
--

DROP TABLE IF EXISTS `tb_entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_entrega` (
  `id_entrega` int(11) NOT NULL AUTO_INCREMENT,
  `data_hora_entrega` datetime NOT NULL,
  `codigo_otp_entrega` varchar(255) NOT NULL,
  `id_encomenda` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_entrega`),
  KEY `id_encomenda` (`id_encomenda`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `tb_entrega_ibfk_1` FOREIGN KEY (`id_encomenda`) REFERENCES `tb_encomenda` (`id_encomenda`),
  CONSTRAINT `tb_entrega_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `tb_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_entrega`
--

LOCK TABLES `tb_entrega` WRITE;
/*!40000 ALTER TABLE `tb_entrega` DISABLE KEYS */;
INSERT INTO `tb_entrega` VALUES (1,'2026-08-10 14:58:50','004904',1,6);
/*!40000 ALTER TABLE `tb_entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_usuario`
--

DROP TABLE IF EXISTS `tb_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(255) NOT NULL,
  `email_usuario` varchar(255) NOT NULL,
  `senha_usuario` varchar(255) NOT NULL,
  `role_usuario` varchar(31) NOT NULL,
  `disponibilidade_usuario` varchar(255) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email_usuario` (`email_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuario`
--

LOCK TABLES `tb_usuario` WRITE;
/*!40000 ALTER TABLE `tb_usuario` DISABLE KEYS */;
INSERT INTO `tb_usuario` VALUES (1,'Gustavo','operador1@gmail.com','operador1010','operador logistico','disponível'),(2,'João','operador2@gmail.com','operador1020','operador logistico','disponível'),(3,'Gabriel','operador3@gmail.com','operador1030','operador logistico','disponível'),(4,'Matheus','operador4@gmail.com','operador1040','operador logistico','disponível'),(5,'Luiz','operador5@gmail.com','operador1050','operador logistico','disponível'),(6,'André','entregador1@gmail.com','entregador1010','entregador','indisponível'),(7,'Estevão','entregador2@gmail.com','entregador1020','entregador','disponível'),(8,'Pedro','entregador3@gmail.com','entregador1030','entregador','disponível'),(9,'Ananias','entregador4@gmail.com','entregador1040','entregador','indisponível'),(10,'José','entregador5@gmail.com','entregador1050','entregador','indisponível');
/*!40000 ALTER TABLE `tb_usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-10 17:25:28
