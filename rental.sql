-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 29, 2018 at 07:21 
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `rental`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `nama` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`nama`, `password`) VALUES
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `bayar`
--

CREATE TABLE IF NOT EXISTS `bayar` (
  `no` varchar(10) NOT NULL,
  `tgl` varchar(30) NOT NULL,
  `id_petugas` varchar(10) NOT NULL,
  `nama_petugas` varchar(30) NOT NULL,
  `no_sewa` varchar(10) NOT NULL,
  `tgl_sewa` varchar(30) NOT NULL,
  `ktp` varchar(20) NOT NULL,
  `nama_driver` varchar(30) NOT NULL,
  `merk` varchar(30) NOT NULL,
  `nopol` varchar(15) NOT NULL,
  `bayar` varchar(10) NOT NULL,
  `lama` varchar(10) NOT NULL,
  `denda` varchar(10) NOT NULL,
  `total` varchar(10) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bayar`
--

INSERT INTO `bayar` (`no`, `tgl`, `id_petugas`, `nama_petugas`, `no_sewa`, `tgl_sewa`, `ktp`, `nama_driver`, `merk`, `nopol`, `bayar`, `lama`, `denda`, `total`) VALUES
('B001', '7/Agustus/2017', 'P001', 'Andi', 'S001', '7/Agustus/2017', '03123044021000', 'Yusuf Maulana', 'Honda', 'B 6617 ZMB', '300000', '300000', '0', '0');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE IF NOT EXISTS `driver` (
  `id` varchar(10) NOT NULL,
  `ktp` varchar(15) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `tlpn` varchar(15) NOT NULL,
  `tgl` varchar(30) NOT NULL,
  `almt` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`id`, `ktp`, `nama`, `tlpn`, `tgl`, `almt`) VALUES
('D001', '03123044021000', 'Yusuf Maulana', '08567882xxx', '7/Agustus/2017', 'Jl. Medan Timur'),
('D002', '031200980000', 'Bagas Fauzan', '089714499334', '22/November/2017', 'Jalan Gang Damai 2 No 68');

-- --------------------------------------------------------

--
-- Table structure for table `mobil`
--

CREATE TABLE IF NOT EXISTS `mobil` (
  `kd` varchar(10) NOT NULL,
  `merk` varchar(30) NOT NULL,
  `tipe` varchar(30) NOT NULL,
  `thn` varchar(10) NOT NULL,
  `nopol` varchar(10) NOT NULL,
  `warna` varchar(10) NOT NULL,
  `harga` varchar(11) NOT NULL,
  PRIMARY KEY (`kd`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mobil`
--

INSERT INTO `mobil` (`kd`, `merk`, `tipe`, `thn`, `nopol`, `warna`, `harga`) VALUES
('M001', 'Honda', 'Jazz', '2010', 'B 6617 ZMB', 'Putih', '300000');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE IF NOT EXISTS `petugas` (
  `id` varchar(10) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `tlpn` varchar(15) NOT NULL,
  `almt` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id`, `nama`, `tlpn`, `almt`) VALUES
('P001', 'Andi', '08127533xxx', 'Jl.cilandak Raya');

-- --------------------------------------------------------

--
-- Table structure for table `sewa`
--

CREATE TABLE IF NOT EXISTS `sewa` (
  `no` varchar(10) NOT NULL,
  `tgl` varchar(30) NOT NULL,
  `id_driver` varchar(10) NOT NULL,
  `ktp` varchar(20) NOT NULL,
  `nama_driver` varchar(30) NOT NULL,
  `tlpn` varchar(15) NOT NULL,
  `kd_mobil` varchar(10) NOT NULL,
  `merk` varchar(30) NOT NULL,
  `tipe` varchar(30) NOT NULL,
  `thn` varchar(10) NOT NULL,
  `nopol` varchar(10) NOT NULL,
  `warna` varchar(10) NOT NULL,
  `bayar` varchar(15) NOT NULL,
  `lama` varchar(15) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sewa`
--

INSERT INTO `sewa` (`no`, `tgl`, `id_driver`, `ktp`, `nama_driver`, `tlpn`, `kd_mobil`, `merk`, `tipe`, `thn`, `nopol`, `warna`, `bayar`, `lama`) VALUES
('S001', '29/Juli/2018', 'D001', '03123044021000', 'Yusuf Maulana', '08567882xxx', 'M001', 'Honda', 'Jazz', '2010', 'B 6617 ZMB', 'Putih', '300000', '2');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
