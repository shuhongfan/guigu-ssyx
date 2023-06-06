/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 8.0.28 : Database - shequ-product
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shequ-product` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shequ-product`;

/*Table structure for table `attr` */

DROP TABLE IF EXISTS `attr`;

CREATE TABLE `attr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `name` char(30) DEFAULT NULL COMMENT '属性名',
  `input_type` int DEFAULT NULL COMMENT '属性录入方式：0->手工录入；1->从列表中选取',
  `select_list` varchar(255) DEFAULT NULL COMMENT '可选值列表[用逗号分隔]',
  `attr_group_id` bigint DEFAULT NULL COMMENT '属性分组id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='商品属性';

/*Data for the table `attr` */

insert  into `attr`(`id`,`name`,`input_type`,`select_list`,`attr_group_id`,`create_time`,`update_time`,`is_deleted`) values (1,'品牌',0,'',1,'2021-06-06 17:43:46','2023-04-04 09:50:55',1),(2,'规格',0,'',1,'2021-06-06 17:43:58','2023-04-04 09:50:53',1),(3,'产地',1,'中国,美国',1,'2021-06-06 17:44:08','2021-06-06 17:44:08',0),(4,'保质期',1,'一年,两年',1,'2021-06-06 17:44:20','2021-06-06 17:44:20',0),(5,'储存条件',1,'常温,冷场',1,'2021-06-06 17:46:08','2021-06-06 17:46:08',0),(6,'储存条件',1,'常温,冷场',2,'2023-03-13 17:02:47','2023-03-13 17:02:47',0),(7,'333334',0,'1',4,'2023-03-14 09:34:22','2023-03-14 09:34:22',0),(8,'11',0,'1',6,'2023-04-04 13:41:26','2023-04-04 13:42:44',1),(9,'品牌名称',0,'',6,'2023-04-04 13:43:25','2023-04-04 13:43:25',0),(10,'规格',1,'10*10,12*20',6,'2023-04-04 13:44:04','2023-04-04 13:44:04',0);

/*Table structure for table `attr_group` */

DROP TABLE IF EXISTS `attr_group`;

CREATE TABLE `attr_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `name` char(20) DEFAULT NULL COMMENT '组名',
  `sort` int DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='属性分组';

/*Data for the table `attr_group` */

insert  into `attr_group`(`id`,`name`,`sort`,`remark`,`create_time`,`update_time`,`is_deleted`) values (1,'蔬菜水果',1,'蔬菜水果','2021-06-06 17:43:21','2021-06-06 17:43:21',0),(2,'海鲜水品',2,'海鲜水品','2021-11-17 03:31:25','2021-11-17 03:31:25',0),(3,'速冻食品',3,'速冻食品','2021-11-17 03:31:53','2021-11-17 03:31:53',0),(4,'12',1,'1','2023-03-14 09:34:00','2023-03-14 09:34:44',1),(5,'测试',11,'11','2023-04-04 12:28:52','2023-04-04 13:40:29',1),(6,'母婴玩具',5,'母婴玩具','2023-04-04 13:40:55','2023-04-04 13:40:55',0);

/*Table structure for table `base_category_trademark` */

DROP TABLE IF EXISTS `base_category_trademark`;

CREATE TABLE `base_category_trademark` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `category3_id` bigint NOT NULL DEFAULT '0' COMMENT '三级级分类id',
  `trademark_id` bigint NOT NULL DEFAULT '0' COMMENT '品牌id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='三级分类表';

/*Data for the table `base_category_trademark` */

insert  into `base_category_trademark`(`id`,`category3_id`,`trademark_id`,`create_time`,`update_time`,`is_deleted`) values (1,61,1,'2021-08-20 15:22:50','2021-08-20 15:23:14',1),(2,61,2,'2021-08-20 15:22:50','2021-08-25 19:11:47',1),(4,61,1,'2021-08-20 15:23:27','2021-08-20 15:34:36',1),(5,61,1,'2021-08-23 17:47:49','2021-08-23 17:47:49',0),(6,62,1,'2021-08-23 17:48:00','2021-08-23 17:48:00',0),(7,62,2,'2021-08-23 17:48:00','2021-08-25 19:11:58',1),(8,61,2,'2021-08-25 19:11:52','2021-08-25 19:12:25',1),(10,63,2,'2021-10-09 08:44:57','2021-10-09 08:44:57',0),(11,63,3,'2021-10-09 08:45:05','2021-10-09 08:45:05',0);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` char(50) DEFAULT NULL COMMENT '分类名称',
  `img_url` varchar(200) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL COMMENT '父分类id',
  `status` tinyint DEFAULT NULL COMMENT '是否显示[0-不显示，1显示]',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品三级分类';

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`img_url`,`parent_id`,`status`,`sort`,`create_time`,`update_time`,`is_deleted`) values (1,'新鲜蔬菜','\r\nhttps://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xinxianshucai.jpg',NULL,NULL,1,'2021-06-06 17:36:18','2023-04-13 13:35:08',0),(2,'时令水果','\r\nhttps://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/shilingshuiguo.jpg',NULL,NULL,2,'2021-06-06 17:36:45','2023-04-13 13:35:57',0),(3,'肉禽蛋品','\r\nhttps://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/rouqindanpin.jpg',NULL,NULL,3,'2021-06-06 17:37:22','2023-04-13 13:35:57',0),(4,'海鲜水产','\r\nhttps://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/haixianshuichan.jpg',NULL,NULL,4,'2021-06-06 17:37:42','2023-04-13 13:35:57',0),(5,'速食冻品','\r\nhttps://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sudongshuiping.jpg',NULL,NULL,5,'2021-06-06 17:38:07','2023-04-13 13:35:08',0),(6,'乳品烘焙','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8n6AAjUDAABTGU1xPxg840.png',NULL,NULL,6,'2021-06-06 17:39:14','2021-08-29 10:38:01',0),(7,'面包蛋糕','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8uiAcQ9XAACQof4kpis981.png',NULL,NULL,7,'2021-06-06 17:39:37','2021-08-29 10:37:37',0),(8,'酒饮冲调','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8n6AAjUDAABTGU1xPxg840.png',NULL,NULL,8,'2021-06-06 17:40:05','2021-08-29 10:38:02',0),(9,'休闲零食','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8qaAUO2FAADL_iNj45o145.png',NULL,NULL,10,'2021-06-06 17:40:33','2021-08-29 10:37:54',0),(10,'粮油调味','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8rqAdCTUAACvSp6ekiU197.png',NULL,NULL,10,'2021-06-06 17:40:51','2021-08-29 10:36:57',0),(11,'日用百货','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8qaAUO2FAADL_iNj45o145.png',NULL,NULL,11,'2021-06-06 17:41:06','2021-08-29 10:37:55',0),(12,'鲜花宠物','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8uiAcQ9XAACQof4kpis981.png',NULL,NULL,12,'2021-06-06 17:41:25','2021-08-29 10:37:40',0),(13,'母婴玩具','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8n6AAjUDAABTGU1xPxg840.png',NULL,NULL,13,'2021-06-06 17:41:43','2023-04-04 11:54:33',1),(14,'数码家电','http://47.93.148.192:8080/group1/M00/01/95/rBHu8mEq8qaAUO2FAADL_iNj45o145.png',NULL,NULL,14,'2021-06-06 17:42:08','2023-04-04 11:54:33',1),(15,'test1',NULL,NULL,NULL,1002,'2023-04-04 11:53:58','2023-04-04 11:54:23',1);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品名字',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '会员昵称',
  `icon` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `star` tinyint(1) DEFAULT NULL COMMENT '星级',
  `ip` varchar(64) DEFAULT NULL COMMENT '会员ip',
  `status` tinyint(1) DEFAULT NULL COMMENT '显示状态[0-不显示，1-显示]',
  `follow_count` int DEFAULT NULL COMMENT '点赞数',
  `reply_count` int DEFAULT NULL COMMENT '回复数',
  `resources` varchar(1000) DEFAULT NULL COMMENT '评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]',
  `content` text COMMENT '内容',
  `type` tinyint DEFAULT NULL COMMENT '评论类型[0 - 对商品的直接评论，1 - 对评论的回复]',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='商品评价';

/*Data for the table `comment` */

/*Table structure for table `comment_replay` */

DROP TABLE IF EXISTS `comment_replay`;

CREATE TABLE `comment_replay` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_id` bigint DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '1' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='产品评价回复表';

