# Host: 127.0.0.1  (Version 5.7.17-log)
# Date: 2018-06-02 22:25:10
# Generator: MySQL-Front 5.4  (Build 4.153) - http://www.mysqlfront.de/

/*!40101 SET NAMES utf8 */;

#
# Structure for table "accesslog"
#

DROP TABLE IF EXISTS `accesslog`;
CREATE TABLE `accesslog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `access_token` varchar(64) NOT NULL,
  `expires_in` int(11) NOT NULL,
  `creattime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `loginuser` varchar(32) DEFAULT NULL,
  `loginuserId` bigint(20) DEFAULT NULL,
  `logintime` timestamp NULL DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `vericode` varchar(16) DEFAULT NULL,
  `vericodeExpired` timestamp NULL DEFAULT NULL,
  `veriMobile` varchar(16) DEFAULT NULL,
  `photo` varchar(1024) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `kickofftime` timestamp NULL DEFAULT NULL,
  `inviteCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_at` (`access_token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "accesslog"
#


#
# Structure for table "mobilelimit"
#

DROP TABLE IF EXISTS `mobilelimit`;
CREATE TABLE `mobilelimit` (
  `mobile` varchar(50) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '1',
  `firsttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "mobilelimit"
#


#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `sex` tinyint(3) unsigned DEFAULT NULL COMMENT '性别(0:女，1:男，2:不愿透露)',
  `locked` tinyint(3) unsigned DEFAULT NULL COMMENT '状态(0:正常,1:锁定)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

#
# Data for table "user"
#


#
# Structure for table "userext"
#

DROP TABLE IF EXISTS `userext`;
CREATE TABLE `userext` (
  `uid` bigint(20) NOT NULL,
  `avatar` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "userext"
#

