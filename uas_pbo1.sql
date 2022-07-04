-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2022 at 06:17 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas_pbo1`
--

-- --------------------------------------------------------

--
-- Table structure for table `alamat`
--

CREATE TABLE `alamat` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `alamat`
--

INSERT INTO `alamat` (`id`, `nama`) VALUES
(1, 'Banjarbaru'),
(2, 'Banjarmasin'),
(4, 'Martapura Timur'),
(5, 'Martapura Kota'),
(6, 'Pelaihari'),
(7, 'Sungai Tabuk'),
(8, 'Sungai Rangas'),
(10, 'Sekumpul'),
(11, 'Bincau'),
(12, 'Gambut'),
(13, 'Tanjung Rema'),
(14, 'Kayutangi'),
(15, 'Aluh Aluh');

-- --------------------------------------------------------

--
-- Table structure for table `pengunjung`
--

CREATE TABLE `pengunjung` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `alamat_id` int(11) DEFAULT NULL,
  `jenis_kelamin` enum('L','P') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tanggal_kunjungan` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengunjung`
--

INSERT INTO `pengunjung` (`id`, `nama`, `alamat_id`, `jenis_kelamin`, `email`, `tanggal_kunjungan`) VALUES
(11, 'Noor Hasanah', 1, 'P', 'sanah@gmail.com', '2022-06-07'),
(12, 'Nadia Aulia', 2, 'P', 'diaaul@gmail.com', '2022-06-25'),
(13, 'Muhammad Subahan', 8, 'L', 'subahan@gmail.com', '2022-06-19'),
(15, 'Fathurrahman', 12, 'L', 'athur@gmail.com', '2022-02-02'),
(17, 'Nurul Hasanah', 15, 'P', 'nurulhhh@gmail.com', '2022-02-25'),
(18, 'Maulana Abdul Khadir', 4, 'L', 'lanakahir@gmail.com', '2022-05-25'),
(20, 'Aulia Nur Fatmawati', 5, 'P', 'liawati@gmail.com', '2022-05-23');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alamat`
--
ALTER TABLE `alamat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengunjung`
--
ALTER TABLE `pengunjung`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alamat`
--
ALTER TABLE `alamat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `pengunjung`
--
ALTER TABLE `pengunjung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
