-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 29, 2024 at 07:45 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `queenz_inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_accounts`
--

CREATE TABLE `tbl_accounts` (
  `account_id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email_address` varchar(100) NOT NULL,
  `phone_number` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(100) NOT NULL,
  `date_created` date NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_accounts`
--

INSERT INTO `tbl_accounts` (`account_id`, `first_name`, `last_name`, `address`, `email_address`, `phone_number`, `username`, `password`, `role`, `date_created`, `status`) VALUES
(1, 'archie', 'albarico', 'tunghaan, minglanilla, cebu', 'archiealbarico69@gmail.com', '09491853866', 'archieamas11', 'archieamas11', 'admin', '2024-09-16', 'active'),
(2, 'archie', 'albarico', 'tunghaan, minglanilla, cebu', 'archiealbarico69@gmail.com', '09491853866', 'archieamas11', 'archieamas11', 'staff', '2024-09-16', 'active'),
(3, 'archie', 'albarico', 'purok does, minglanilla', 'archiealbarico00@gmail.com', '09231226478', 'staff', '$2a$10$AG0YzjCo7bI/M/XSX3cEouZt2ot.DEgCo/nn6zInWXWwPb3PBdj8e', 'admin', '2024-09-23', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_items`
--

CREATE TABLE `tbl_items` (
  `item_SKU` int(11) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_description` varchar(600) NOT NULL,
  `item_stocks` int(11) NOT NULL,
  `item_price` int(11) NOT NULL,
  `total_sold` int(11) NOT NULL,
  `item_condition` varchar(100) NOT NULL,
  `item_category` varchar(100) NOT NULL,
  `item_size` varchar(100) NOT NULL,
  `item_color` varchar(100) NOT NULL,
  `item_material` varchar(100) NOT NULL,
  `item_supplier` varchar(100) NOT NULL,
  `added_by` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `item_status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_items`
--

INSERT INTO `tbl_items` (`item_SKU`, `item_name`, `item_description`, `item_stocks`, `item_price`, `total_sold`, `item_condition`, `item_category`, `item_size`, `item_color`, `item_material`, `item_supplier`, `added_by`, `date_added`, `item_status`) VALUES
(1001, 'Classic T-Shirt	', 'Good', 0, 2000, 4, 'prelove', 'Casual', 'M', 'Blue', '100% Cotton', 'A', 1, '2024-09-22 16:25:17', 'soldout'),
(1003, '123', 'Good', 123, 123, 0, 'new', 'Formal', 'L', 'Yellow', 'Polyester blend', 'B', 1, '2024-09-22 15:21:55', 'archived'),
(1004, '435', 'Bad', 435435, 435, 0, 'new', 'Activewear', 'XXL', 'Green', 'Leather', 'C', 1, '2024-09-22 15:21:58', 'archived'),
(1005, '3242', 'Nice', 228, 234, 6, 'false', 'Casual', 'M', 'Black', '100% Cotton', 'D', 1, '2024-09-22 17:46:06', 'available'),
(1006, '34', 'Yey', 124, 34, 0, 'new', 'Formal', 'L', 'Orange', 'Polyester blend', 'A', 1, '2024-09-22 15:22:08', 'soldout');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_logs`
--

CREATE TABLE `tbl_logs` (
  `logs_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `logs_action` varchar(100) NOT NULL,
  `logs_details` varchar(600) NOT NULL,
  `logs_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_logs`
--

INSERT INTO `tbl_logs` (`logs_id`, `account_id`, `logs_action`, `logs_details`, `logs_timestamp`) VALUES
(1, 1, 'add item', 'admin 1 Successfully added a new item!', '2024-09-21 20:39:42'),
(2, 1, 'Restored', 'admin 1 successfully Restored an item with SKU number 1003!', '2024-09-22 07:26:02'),
(3, 1, 'Restored', 'admin 1 successfully Restored an item with SKU number 1005!', '2024-09-22 15:17:27'),
(4, 1, 'Restored', 'admin 1 successfully Restored an item with SKU number 1005!', '2024-09-22 15:19:08'),
(5, 1, 'edit item', 'admin 1 successfully edited item 1005!', '2024-09-22 15:39:35'),
(7, 3, 'Logged in', 'Account 3 successfully logged in!', '2024-09-22 17:40:41'),
(10, 3, 'Logged in', 'Account 3 successfully logged in!', '2024-09-29 16:24:08'),
(11, 3, 'Logged in', 'Account 3 successfully logged in!', '2024-09-29 16:25:38'),
(12, 3, 'Logged in', 'Account 3 successfully logged in!', '2024-09-29 16:32:17');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `tbl_items`
--
ALTER TABLE `tbl_items`
  ADD PRIMARY KEY (`item_SKU`),
  ADD KEY `added_by` (`added_by`);

--
-- Indexes for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD PRIMARY KEY (`logs_id`),
  ADD KEY `account_id` (`account_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_items`
--
ALTER TABLE `tbl_items`
  MODIFY `item_SKU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1007;

--
-- AUTO_INCREMENT for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  MODIFY `logs_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_items`
--
ALTER TABLE `tbl_items`
  ADD CONSTRAINT `tbl_items_ibfk_1` FOREIGN KEY (`added_by`) REFERENCES `tbl_accounts` (`account_id`);

--
-- Constraints for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD CONSTRAINT `tbl_logs_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `tbl_accounts` (`account_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
