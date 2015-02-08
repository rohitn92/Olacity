-- phpMyAdmin SQL Dump
-- version 4.0.10.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 07, 2015 at 11:03 PM
-- Server version: 5.5.41-cll-lve
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gcm`
--

-- --------------------------------------------------------

--
-- Table structure for table `coord`
--

CREATE TABLE IF NOT EXISTS `coord` (
  `device_id` int(11) NOT NULL,
  `lat` varchar(40) NOT NULL,
  `long` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `coord`
--

INSERT INTO `coord` (`device_id`, `lat`, `long`) VALUES
(1, '12.9486229', '77.6403316'),
(3, '13', '16'),
(4, '15.8', '70.9');

-- --------------------------------------------------------

--
-- Table structure for table `event_details`
--

CREATE TABLE IF NOT EXISTS `event_details` (
  `event_id` int(11) NOT NULL,
  `device_id` varchar(40) NOT NULL,
  `lat` varchar(50) NOT NULL,
  `long` varchar(50) NOT NULL,
  `flag` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `gcm_users`
--

CREATE TABLE IF NOT EXISTS `gcm_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gcm_regid` text,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `gcm_users`
--

INSERT INTO `gcm_users` (`id`, `gcm_regid`, `name`, `email`, `created_at`) VALUES
(1, 'APA91bEzwrThjttkHjXMEsvS2Doiyz9c3XcBoXLGXSmGDHbaxtN53IxnQHEhmW6qsQw76JbDqD9Q3gOImstVMrf0cJaIL6F3-levvSVZSy-pSl-waW4iScH8arl6dIMdrVpC_99d_qKY4h1cJkNTd32W5diRMjksmVBBz6S22o8rTeRtzhy3dXo', 'Kaushal', '8867385777', '2015-02-07 09:48:48'),
(2, 'APA91bHdAgpofS1A6-Gsv3GkONmQh7Wiajcq1EHD5dHRmeQUgWe9EP8BcPIDWwapHy-2PInhqQRkyDZRCv5b8AGEWnoAJhhyZjOVEwe4hVPp_el8Z-hOktkln7zRlJD1bHWpUNuK3YlRCcUXwSHKudsXixWVL-pOBkjofEgv0ZiB_SyEi-xic_k\n', 'Avinash', '07795426645', '2015-02-07 10:35:22'),
(3, 'APA91bEMVvbShqJ71cTM8KfRK5s8DJmbtVjUwWcORi1PBDTw47ndRIRNOWHkFUDneM_vakRQBxZ1xtoLVVyMJzSKMqWLuwlSbCPPMIHdxPYxT-CEPPZ7FslN9a1TfXIHZn0Co8sZjpCL7uGY4kLYDZTZIcRuUAz8w1KLrOqd19stNC4z-Osn-4Y', 'Rohit', '+919886507945', '2015-02-07 10:37:08'),
(4, NULL, '', '', '2015-02-08 04:30:25'),
(5, NULL, 'rohit', '9035164146', '2015-02-08 04:37:13'),
(6, 'h', 'Rohit nn', '9035111111', '2015-02-08 04:42:07'),
(7, 'h', 'ronnnnn', '1234567', '2015-02-08 04:44:53'),
(8, 'h', 'xxxxx', '6788893', '2015-02-08 04:46:30'),
(9, 'h', 'eeeee', '3589942', '2015-02-08 04:48:40'),
(10, 'h', 'xcvvbjd', '68000522', '2015-02-08 04:51:17'),
(11, 'h', 'rohit nigamd', '66557835', '2015-02-08 05:25:30');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE IF NOT EXISTS `request` (
  `device_id` int(11) NOT NULL,
  `event_id` varchar(40) NOT NULL,
  `flag` int(11) NOT NULL,
  `host_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`device_id`, `event_id`, `flag`, `host_id`) VALUES
(1, '47', 1, 7),
(1, '47', 1, 7),
(1, '34', 1, 7),
(1, '34', 1, 7),
(1, '34', 0, 7),
(1, '24', 0, 7),
(1, '43', 0, 7);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
