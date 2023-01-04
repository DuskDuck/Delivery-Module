-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.7.3-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for delivery
CREATE DATABASE IF NOT EXISTS `delivery` /*!40100 DEFAULT CHARACTER SET utf32 COLLATE utf32_vietnamese_ci */;
USE `delivery`;

-- Dumping structure for table delivery.shipping_order
CREATE TABLE IF NOT EXISTS `shipping_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(50) COLLATE utf32_vietnamese_ci DEFAULT NULL,
  `status_code` int(11) DEFAULT 1,
  `status_detail` varchar(255) COLLATE utf32_vietnamese_ci DEFAULT NULL,
  `create_at` datetime DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_code` (`order_code`),
  KEY `FK_shipping_order_status` (`status_code`),
  CONSTRAINT `FK_shipping_order_status` FOREIGN KEY (`status_code`) REFERENCES `status` (`status_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf32 COLLATE=utf32_vietnamese_ci;

-- Dumping data for table delivery.shipping_order: ~3 rows (approximately)
/*!40000 ALTER TABLE `shipping_order` DISABLE KEYS */;
INSERT INTO `shipping_order` (`id`, `order_code`, `status_code`, `status_detail`, `create_at`, `update_at`) VALUES
	(1, 'OD1132', 2, 'đơn hàng đã xuất kho', '2022-11-27 09:52:15', '2022-11-27 10:00:00'),
	(2, 'OD1133', 2, 'khách hàng không phản hồi', '2022-11-27 09:56:33', '2022-11-27 10:02:39'),
	(3, 'OD1134', 1, NULL, '2022-11-27 10:00:47', '2022-11-27 10:00:47');
/*!40000 ALTER TABLE `shipping_order` ENABLE KEYS */;

-- Dumping structure for table delivery.status
CREATE TABLE IF NOT EXISTS `status` (
  `status_code` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(255) COLLATE utf32_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`status_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf32 COLLATE=utf32_vietnamese_ci;

-- Dumping data for table delivery.status: ~4 rows (approximately)
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`status_code`, `desc`) VALUES
	(1, 'chưa giao hàng'),
	(2, 'đang giao hàng'),
	(3, 'đã giao hàng'),
	(4, 'hủy');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
