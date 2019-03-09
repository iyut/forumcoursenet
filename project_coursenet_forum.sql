-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 09, 2019 at 09:52 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_coursenet_forum`
--

-- --------------------------------------------------------

--
-- Table structure for table `frm_comment`
--

CREATE TABLE `frm_comment` (
  `id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `author` int(11) NOT NULL,
  `thread_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `frm_comment`
--

INSERT INTO `frm_comment` (`id`, `comment`, `author`, `thread_id`) VALUES
(1, 'testing', 1, 1),
(2, 'adasdasd', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `frm_thread`
--

CREATE TABLE `frm_thread` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `author` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `frm_thread`
--

INSERT INTO `frm_thread` (`id`, `title`, `content`, `author`) VALUES
(1, 'Thread 1', 'testing', 1),
(2, 'testing with author', 'just testing with author', 1),
(3, 'fsdfsdfs', 'dfsdfsdf', 1),
(4, 'test', 'pass123qwe', 9),
(5, 'test', 'pass123qwe', 9),
(6, 'testing with author', 'just testing with author', 1),
(7, 'test', 'pass123qwe', 9),
(8, 'test', 'pass123qwe', 9),
(9, 'test', 'pass123qwe', 9),
(10, 'test', 'pass123qwe', 9);

-- --------------------------------------------------------

--
-- Table structure for table `frm_user`
--

CREATE TABLE `frm_user` (
  `id` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `profile_picture` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `frm_user`
--

INSERT INTO `frm_user` (`id`, `email`, `name`, `password`, `telephone`, `profile_picture`) VALUES
(1, 'iyut85@yahoo.com', 'Luthfi Bintoro', '9a11c87a0c97dfa034b6d95f1c7e9f15', '081807556285', ''),
(9, 'iyut85@yahoo.co.id', 'Luthfi Bintoro', '9a11c87a0c97dfa034b6d95f1c7e9f15', '', ''),
(10, 'mynameistobi@gmail.com', 'Iyut', '9a11c87a0c97dfa034b6d95f1c7e9f15', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `frm_comment`
--
ALTER TABLE `frm_comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `frm_thread`
--
ALTER TABLE `frm_thread`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `frm_user`
--
ALTER TABLE `frm_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `frm_comment`
--
ALTER TABLE `frm_comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `frm_thread`
--
ALTER TABLE `frm_thread`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `frm_user`
--
ALTER TABLE `frm_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
