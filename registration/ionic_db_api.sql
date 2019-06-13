-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 13, 2019 at 11:53 AM
-- Server version: 5.7.19
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ionic_db_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `indeastbl1`
--

DROP TABLE IF EXISTS `indeastbl1`;
CREATE TABLE IF NOT EXISTS `indeastbl1` (
  `id` int(10) NOT NULL,
  `ideaimagepath` varchar(300) NOT NULL,
  `ideasdetail` text NOT NULL,
  `ideasimage` text NOT NULL,
  `ideasname` varchar(100) NOT NULL,
  `ideasquote` varchar(300) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `indeastbl1`
--

INSERT INTO `indeastbl1` (`id`, `ideaimagepath`, `ideasdetail`, `ideasimage`, `ideasname`, `ideasquote`) VALUES
(1, 'c:\\path1', 'ideasdetail 1', 'ideasimage 1', 'Swapnil', 'ideasquote 1'),
(2, 'c:\\path2', 'ideasdetail 2', 'ideasimage 2', 'ideasname 2', 'ideasquote 2'),
(1, 'c:\\path1', 'ideasdetail 1', 'ideasimage 1', 'Swapnil', 'ideasquote 1'),
(2, 'c:\\path2', 'ideasdetail 2', 'ideasimage 2', 'ideasname 2', 'ideasquote 2'),
(3, 'c:\\path3', 'ideasdetail 3', 'ideasimage 3', 'ideasname 3', 'ideasquote 3'),
(4, 'c:\\path4', 'ideasdetail 4', 'ideasimage 42', 'ideasname 42', 'ideasquote 42'),
(5, 'c:\\path5', 'ideasdetail 52', 'ideasimage 52', 'ideasname 52', 'ideasquote 52');

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
CREATE TABLE IF NOT EXISTS `registration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(300) NOT NULL,
  `company` varchar(300) NOT NULL,
  `job_title` varchar(300) NOT NULL,
  `email` varchar(300) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`id`, `full_name`, `company`, `job_title`, `email`, `message`) VALUES
(1, 'swapnilshete111', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(2, 'swapnilshete111', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(3, 'swapnilshete111', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(4, 'swapnilshete345', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(5, 'swapnilshete789', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(6, 'swapnilshete222', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(7, 'swapnilshete789', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData'),
(8, 'swapnilshete1118', 'Bitwise', 'TestjobTitle', 'swapnil.shete111@bitwiseglobal.com', 'TestData');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