/*Data for the table `comment_replay` */

/*Table structure for table `mq_repeat_record` */

DROP TABLE IF EXISTS `mq_repeat_record`;

CREATE TABLE `mq_repeat_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `business_no` varchar(20) DEFAULT NULL COMMENT '业务编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='mq去重表';

/*Data for the table `mq_repeat_record` */

/*Table structure for table `region_ware` */

DROP TABLE IF EXISTS `region_ware`;

CREATE TABLE `region_ware` (
  `id` bigint NOT NULL DEFAULT '0' COMMENT 'id',
  `region` varchar(20) NOT NULL DEFAULT '0' COMMENT '开通区域',
  `region_name` varchar(30) NOT NULL DEFAULT '' COMMENT '区域名称',
  `ware_id` bigint NOT NULL DEFAULT '0' COMMENT '仓库',
  `ware_name` varchar(30) NOT NULL DEFAULT '' COMMENT '仓库名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '1' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='城市仓库关联表';

/*Data for the table `region_ware` */

/*Table structure for table `sku_attr_value` */

DROP TABLE IF EXISTS `sku_attr_value`;

CREATE TABLE `sku_attr_value` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT '商品id',
  `attr_id` bigint DEFAULT NULL COMMENT '属性id',
  `attr_name` varchar(200) DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(200) DEFAULT NULL COMMENT '属性值',
  `sort` int DEFAULT NULL COMMENT '顺序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8mb3 COMMENT='spu属性值';

/*Data for the table `sku_attr_value` */

insert  into `sku_attr_value`(`id`,`sku_id`,`attr_id`,`attr_name`,`attr_value`,`sort`,`create_time`,`update_time`,`is_deleted`) values (1,1,1,'品牌','自产',1,'2021-06-06 17:53:03','2021-08-17 11:25:47',1),(2,1,2,'规格','300g±30g',2,'2021-06-06 17:53:03','2021-08-17 11:25:47',1),(3,1,3,'产地','成都龙泉',3,'2021-06-06 17:53:03','2021-08-17 11:25:47',1),(4,1,4,'保质期','3天',4,'2021-06-06 17:53:03','2021-08-17 11:25:47',1),(5,1,5,'储存条件','常温',5,'2021-06-06 17:53:03','2021-08-17 11:25:47',1),(6,2,1,'品牌','自产',1,'2021-06-06 18:04:27','2021-08-17 11:26:23',1),(7,2,2,'规格','250',2,'2021-06-06 18:04:27','2021-08-17 11:26:23',1),(8,2,3,'产地','成都郫县',3,'2021-06-06 18:04:27','2021-08-17 11:26:23',1),(9,2,4,'保质期','180天',4,'2021-06-06 18:04:27','2021-08-17 11:26:23',1),(10,2,5,'储存条件','常温',5,'2021-06-06 18:04:27','2021-08-17 11:26:23',1),(11,3,1,'品牌','宏达',1,'2021-06-06 18:05:57','2021-08-17 11:26:43',1),(12,3,2,'规格','300g±30g',2,'2021-06-06 18:05:57','2021-08-17 11:26:43',1),(13,3,3,'产地','成都龙泉',3,'2021-06-06 18:05:57','2021-08-17 11:26:43',1),(14,3,4,'保质期','3天',4,'2021-06-06 18:05:57','2021-08-17 11:26:43',1),(15,3,5,'储存条件','冷场',5,'2021-06-06 18:05:57','2021-08-17 11:26:43',1),(16,4,1,'品牌','三菱',1,'2021-06-06 18:09:42','2021-08-17 11:27:12',1),(17,4,2,'规格','500g±30g',2,'2021-06-06 18:09:42','2021-08-17 11:27:12',1),(18,4,3,'产地','成都达州',3,'2021-06-06 18:09:42','2021-08-17 11:27:12',1),(19,4,4,'保质期','180天',4,'2021-06-06 18:09:42','2021-08-17 11:27:12',1),(20,4,5,'储存条件','常温',5,'2021-06-06 18:09:42','2021-08-17 11:27:12',1),(21,5,1,'品牌','自产',1,'2021-06-06 18:10:56','2021-08-17 11:27:29',1),(22,5,2,'规格','500±30g',2,'2021-06-06 18:10:56','2021-08-17 11:27:29',1),(23,5,3,'产地','四川巴中',3,'2021-06-06 18:10:56','2021-08-17 11:27:29',1),(24,5,4,'保质期','200天',4,'2021-06-06 18:10:56','2021-08-17 11:27:29',1),(25,5,5,'储存条件','常温',5,'2021-06-06 18:10:56','2021-08-17 11:27:29',1),(26,6,1,'品牌','自产',1,'2021-06-06 18:13:46','2021-08-17 11:27:49',1),(27,6,2,'规格','500g±30g',2,'2021-06-06 18:13:46','2021-08-17 11:27:49',1),(28,6,3,'产地','四川达州',3,'2021-06-06 18:13:46','2021-08-17 11:27:49',1),(29,6,4,'保质期','3',4,'2021-06-06 18:13:46','2021-08-17 11:27:49',1),(30,6,5,'储存条件','冷场',5,'2021-06-06 18:13:46','2021-08-17 11:27:49',1),(31,7,1,'品牌','自产',1,'2021-06-06 18:15:18','2021-08-17 11:28:10',1),(32,7,2,'规格','300g±30g',2,'2021-06-06 18:15:18','2021-08-17 11:28:10',1),(33,7,3,'产地','四川达州',3,'2021-06-06 18:15:18','2021-08-17 11:28:10',1),(34,7,4,'保质期','4天',4,'2021-06-06 18:15:18','2021-08-17 11:28:10',1),(35,7,5,'储存条件','常温',5,'2021-06-06 18:15:18','2021-08-17 11:28:10',1),(36,8,1,'品牌','自产',1,'2021-06-06 18:16:26','2021-06-06 18:25:29',1),(37,8,2,'规格','500g±30g',2,'2021-06-06 18:16:26','2021-06-06 18:25:29',1),(38,8,3,'产地','四川内江',3,'2021-06-06 18:16:26','2021-06-06 18:25:29',1),(39,8,4,'保质期','5天',4,'2021-06-06 18:16:26','2021-06-06 18:25:29',1),(40,8,5,'储存条件','常温',5,'2021-06-06 18:16:26','2021-06-06 18:25:29',1),(41,9,1,'品牌','自产',1,'2021-06-06 18:17:47','2021-08-17 11:28:42',1),(42,9,2,'规格','500g±30g',2,'2021-06-06 18:17:47','2021-08-17 11:28:42',1),(43,9,3,'产地','四川巴中',3,'2021-06-06 18:17:47','2021-08-17 11:28:42',1),(44,9,4,'保质期','5天',4,'2021-06-06 18:17:47','2021-08-17 11:28:42',1),(45,9,5,'储存条件','冷场',5,'2021-06-06 18:17:47','2021-08-17 11:28:42',1),(46,8,1,'品牌','自产',1,'2021-06-06 18:16:26','2021-08-17 11:28:25',1),(47,8,2,'规格','500g±30g',2,'2021-06-06 18:16:26','2021-08-17 11:28:25',1),(48,8,3,'产地','四川内江',3,'2021-06-06 18:16:26','2021-08-17 11:28:25',1),(49,8,4,'保质期','5天',4,'2021-06-06 18:16:26','2021-08-17 11:28:25',1),(50,8,5,'储存条件','常温',5,'2021-06-06 18:16:26','2021-08-17 11:28:25',1),(51,10,1,'品牌','农家',1,'2021-08-14 21:02:56','2021-09-18 09:28:28',1),(52,10,2,'规格','南瓜+2kg',2,'2021-08-14 21:02:56','2021-09-18 09:28:28',1),(53,10,3,'产地','成都',3,'2021-08-14 21:02:56','2021-09-18 09:28:28',1),(54,10,4,'保质期','180天 ',4,'2021-08-14 21:02:56','2021-09-18 09:28:28',1),(55,10,5,'储存条件','常温',5,'2021-08-14 21:02:56','2021-09-18 09:28:28',1),(56,11,1,'品牌','农家',1,'2021-08-14 21:26:30','2021-08-17 11:03:54',1),(57,11,2,'规格','300g±30g',2,'2021-08-14 21:26:30','2021-08-17 11:03:54',1),(58,11,3,'产地','成都',3,'2021-08-14 21:26:30','2021-08-17 11:03:54',1),(59,11,4,'保质期','7',4,'2021-08-14 21:26:30','2021-08-17 11:03:54',1),(60,11,5,'储存条件','常温',5,'2021-08-14 21:26:30','2021-08-17 11:03:54',1),(61,12,1,'品牌','农家',1,'2021-08-14 21:28:16','2021-08-17 11:03:36',1),(62,12,2,'规格','300g±30g',2,'2021-08-14 21:28:16','2021-08-17 11:03:36',1),(63,12,3,'产地','成都',3,'2021-08-14 21:28:16','2021-08-17 11:03:36',1),(64,12,4,'保质期','7',4,'2021-08-14 21:28:16','2021-08-17 11:03:36',1),(65,12,5,'储存条件','常温',5,'2021-08-14 21:28:16','2021-08-17 11:03:36',1),(66,13,1,'品牌','农商',1,'2021-08-14 21:51:50','2021-08-17 10:44:20',1),(67,13,2,'规格','500g±30g',2,'2021-08-14 21:51:50','2021-08-17 10:44:20',1),(68,13,3,'产地','成都',3,'2021-08-14 21:51:50','2021-08-17 10:44:20',1),(69,13,4,'保质期','7',4,'2021-08-14 21:51:50','2021-08-17 10:44:20',1),(70,13,5,'储存条件','冷场',5,'2021-08-14 21:51:50','2021-08-17 10:44:20',1),(71,13,1,'品牌','农商',1,'2021-08-14 21:51:50','2021-08-17 10:46:50',1),(72,13,2,'规格','500g±30g',2,'2021-08-14 21:51:50','2021-08-17 10:46:50',1),(73,13,3,'产地','成都',3,'2021-08-14 21:51:50','2021-08-17 10:46:50',1),(74,13,4,'保质期','7',4,'2021-08-14 21:51:50','2021-08-17 10:46:50',1),(75,13,5,'储存条件','冷场',5,'2021-08-14 21:51:50','2021-08-17 10:46:50',1),(76,13,1,'品牌','农商',1,'2021-08-14 21:51:50','2021-08-17 10:54:16',1),(77,13,2,'规格','500g±30g',2,'2021-08-14 21:51:50','2021-08-17 10:54:16',1),(78,13,3,'产地','成都',3,'2021-08-14 21:51:50','2021-08-17 10:54:16',1),(79,13,4,'保质期','7',4,'2021-08-14 21:51:50','2021-08-17 10:54:16',1),(80,13,5,'储存条件','冷场',5,'2021-08-14 21:51:50','2021-08-17 10:54:16',1),(81,13,1,'品牌','农商',1,'2021-08-14 21:51:50','2021-08-17 11:02:50',1),(82,13,2,'规格','500g±30g',2,'2021-08-14 21:51:50','2021-08-17 11:02:50',1),(83,13,3,'产地','成都',3,'2021-08-14 21:51:50','2021-08-17 11:02:50',1),(84,13,4,'保质期','7',4,'2021-08-14 21:51:50','2021-08-17 11:02:50',1),(85,13,5,'储存条件','冷场',5,'2021-08-14 21:51:50','2021-08-17 11:02:50',1),(86,13,1,'品牌','农商',1,'2021-08-17 11:02:50','2021-08-17 11:02:50',0),(87,13,2,'规格','500g±30g',2,'2021-08-17 11:02:50','2021-08-17 11:02:50',0),(88,13,3,'产地','成都',3,'2021-08-17 11:02:50','2021-08-17 11:02:50',0),(89,13,4,'保质期','7',4,'2021-08-17 11:02:50','2021-08-17 11:02:50',0),(90,13,5,'储存条件','冷场',5,'2021-08-17 11:02:50','2021-08-17 11:02:50',0),(91,12,1,'品牌','农家',1,'2021-08-17 11:03:36','2021-08-17 11:28:59',1),(92,12,2,'规格','300g±30g',2,'2021-08-17 11:03:36','2021-08-17 11:28:59',1),(93,12,3,'产地','成都',3,'2021-08-17 11:03:36','2021-08-17 11:28:59',1),(94,12,4,'保质期','7',4,'2021-08-17 11:03:36','2021-08-17 11:28:59',1),(95,12,5,'储存条件','常温',5,'2021-08-17 11:03:36','2021-08-17 11:28:59',1),(96,11,1,'品牌','农家',1,'2021-08-17 11:03:54','2021-08-17 11:03:54',0),(97,11,2,'规格','300g±30g',2,'2021-08-17 11:03:54','2021-08-17 11:03:54',0),(98,11,3,'产地','成都',3,'2021-08-17 11:03:54','2021-08-17 11:03:54',0),(99,11,4,'保质期','7',4,'2021-08-17 11:03:54','2021-08-17 11:03:54',0),(100,11,5,'储存条件','常温',5,'2021-08-17 11:03:54','2021-08-17 11:03:54',0),(101,1,1,'品牌','自产',1,'2021-08-17 11:25:47','2021-09-18 09:40:23',1),(102,1,2,'规格','300g±30g',2,'2021-08-17 11:25:47','2021-09-18 09:40:23',1),(103,1,3,'产地','成都龙泉',3,'2021-08-17 11:25:47','2021-09-18 09:40:23',1),(104,1,4,'保质期','3天',4,'2021-08-17 11:25:47','2021-09-18 09:40:23',1),(105,1,5,'储存条件','常温',5,'2021-08-17 11:25:48','2021-09-18 09:40:23',1),(106,2,1,'品牌','自产',1,'2021-08-17 11:26:23','2021-08-17 11:26:23',0),(107,2,2,'规格','250',2,'2021-08-17 11:26:23','2021-08-17 11:26:23',0),(108,2,3,'产地','成都郫县',3,'2021-08-17 11:26:23','2021-08-17 11:26:23',0),(109,2,4,'保质期','180天',4,'2021-08-17 11:26:23','2021-08-17 11:26:23',0),(110,2,5,'储存条件','常温',5,'2021-08-17 11:26:23','2021-08-17 11:26:23',0),(111,3,1,'品牌','宏达',1,'2021-08-17 11:26:44','2021-08-17 11:26:44',0),(112,3,2,'规格','300g±30g',2,'2021-08-17 11:26:44','2021-08-17 11:26:44',0),(113,3,3,'产地','成都龙泉',3,'2021-08-17 11:26:44','2021-08-17 11:26:44',0),(114,3,4,'保质期','3天',4,'2021-08-17 11:26:44','2021-08-17 11:26:44',0),(115,3,5,'储存条件','冷场',5,'2021-08-17 11:26:44','2021-08-17 11:26:44',0),(116,4,1,'品牌','三菱',1,'2021-08-17 11:27:12','2021-09-18 09:35:51',1),(117,4,2,'规格','500g±30g',2,'2021-08-17 11:27:12','2021-09-18 09:35:51',1),(118,4,3,'产地','成都达州',3,'2021-08-17 11:27:12','2021-09-18 09:35:51',1),(119,4,4,'保质期','180天',4,'2021-08-17 11:27:12','2021-09-18 09:35:51',1),(120,4,5,'储存条件','常温',5,'2021-08-17 11:27:12','2021-09-18 09:35:51',1),(121,5,1,'品牌','自产',1,'2021-08-17 11:27:29','2021-09-28 06:08:58',1),(122,5,2,'规格','500±30g',2,'2021-08-17 11:27:29','2021-09-28 06:08:58',1),(123,5,3,'产地','四川巴中',3,'2021-08-17 11:27:29','2021-09-28 06:08:58',1),(124,5,4,'保质期','200天',4,'2021-08-17 11:27:29','2021-09-28 06:08:58',1),(125,5,5,'储存条件','常温',5,'2021-08-17 11:27:29','2021-09-28 06:08:58',1),(126,6,1,'品牌','自产',1,'2021-08-17 11:27:49','2021-08-17 11:27:49',0),(127,6,2,'规格','500g±30g',2,'2021-08-17 11:27:49','2021-08-17 11:27:49',0),(128,6,3,'产地','四川达州',3,'2021-08-17 11:27:49','2021-08-17 11:27:49',0),(129,6,4,'保质期','3',4,'2021-08-17 11:27:49','2021-08-17 11:27:49',0),(130,6,5,'储存条件','冷场',5,'2021-08-17 11:27:49','2021-08-17 11:27:49',0),(131,7,1,'品牌','自产',1,'2021-08-17 11:28:10','2021-09-27 10:38:32',1),(132,7,2,'规格','300g±30g',2,'2021-08-17 11:28:10','2021-09-27 10:38:32',1),(133,7,3,'产地','四川达州',3,'2021-08-17 11:28:10','2021-09-27 10:38:32',1),(134,7,4,'保质期','4天',4,'2021-08-17 11:28:10','2021-09-27 10:38:32',1),(135,7,5,'储存条件','常温',5,'2021-08-17 11:28:10','2021-09-27 10:38:32',1),(136,8,1,'品牌','自产',1,'2021-08-17 11:28:25','2021-08-17 11:28:25',0),(137,8,2,'规格','500g±30g',2,'2021-08-17 11:28:25','2021-08-17 11:28:25',0),(138,8,3,'产地','四川内江',3,'2021-08-17 11:28:25','2021-08-17 11:28:25',0),(139,8,4,'保质期','5天',4,'2021-08-17 11:28:26','2021-08-17 11:28:26',0),(140,8,5,'储存条件','常温',5,'2021-08-17 11:28:26','2021-08-17 11:28:26',0),(141,9,1,'品牌','自产',1,'2021-08-17 11:28:42','2021-08-17 11:28:42',0),(142,9,2,'规格','500g±30g',2,'2021-08-17 11:28:42','2021-08-17 11:28:42',0),(143,9,3,'产地','四川巴中',3,'2021-08-17 11:28:42','2021-08-17 11:28:42',0),(144,9,4,'保质期','5天',4,'2021-08-17 11:28:42','2021-08-17 11:28:42',0),(145,9,5,'储存条件','冷场',5,'2021-08-17 11:28:42','2021-08-17 11:28:42',0),(146,12,1,'品牌','农家',1,'2021-08-17 11:28:59','2021-08-17 11:28:59',0),(147,12,2,'规格','300g±30g',2,'2021-08-17 11:28:59','2021-08-17 11:28:59',0),(148,12,3,'产地','成都',3,'2021-08-17 11:28:59','2021-08-17 11:28:59',0),(149,12,4,'保质期','7',4,'2021-08-17 11:28:59','2021-08-17 11:28:59',0),(150,12,5,'储存条件','常温',5,'2021-08-17 11:28:59','2021-08-17 11:28:59',0),(151,10,1,'品牌','农家',1,'2021-09-18 09:28:28','2021-09-18 09:28:28',0),(152,10,2,'规格','南瓜+2kg',2,'2021-09-18 09:28:28','2021-09-18 09:28:28',0),(153,10,3,'产地','成都',3,'2021-09-18 09:28:28','2021-09-18 09:28:28',0),(154,10,4,'保质期','180天 ',4,'2021-09-18 09:28:28','2021-09-18 09:28:28',0),(155,10,5,'储存条件','常温',5,'2021-09-18 09:28:28','2021-09-18 09:28:28',0),(156,4,1,'品牌','三菱',1,'2021-09-18 09:35:51','2021-09-18 09:35:51',0),(157,4,2,'规格','500g±30g',2,'2021-09-18 09:35:51','2021-09-18 09:35:51',0),(158,4,3,'产地','成都达州',3,'2021-09-18 09:35:51','2021-09-18 09:35:51',0),(159,4,4,'保质期','180天',4,'2021-09-18 09:35:51','2021-09-18 09:35:51',0),(160,4,5,'储存条件','常温',5,'2021-09-18 09:35:51','2021-09-18 09:35:51',0),(161,1,1,'品牌','自产',1,'2021-09-18 09:40:23','2021-09-18 09:41:28',1),(162,1,2,'规格','300g±30g',2,'2021-09-18 09:40:23','2021-09-18 09:41:28',1),(163,1,3,'产地','成都龙泉',3,'2021-09-18 09:40:23','2021-09-18 09:41:28',1),(164,1,4,'保质期','3天',4,'2021-09-18 09:40:23','2021-09-18 09:41:28',1),(165,1,5,'储存条件','常温',5,'2021-09-18 09:40:23','2021-09-18 09:41:28',1),(166,1,1,'品牌','自产',1,'2021-09-18 09:41:29','2021-09-18 09:55:42',1),(167,1,2,'规格','300g±30g',2,'2021-09-18 09:41:29','2021-09-18 09:55:42',1),(168,1,3,'产地','成都龙泉',3,'2021-09-18 09:41:29','2021-09-18 09:55:42',1),(169,1,4,'保质期','3天',4,'2021-09-18 09:41:29','2021-09-18 09:55:42',1),(170,1,5,'储存条件','常温',5,'2021-09-18 09:41:29','2021-09-18 09:55:42',1),(171,1,1,'品牌','自产',1,'2021-09-18 09:55:42','2021-09-18 09:56:00',1),(172,1,2,'规格','300g±30g',2,'2021-09-18 09:55:42','2021-09-18 09:56:00',1),(173,1,3,'产地','成都龙泉',3,'2021-09-18 09:55:42','2021-09-18 09:56:00',1),(174,1,4,'保质期','3天',4,'2021-09-18 09:55:42','2021-09-18 09:56:00',1),(175,1,5,'储存条件','常温',5,'2021-09-18 09:55:42','2021-09-18 09:56:00',1),(176,1,1,'品牌','自产',1,'2021-09-18 09:56:00','2021-09-18 09:56:11',1),(177,1,2,'规格','300g±30g',2,'2021-09-18 09:56:01','2021-09-18 09:56:11',1),(178,1,3,'产地','成都龙泉',3,'2021-09-18 09:56:01','2021-09-18 09:56:11',1),(179,1,4,'保质期','3天',4,'2021-09-18 09:56:01','2021-09-18 09:56:11',1),(180,1,5,'储存条件','常温',5,'2021-09-18 09:56:01','2021-09-18 09:56:11',1),(181,1,1,'品牌','自产',1,'2021-09-18 09:56:11','2021-09-18 09:56:11',0),(182,1,2,'规格','300g±30g',2,'2021-09-18 09:56:11','2021-09-18 09:56:11',0),(183,1,3,'产地','成都龙泉',3,'2021-09-18 09:56:11','2021-09-18 09:56:11',0),(184,1,4,'保质期','3天',4,'2021-09-18 09:56:11','2021-09-18 09:56:11',0),(185,1,5,'储存条件','常温',5,'2021-09-18 09:56:11','2021-09-18 09:56:11',0),(186,7,1,'品牌','自产',1,'2021-09-27 10:38:32','2021-09-27 10:42:35',1),(187,7,2,'规格','300g±30g',2,'2021-09-27 10:38:32','2021-09-27 10:42:35',1),(188,7,3,'产地','四川达州',3,'2021-09-27 10:38:32','2021-09-27 10:42:35',1),(189,7,4,'保质期','4天',4,'2021-09-27 10:38:32','2021-09-27 10:42:35',1),(190,7,5,'储存条件','常温',5,'2021-09-27 10:38:32','2021-09-27 10:42:35',1),(191,7,1,'品牌','自产',1,'2021-09-27 10:42:35','2021-09-27 10:42:35',0),(192,7,2,'规格','300g±30g',2,'2021-09-27 10:42:35','2021-09-27 10:42:35',0),(193,7,3,'产地','四川达州',3,'2021-09-27 10:42:35','2021-09-27 10:42:35',0),(194,7,4,'保质期','4天',4,'2021-09-27 10:42:35','2021-09-27 10:42:35',0),(195,7,5,'储存条件','常温',5,'2021-09-27 10:42:35','2021-09-27 10:42:35',0),(196,5,1,'品牌','自产',1,'2021-09-28 06:08:58','2021-09-28 06:08:58',0),(197,5,2,'规格','500±30g',2,'2021-09-28 06:08:58','2021-09-28 06:08:58',0),(198,5,3,'产地','四川巴中',3,'2021-09-28 06:08:58','2021-09-28 06:08:58',0),(199,5,4,'保质期','200天',4,'2021-09-28 06:08:58','2021-09-28 06:08:58',0),(200,5,5,'储存条件','常温',5,'2021-09-28 06:08:58','2021-09-28 06:08:58',0),(201,15,1,'品牌','寿光苹果',1,'2023-03-13 17:00:01','2023-03-13 17:00:01',0),(202,15,2,'规格','10kg',2,'2023-03-13 17:00:01','2023-03-13 17:00:01',0),(203,15,3,'产地','山东',3,'2023-03-13 17:00:01','2023-03-13 17:00:01',0),(204,15,4,'保质期','180天',4,'2023-03-13 17:00:01','2023-03-13 17:00:01',0),(205,15,5,'储存条件','常温',5,'2023-03-13 17:00:01','2023-03-13 17:00:01',0),(206,16,6,'储存条件','冷场',1,'2023-03-14 11:39:22','2023-03-14 11:39:22',0),(207,17,9,'品牌名称','未知',NULL,'2023-04-04 21:33:20','2023-04-04 22:39:56',1),(208,17,10,'规格','10*10',NULL,'2023-04-04 21:33:20','2023-04-04 22:39:56',1),(209,18,9,'品牌名称','托马斯',NULL,'2023-04-04 21:39:46','2023-04-04 22:41:07',1),(210,18,10,'规格','12*20',NULL,'2023-04-04 21:39:46','2023-04-04 22:41:07',1),(211,17,9,'品牌名称','未知',NULL,'2023-04-04 22:39:56','2023-04-04 22:39:56',0),(212,17,10,'规格','10*10',NULL,'2023-04-04 22:39:56','2023-04-04 22:39:56',0),(213,18,9,'品牌名称','托马斯',NULL,'2023-04-04 22:41:07','2023-04-04 22:41:07',0),(214,18,10,'规格','12*20',NULL,'2023-04-04 22:41:07','2023-04-04 22:41:07',0),(215,19,9,'品牌名称','托马斯',NULL,'2023-04-06 21:33:10','2023-04-06 21:33:10',0),(216,19,10,'规格','10*10',NULL,'2023-04-06 21:33:10','2023-04-06 21:33:10',0),(217,20,9,'品牌名称','东方红',NULL,'2023-04-06 21:41:27','2023-04-06 21:41:27',0),(218,20,10,'规格','12*20',NULL,'2023-04-06 21:41:27','2023-04-06 21:41:27',0);

/*Table structure for table `sku_detail` */

DROP TABLE IF EXISTS `sku_detail`;

CREATE TABLE `sku_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT '商品id',
  `detail_html` text COMMENT '详情内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='spu属性值';

