-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- 主機: 127.0.0.1
-- 產生時間： 2015 �?05 ??13 ??03:45
-- 伺服器版本: 5.6.21
-- PHP 版本： 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫： `sensor`
--

-- --------------------------------------------------------

--
-- 資料表結構 `accelermeter`
--

CREATE TABLE IF NOT EXISTS `accelermeter` (
`log_id` int(11) NOT NULL COMMENT '編號',
  `IMEI` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '手機IMEI',
  `X` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'X值',
  `Y` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Y值',
  `Z` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Z值',
  `G` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'G值',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '發佈時間'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `gyroscope`
--

CREATE TABLE IF NOT EXISTS `gyroscope` (
`log_id` int(11) NOT NULL COMMENT '編號',
  `IMEI` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '手機IMEI',
  `X` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'X值',
  `Y` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Y值',
  `Z` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Z值',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '發佈時間'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `proximity`
--

CREATE TABLE IF NOT EXISTS `proximity` (
`log_id` int(11) NOT NULL COMMENT '編號',
  `IMEI` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '手機IMEI',
  `X` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'X值',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '發佈時間'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `accelermeter`
--
ALTER TABLE `accelermeter`
 ADD PRIMARY KEY (`log_id`);

--
-- 資料表索引 `gyroscope`
--
ALTER TABLE `gyroscope`
 ADD PRIMARY KEY (`log_id`);

--
-- 資料表索引 `proximity`
--
ALTER TABLE `proximity`
 ADD PRIMARY KEY (`log_id`);

--
-- 在匯出的資料表使用 AUTO_INCREMENT
--

--
-- 使用資料表 AUTO_INCREMENT `accelermeter`
--
ALTER TABLE `accelermeter`
MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號';
--
-- 使用資料表 AUTO_INCREMENT `gyroscope`
--
ALTER TABLE `gyroscope`
MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號';
--
-- 使用資料表 AUTO_INCREMENT `proximity`
--
ALTER TABLE `proximity`
MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
