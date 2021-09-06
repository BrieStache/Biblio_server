--
--  creating the database if not existing
--
CREATE DATABASE  IF NOT EXISTS `cb_biblio_db`;
USE `cb_biblio_db`;

--
-- Removing all the table to create a 'fresh db'
--
DROP TABLE IF EXISTS `bookReservation`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `autor`;
DROP TABLE IF EXISTS `publisher`;

--
-- Creating all the table
--
--
-- Table structure for table `publisher`
--
CREATE TABLE `publisher` (
	`publisherId` int NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`email` varchar(255) NOT NULL,
	PRIMARY KEY (`publisherId`)
);

--
-- Table structure for table `autor`
--
CREATE TABLE `autor` (
	`autorId` int NOT NULL AUTO_INCREMENT,
	`firstName` varchar(255) NOT NULL,
	`lastName` varchar(255) NOT NULL,
	`email` varchar(255) NOT NULL,
	PRIMARY KEY (`autorId`)
);

--
-- Table structure for table `book`
--
CREATE TABLE `book` (
	`bookId` int NOT NULL AUTO_INCREMENT,
	`ISBN` varchar(100) NOT NULL,
	`title` varchar(100) NOT NULL,
	`lang` varchar(50) NOT NULL,
	`page` int NOT NULL,
	`autorId` int,
	`publisherId` int,
	`accountId` int,
	`borrowedDate` DATE,
	PRIMARY KEY (`bookId`)
);

--
-- Table structure for table `account`
--
CREATE TABLE `account` (
	`accountId` int NOT NULL AUTO_INCREMENT,
	`userName` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	`firstName` varchar(255) NOT NULL,
	`lastName` varchar(255) NOT NULL,
	`email` varchar(255) NOT NULL,
	PRIMARY KEY (`accountId`)
);

--
-- Table structure for table `borrowedBook`
--
CREATE TABLE `bookReservation` (
	`reservationId` int NOT NULL AUTO_INCREMENT,
	`accountID` int,
	`bookId` int,
	`requestDate` DATE,
	PRIMARY KEY (`reservationId`)
);