/*Data for the table `sku_detail` */

insert  into `sku_detail`(`id`,`sku_id`,`detail_html`,`create_time`,`update_time`,`is_deleted`) values (1,1,'西红柿','2021-06-06 17:53:03','2021-06-06 18:00:18',0),(2,2,'红薯','2021-06-06 18:04:27','2021-06-06 18:04:27',0),(3,3,'红薯','2021-06-06 18:05:57','2021-06-06 18:05:57',0),(4,4,'大蒜','2021-06-06 18:09:42','2021-06-06 18:09:42',0),(5,5,'土豆','2021-06-06 18:10:56','2021-06-06 18:10:56',0),(6,6,'丝瓜','2021-06-06 18:13:46','2021-06-06 18:13:46',0),(7,7,'辣椒','2021-06-06 18:15:18','2021-06-06 18:15:18',0),(8,8,'茄子','2021-06-06 18:16:26','2021-06-06 18:16:26',0),(9,9,'玉米','2021-06-06 18:17:47','2021-06-06 18:17:47',0),(10,10,'南瓜','2021-08-14 21:02:56','2021-08-14 21:02:56',0),(11,11,'苹果300g±30g','2021-08-14 21:26:30','2021-08-14 21:26:30',0),(12,12,'橘子300g±30g','2021-08-14 21:28:16','2021-08-14 21:28:16',0),(13,13,NULL,'2021-08-14 21:51:50','2021-08-27 21:17:30',0);

