-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 28, 2024 alle 13:27
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `watchshop`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `name` varchar(99) NOT NULL,
  `surname` varchar(99) NOT NULL,
  `mail` varchar(99) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `password` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`name`, `surname`, `mail`, `admin`, `password`) VALUES
('admin', 'admin', 'admin', 1, 'password'),
('user', 'user', 'user', 0, 'password');

-- --------------------------------------------------------

--
-- Struttura della tabella `watch`
--

CREATE TABLE `watch` (
  `model` varchar(99) NOT NULL,
  `brand` varchar(99) NOT NULL,
  `diameter` double NOT NULL,
  `category` varchar(99) NOT NULL,
  `movement` varchar(99) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(99) NOT NULL,
  `description` varchar(300) NOT NULL,
  `possessed` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `watch`
--

INSERT INTO `watch` (`model`, `brand`, `diameter`, `category`, `movement`, `price`, `image`, `description`, `possessed`) VALUES
('Mako 2', 'Orient', 42, 'divers', 'automatic', 195, 'mako2.jpg', 'This watch best represents the tradition and innovation of the house. This edition of the underwater timepiece is synonymous with global success thanks to the coordinated design of the elements and colors of the dials and bezels, the ideal proportions and the robustness and assembly of the materials', ''),
('Daytona', 'Rolex', 43, 'chronographs', 'automatic', 29000, 'daytona.jpg', 'The Rolex Cosmograph Daytona is a mechanical chronograph wristwatch designed to meet the needs of racing drivers by measuring elapsed time and calculating average speed. Its name refers to Daytona, Florida, where racing flourished in the early 20th century.', ''),
('Crono SuperTitanio 2530', 'Citizen', 43, 'chronographs', 'eco-drive', 348, 'citizen.jpg', 'Crono SuperTitanio 2530 is a watch that embodies the perfect combination of design and technology. The Super Titanium case and bracelet offer unprecedented lightness and resistance to wear.', ''),
('Submariner', 'Rolex', 41, 'divers', 'automatic', 15000, 'submariner.jpg', 'Rolex Submariner is a famous diving sports watch known for its elegant and durable design, featuring a stainless steel case, luminescent dial, and a unidirectional rotating bezel. It is powered by a precise automatic movement.', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