/*Table structure for table `sku_image` */

DROP TABLE IF EXISTS `sku_image`;

CREATE TABLE `sku_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `img_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb3 COMMENT='商品图片';

/*Data for the table `sku_image` */

insert  into `sku_image`(`id`,`sku_id`,`img_name`,`img_url`,`sort`,`create_time`,`update_time`,`is_deleted`) values (53,11,'pngguo.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/pngguo.jpg',1,'2021-08-17 11:03:54','2023-04-13 13:28:49',0),(56,2,'hongshu.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg',1,'2021-08-17 11:26:23','2023-04-13 13:25:39',0),(57,2,'hongshu.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg',2,'2021-08-17 11:26:23','2023-04-13 13:25:39',0),(58,3,'sijidou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg',1,'2021-08-17 11:26:43','2023-04-13 13:26:08',0),(59,3,'sijidou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg',2,'2021-08-17 11:26:43','2023-04-13 13:26:08',0),(60,3,'sijidou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg',3,'2021-08-17 11:26:43','2023-04-13 13:26:08',0),(66,6,'sigua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg',1,'2021-08-17 11:27:49','2023-04-13 13:27:23',0),(67,6,'sigua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg',2,'2021-08-17 11:27:49','2023-04-13 13:27:23',0),(71,8,'qiezi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/qiezi.jpg',1,'2021-08-17 11:28:25','2023-04-13 13:28:01',0),(72,8,'qiezi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/qiezi.jpg',2,'2021-08-17 11:28:25','2023-04-13 13:28:01',0),(73,9,'yumi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg',1,'2021-08-17 11:28:42','2023-04-13 13:28:01',0),(74,9,'yumi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg',2,'2021-08-17 11:28:42','2023-04-13 13:28:01',0),(75,9,'yumi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg',3,'2021-08-17 11:28:42','2023-04-13 13:28:01',0),(76,12,'juzi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/juzi.jpg',1,'2021-08-17 11:28:59','2023-04-13 13:28:49',0),(77,10,'nangua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg',1,'2021-09-18 09:28:28','2023-04-13 13:28:49',0),(78,10,'nangua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg',2,'2021-09-18 09:28:28','2023-04-13 13:28:49',0),(79,4,'dasuan.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg',1,'2021-08-17 11:27:12','2023-04-13 13:26:26',0),(80,4,'dasuan.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg',2,'2021-08-17 11:27:12','2023-04-13 13:26:27',0),(81,4,'dasuan.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg',3,'2021-08-17 11:27:12','2023-04-13 13:26:27',0),(92,1,'xihongshi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg',1,'2021-08-17 11:25:47','2023-04-13 13:25:17',0),(93,1,'xihongshi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg',2,'2021-08-17 11:25:47','2023-04-13 13:25:17',0),(100,5,'tudou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg',1,'2021-08-17 11:27:29','2023-04-13 13:26:42',0),(101,5,'tudou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg',2,'2021-08-17 11:27:29','2023-04-13 13:26:42',0);

/*Table structure for table `sku_info` */

DROP TABLE IF EXISTS `sku_info`;

CREATE TABLE `sku_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL DEFAULT '0' COMMENT '分类id',
  `attr_group_id` bigint NOT NULL DEFAULT '0' COMMENT '平台属性分组id',
  `sku_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商品类型：0->普通商品 1->秒杀商品',
  `sku_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'sku名称',
  `img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '展示图片',
  `per_limit` int NOT NULL DEFAULT '1' COMMENT '限购个数/每天（0：不限购）',
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '上架状态：0->下架；1->上架',
  `check_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态：0->未审核；1->审核通过',
  `is_new_person` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新人专享：0->否；1->是',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `sku_code` varchar(30) DEFAULT NULL COMMENT 'sku编码',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '价格',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `lock_stock` int NOT NULL DEFAULT '0' COMMENT '锁定库存',
  `low_stock` int NOT NULL DEFAULT '0' COMMENT '预警库存',
  `sale` int NOT NULL DEFAULT '0' COMMENT '销量',
  `ware_id` bigint NOT NULL DEFAULT '0' COMMENT '仓库',
  `version` bigint NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COMMENT='sku信息';

/*Data for the table `sku_info` */

insert  into `sku_info`(`id`,`category_id`,`attr_group_id`,`sku_type`,`sku_name`,`img_url`,`per_limit`,`publish_status`,`check_status`,`is_new_person`,`sort`,`sku_code`,`price`,`market_price`,`stock`,`lock_stock`,`low_stock`,`sale`,`ware_id`,`version`,`create_time`,`update_time`,`is_deleted`) values (1,1,1,0,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg',5,1,1,0,1,'0001','2.20','2.90',99,20,10,1,1,2,'2021-06-06 17:53:03','2023-04-27 13:39:32',0),(2,1,1,0,'红薯','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg',5,1,1,0,2,'0002','1.79','2.50',100,25,10,0,1,2,'2021-06-06 18:04:27','2023-04-25 21:34:26',0),(3,1,1,0,'四季豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg',5,1,1,0,3,'0003','3.50','4.10',100,97,10,0,1,1,'2021-06-06 18:05:57','2023-04-25 11:46:13',0),(4,1,1,0,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg',5,1,1,0,4,'0004','5.50','7.80',100,47,10,0,1,2,'2021-06-06 18:09:42','2023-04-25 19:43:45',0),(5,1,1,0,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg',5,1,1,1,5,'0005','5.30','5.90',100,62,10,0,1,1,'2021-06-06 18:10:56','2023-04-25 21:34:26',0),(6,1,1,0,'丝瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg',5,1,1,1,6,'0006','3.60','4.50',100,26,10,0,1,1,'2021-06-06 18:13:46','2023-04-27 13:39:32',0),(8,1,1,1,'茄子','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/qiezi.jpg',5,0,1,0,8,'0008','3.50','4.40',100,0,10,0,1,1,'2021-06-06 18:16:26','2023-04-13 13:23:02',0),(9,1,1,1,'玉米','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg',5,0,1,0,9,'0009','1.90','3.00',100,0,10,0,1,1,'2021-06-06 18:17:47','2023-04-13 13:23:02',0),(10,1,1,0,'南瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg',5,1,1,1,10,'555667','5.00','4.00',100,43,10,0,1,0,'2021-08-14 21:02:56','2023-04-25 20:36:23',0),(11,2,1,0,'苹果','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/pngguo.jpg',5,1,1,0,11,'0011','5.00','7.00',100,3,10,0,1,0,'2021-08-14 21:26:30','2023-04-25 19:36:33',0),(12,2,1,0,'橘子','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/juzi.jpg',5,1,1,0,12,'0012','7.00','8.00',100,0,10,0,1,0,'2021-08-14 21:28:16','2023-04-13 13:23:02',0),(13,1,1,0,'蔬菜拼盘','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/shucaipingpan.jpg',5,1,1,0,13,'0013','50.00','70.00',100,59,10,0,1,0,'2021-08-14 21:51:50','2023-04-13 13:23:47',0);

/*Table structure for table `sku_poster` */

DROP TABLE IF EXISTS `sku_poster`;

CREATE TABLE `sku_poster` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sku_id` bigint DEFAULT NULL COMMENT '商品id',
  `img_name` varchar(200) DEFAULT NULL COMMENT '文件名称',
  `img_url` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品海报表';

/*Data for the table `sku_poster` */

insert  into `sku_poster`(`id`,`sku_id`,`img_name`,`img_url`,`create_time`,`update_time`,`is_deleted`) values (36,11,'pngguo.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/pngguo.jpg','2021-09-18 09:40:01','2023-04-13 13:33:37',0),(39,2,'hongshu.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg','2021-09-18 09:40:01','2023-04-13 13:30:57',0),(40,2,'hongshu.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg','2021-09-18 09:40:01','2023-04-13 13:30:57',0),(41,3,'sijidou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg','2021-09-18 09:40:01','2023-04-13 13:31:19',0),(49,6,'sigua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg','2021-09-18 09:40:01','2023-04-13 13:32:26',0),(50,6,'sigua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg','2021-09-18 09:40:01','2023-04-13 13:32:26',0),(54,8,'qiezi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/qiezi.jpg','2021-09-18 09:40:01','2023-04-13 13:33:02',0),(55,8,'qiezi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/qiezi.jpg','2021-09-18 09:40:01','2023-04-13 13:33:02',0),(56,9,'yumi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg','2021-09-18 09:40:01','2023-04-13 13:33:02',0),(57,9,'yumi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/yumi.jpg','2021-09-18 09:40:01','2023-04-13 13:33:02',0),(59,12,'juzi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/juzi.jpg','2021-09-18 09:40:01','2023-04-13 13:33:49',0),(60,10,'nangua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg','2021-09-18 09:40:01','2023-04-13 13:33:20',0),(61,10,'nangua.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg','2021-09-18 09:40:01','2023-04-13 13:33:20',0),(62,4,'dasuan.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','2021-09-18 09:40:01','2023-04-13 13:31:38',0),(63,4,'dasuan.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','2021-09-18 09:40:01','2023-04-13 13:31:38',0),(138,1,'xihongshi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2021-09-18 09:41:28','2023-04-13 13:30:57',0),(139,1,'xihongshi.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2021-09-18 09:41:28','2023-04-13 13:30:57',0),(146,5,'tudou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','2021-09-18 09:40:01','2023-04-13 13:32:26',0),(147,5,'tudou.jpg','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','2021-09-18 09:40:01','2023-04-13 13:32:26',0);

/*Table structure for table `sku_stock_history` */

DROP TABLE IF EXISTS `sku_stock_history`;

CREATE TABLE `sku_stock_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sku_id` bigint NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价格',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `sale` int NOT NULL DEFAULT '0' COMMENT '销量',
  `sale_date` date DEFAULT NULL COMMENT '销售日期',
  `ware_id` bigint NOT NULL DEFAULT '0' COMMENT '仓库',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '1' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='sku的库存历史记录';

/*Data for the table `sku_stock_history` */

/*Table structure for table `ware` */

DROP TABLE IF EXISTS `ware`;

CREATE TABLE `ware` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `province` varchar(20) DEFAULT NULL COMMENT '省code',
  `city` varchar(20) DEFAULT NULL COMMENT '城市code',
  `district` varchar(20) DEFAULT NULL COMMENT '区域code',
  `detail_address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `longitude` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='仓库表';

/*Data for the table `ware` */

insert  into `ware`(`id`,`name`,`province`,`city`,`district`,`detail_address`,`longitude`,`latitude`,`create_time`,`update_time`,`is_deleted`) values (1,'成都仓库',NULL,NULL,NULL,NULL,NULL,NULL,'2021-06-15 08:07:17','2021-06-15 08:07:17',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
