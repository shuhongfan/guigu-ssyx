/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 8.0.28 : Database - shequ-order
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shequ-order` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shequ-order`;

/*Table structure for table `cart_info` */

DROP TABLE IF EXISTS `cart_info`;

CREATE TABLE `cart_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` varchar(200) DEFAULT NULL COMMENT '用户id',
  `category_id` bigint DEFAULT NULL COMMENT '分类id',
  `sku_type` tinyint DEFAULT NULL COMMENT 'sku类型',
  `sku_name` varchar(200) DEFAULT NULL COMMENT 'sku名称 (冗余)',
  `sku_id` bigint DEFAULT NULL COMMENT 'skuid',
  `cart_price` decimal(10,2) DEFAULT NULL COMMENT '放入购物车时价格',
  `sku_num` int DEFAULT NULL COMMENT '数量',
  `per_limit` int DEFAULT NULL COMMENT '限购个数',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片文件',
  `is_checked` tinyint(1) NOT NULL DEFAULT '1',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1：正常 0：无效）',
  `ware_id` bigint DEFAULT NULL COMMENT '仓库id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='购物车表';

/*Data for the table `cart_info` */

/*Table structure for table `order_deliver` */

DROP TABLE IF EXISTS `order_deliver`;

CREATE TABLE `order_deliver` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deliver_date` datetime DEFAULT NULL COMMENT '配送日期',
  `leader_id` bigint NOT NULL DEFAULT '0' COMMENT '团长id',
  `driver_id` bigint DEFAULT NULL COMMENT '司机id',
  `driver_name` varchar(20) DEFAULT NULL COMMENT '司机名称',
  `driver_phone` varchar(11) DEFAULT NULL COMMENT '司机电话',
  `status` tinyint DEFAULT NULL COMMENT '状态（0：默认，1：已发货，2：团长收货）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='订单配送表';

/*Data for the table `order_deliver` */

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '会员_id',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `order_no` char(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `coupon_id` bigint DEFAULT NULL COMMENT '使用的优惠券',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总额',
  `activity_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '促销金额',
  `coupon_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券',
  `original_total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '原价金额',
  `feight_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `feight_fee_reduce` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '减免运费',
  `refundable_time` datetime DEFAULT NULL COMMENT '可退款日期（签收后1天）',
  `pay_type` tinyint DEFAULT NULL COMMENT '支付方式【1->微信】',
  `source_type` tinyint DEFAULT NULL COMMENT '订单来源[0->小程序；1->H5]',
  `order_status` tinyint NOT NULL DEFAULT '0' COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->待用户收货，已完成；-1->已取消】',
  `process_status` tinyint NOT NULL DEFAULT '0',
  `leader_id` bigint NOT NULL DEFAULT '0' COMMENT '团长id',
  `leader_name` varchar(20) DEFAULT NULL COMMENT '团长名称',
  `leader_phone` varchar(11) DEFAULT NULL COMMENT '团长电话',
  `take_name` varchar(50) DEFAULT '' COMMENT '提货点名称',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` bigint DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` bigint DEFAULT NULL COMMENT '城市',
  `receiver_district` bigint DEFAULT NULL COMMENT '区',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `take_time` datetime DEFAULT NULL COMMENT '提货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '订单备注',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消订单时间',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '取消订单原因',
  `ware_id` bigint NOT NULL DEFAULT '0' COMMENT '仓库id',
  `commission_amount` decimal(10,2) DEFAULT NULL COMMENT '团长佣金',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb3 COMMENT='订单';

/*Data for the table `order_info` */

insert  into `order_info`(`id`,`user_id`,`nick_name`,`order_no`,`coupon_id`,`total_amount`,`activity_amount`,`coupon_amount`,`original_total_amount`,`feight_fee`,`feight_fee_reduce`,`refundable_time`,`pay_type`,`source_type`,`order_status`,`process_status`,`leader_id`,`leader_name`,`leader_phone`,`take_name`,`receiver_name`,`receiver_phone`,`receiver_post_code`,`receiver_province`,`receiver_city`,`receiver_district`,`receiver_address`,`payment_time`,`delivery_time`,`take_time`,`receive_time`,`remark`,`cancel_time`,`cancel_reason`,`ware_id`,`commission_amount`,`create_time`,`update_time`,`is_deleted`) values (169,31,NULL,'1682240682997',NULL,'10.80','0.00','0.00','10.80','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','杨大力','13978909876',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-23 17:04:58','2023-04-23 17:04:58',0),(170,31,NULL,'1682392293341',NULL,'3.50','0.00','0.00','3.50','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','杨大力','13980798765',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:12:16','2023-04-25 11:12:16',0),(171,31,NULL,'1682392518915',NULL,'10.80','0.00','0.00','10.80','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','111','13567890987',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:15:47','2023-04-25 11:15:47',0),(172,31,NULL,'1682393446100',NULL,'8.80','0.00','0.00','8.80','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','333','13567897890',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:30:56','2023-04-25 11:30:56',0),(173,31,NULL,'1682393662435',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','4','13456789099',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:34:32','2023-04-25 11:34:32',0),(174,31,NULL,'1682393852353',NULL,'7.50','0.00','0.00','7.50','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','444','13456787654',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:37:58','2023-04-25 11:37:58',0),(175,31,NULL,'1682393996770',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','55','13456543333',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:40:11','2023-04-25 11:40:11',0),(176,31,NULL,'1682394075104',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','666','13667765544',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:41:23','2023-04-25 11:41:23',0),(177,31,NULL,'1682394273827',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','777','13556654789',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:44:42','2023-04-25 11:44:42',0),(178,31,NULL,'1682394354592',NULL,'14.30','0.00','0.00','14.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','9999','13987654321',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 11:46:14','2023-04-25 11:46:14',0),(179,31,NULL,'1682415120296',NULL,'10.80','0.00','0.00','10.80','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','4532','13425431234',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 17:32:17','2023-04-25 17:32:17',0),(180,27,NULL,'1682416124200',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','56','13421231234',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 17:48:53','2023-04-25 17:48:53',0),(181,31,NULL,'1682416819890',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','1','13456789000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 18:00:28','2023-04-25 18:00:28',0),(182,36,NULL,'1682417748665',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','5','13456789090',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 18:15:57','2023-04-25 18:15:57',0),(183,36,NULL,'1682422583069',NULL,'5.00','0.00','0.00','5.00','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','33','13456789099',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:36:34','2023-04-25 19:36:34',0),(184,36,NULL,'1682422852768',NULL,'5.00','0.00','0.00','5.00','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','3','13454321234',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:40:59','2023-04-25 19:40:59',0),(185,36,NULL,'1682423016938',NULL,'5.50','0.00','0.00','5.50','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','5','13555555555',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:43:45','2023-04-25 19:43:45',0),(186,36,NULL,'1682423276196',NULL,'5.00','0.00','0.00','5.00','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','5','13456789000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:48:03','2023-04-25 19:48:03',0),(187,36,NULL,'1682423709092',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','6','13456789000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:55:17','2023-04-25 19:55:17',0),(188,36,NULL,'1682423947261',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','4','13423456789',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 19:59:14','2023-04-25 19:59:14',0),(189,36,NULL,'1682424457246',NULL,'2.20','0.00','0.00','2.20','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','6','13455431234',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:07:45','2023-04-25 20:07:45',0),(190,36,NULL,'1682425039734',NULL,'3.60','0.00','0.00','3.60','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','6','13667789900',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:17:27','2023-04-25 20:17:27',0),(191,36,NULL,'1682425829654',NULL,'3.60','0.00','0.00','3.60','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','5','13555555555',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:30:36','2023-04-25 20:30:36',0),(192,36,NULL,'1682426019007',NULL,'2.20','0.00','0.00','2.20','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','6','13666666666',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:33:47','2023-04-25 20:33:47',0),(193,36,NULL,'1682426174095',NULL,'5.00','0.00','0.00','5.00','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','5','13555567788',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:36:23','2023-04-25 20:36:23',0),(194,36,NULL,'1682426422674',NULL,'5.30','0.00','0.00','5.30','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','6','13445567788',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:40:29','2023-04-25 20:40:29',0),(195,36,NULL,'1682426743216',NULL,'2.20','0.00','0.00','2.20','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','张大虎','18978009876',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 20:46:02','2023-04-25 20:46:02',0),(196,36,NULL,'1682429647121',NULL,'7.09','0.00','0.00','7.09','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','李二牛','15678900000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 21:34:27','2023-04-25 21:34:27',0),(197,36,NULL,'1682435683147',NULL,'2.20','0.00','0.00','2.20','0.00','0.00',NULL,NULL,NULL,1,2,3,'懂华','15012222256','北京魔方公寓店','张三户','13567899000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-25 23:15:04','2023-04-25 23:15:04',0),(198,36,NULL,'1682573952476',NULL,'8.20','2.00','0.00','10.20','0.00','0.00',NULL,NULL,NULL,0,1,3,'懂华','15012222256','北京魔方公寓店','杨大力','13567899000',NULL,NULL,NULL,NULL,'北京魔方公寓店',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'0.00','2023-04-27 13:39:34','2023-04-27 13:39:34',0);

/*Table structure for table `order_item` */

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint DEFAULT NULL COMMENT 'order_id',
  `category_id` bigint DEFAULT NULL COMMENT '商品分类id',
  `sku_type` tinyint DEFAULT NULL COMMENT 'sku类型',
  `sku_id` bigint DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品sku名字',
  `img_url` varchar(500) DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(10,2) DEFAULT NULL COMMENT '商品sku价格',
  `sku_num` int DEFAULT NULL COMMENT '商品购买的数量',
  `split_activity_amount` decimal(10,2) DEFAULT NULL COMMENT '商品促销分解金额',
  `split_coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `split_total_amount` decimal(10,2) DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `leader_id` bigint DEFAULT NULL COMMENT '团长id（冗余）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=433 DEFAULT CHARSET=utf8mb3 COMMENT='订单项信息';

/*Data for the table `order_item` */

insert  into `order_item`(`id`,`order_id`,`category_id`,`sku_type`,`sku_id`,`sku_name`,`img_url`,`sku_price`,`sku_num`,`split_activity_amount`,`split_coupon_amount`,`split_total_amount`,`leader_id`,`create_time`,`update_time`,`is_deleted`) values (394,169,1,0,4,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','5.50',1,'0.00','0.00','5.50',3,'2023-04-23 17:04:58','2023-04-23 17:04:58',0),(395,169,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-23 17:04:58','2023-04-23 17:04:58',0),(396,170,1,0,3,'四季豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg','3.50',1,'0.00','0.00','3.50',3,'2023-04-25 11:12:16','2023-04-25 11:12:16',0),(397,171,1,0,4,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','5.50',1,'0.00','0.00','5.50',3,'2023-04-25 11:15:47','2023-04-25 11:15:47',0),(398,171,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:15:47','2023-04-25 11:15:47',0),(399,172,1,0,3,'四季豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg','3.50',1,'0.00','0.00','3.50',3,'2023-04-25 11:30:56','2023-04-25 11:30:56',0),(400,172,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:30:56','2023-04-25 11:30:56',0),(401,173,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:34:32','2023-04-25 11:34:32',0),(402,174,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',1,'0.00','0.00','2.20',3,'2023-04-25 11:37:58','2023-04-25 11:37:58',0),(403,174,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:37:58','2023-04-25 11:37:58',0),(404,175,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:40:11','2023-04-25 11:40:11',0),(405,176,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:41:23','2023-04-25 11:41:23',0),(406,177,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:44:42','2023-04-25 11:44:42',0),(407,178,1,0,3,'四季豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sijidou.jpg','3.50',1,'0.00','0.00','3.50',3,'2023-04-25 11:46:14','2023-04-25 11:46:14',0),(408,178,1,0,4,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','5.50',1,'0.00','0.00','5.50',3,'2023-04-25 11:46:14','2023-04-25 11:46:14',0),(409,178,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 11:46:14','2023-04-25 11:46:14',0),(410,179,1,0,4,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','5.50',1,'0.00','0.00','5.50',3,'2023-04-25 17:32:17','2023-04-25 17:32:17',0),(411,179,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 17:32:17','2023-04-25 17:32:17',0),(412,180,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 17:48:53','2023-04-25 17:48:53',0),(413,181,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 18:00:28','2023-04-25 18:00:28',0),(414,182,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 18:15:57','2023-04-25 18:15:57',0),(415,183,2,0,11,'苹果','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/pngguo.jpg','5.00',1,'0.00','0.00','5.00',3,'2023-04-25 19:36:34','2023-04-25 19:36:34',0),(416,184,1,0,10,'南瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg','5.00',1,'0.00','0.00','5.00',3,'2023-04-25 19:40:59','2023-04-25 19:40:59',0),(417,185,1,0,4,'大蒜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/dasuan.jpg','5.50',1,'0.00','0.00','5.50',3,'2023-04-25 19:43:45','2023-04-25 19:43:45',0),(418,186,1,0,10,'南瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg','5.00',1,'0.00','0.00','5.00',3,'2023-04-25 19:48:03','2023-04-25 19:48:03',0),(419,187,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 19:55:17','2023-04-25 19:55:17',0),(420,188,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 19:59:14','2023-04-25 19:59:14',0),(421,189,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',1,'0.00','0.00','2.20',3,'2023-04-25 20:07:45','2023-04-25 20:07:45',0),(422,190,1,0,6,'丝瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg','3.60',1,'0.00','0.00','3.60',3,'2023-04-25 20:17:27','2023-04-25 20:17:27',0),(423,191,1,0,6,'丝瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg','3.60',1,'0.00','0.00','3.60',3,'2023-04-25 20:30:36','2023-04-25 20:30:36',0),(424,192,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',1,'0.00','0.00','2.20',3,'2023-04-25 20:33:47','2023-04-25 20:33:47',0),(425,193,1,0,10,'南瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/nangua.jpg','5.00',1,'0.00','0.00','5.00',3,'2023-04-25 20:36:23','2023-04-25 20:36:23',0),(426,194,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 20:40:29','2023-04-25 20:40:29',0),(427,195,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',1,'0.00','0.00','2.20',3,'2023-04-25 20:46:02','2023-04-25 20:46:02',0),(428,196,1,0,2,'红薯','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/hongshu.jpg','1.79',1,'0.00','0.00','1.79',3,'2023-04-25 21:34:27','2023-04-25 21:34:27',0),(429,196,1,0,5,'土豆','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/tudou.jpg','5.30',1,'0.00','0.00','5.30',3,'2023-04-25 21:34:27','2023-04-25 21:34:27',0),(430,197,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',1,'0.00','0.00','2.20',3,'2023-04-25 23:15:04','2023-04-25 23:15:04',0),(431,198,1,0,1,'西红柿','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/xihongshi.jpg','2.20',3,'2.00','0.00','4.60',3,'2023-04-27 13:39:34','2023-04-27 13:39:34',0),(432,198,1,0,6,'丝瓜','https://ssyx-guigu.oss-cn-beijing.aliyuncs.com/img/sigua.jpg','3.60',1,'0.00','0.00','3.60',3,'2023-04-27 13:39:34','2023-04-27 13:39:34',0);

/*Table structure for table `order_log` */

DROP TABLE IF EXISTS `order_log`;

CREATE TABLE `order_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `operate_user` varchar(100) DEFAULT NULL COMMENT '操作人：用户；系统；后台管理员',
  `process_status` int DEFAULT NULL COMMENT '订单状态',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb3 COMMENT='订单操作日志记录';

/*Data for the table `order_log` */

insert  into `order_log`(`id`,`order_id`,`operate_user`,`process_status`,`note`,`create_time`,`update_time`,`is_deleted`) values (1,1,NULL,1,NULL,'2021-08-18 14:14:01','2021-08-18 14:14:01',0),(2,2,NULL,1,NULL,'2021-09-14 16:17:35','2021-09-14 16:17:35',0),(3,3,NULL,1,NULL,'2021-09-14 19:46:10','2021-09-14 19:46:10',0),(4,4,NULL,1,NULL,'2021-09-14 20:34:03','2021-09-14 20:34:03',0),(5,5,NULL,1,NULL,'2021-09-14 21:09:14','2021-09-14 21:09:14',0),(6,6,NULL,1,NULL,'2021-09-14 21:29:21','2021-09-14 21:29:21',0),(7,7,NULL,1,NULL,'2021-09-14 21:30:22','2021-09-14 21:30:22',0),(8,8,NULL,1,NULL,'2021-09-14 21:35:19','2021-09-14 21:35:19',0),(9,9,NULL,1,NULL,'2021-09-14 21:50:04','2021-09-14 21:50:04',0),(10,10,NULL,1,NULL,'2021-09-15 17:07:21','2021-09-15 17:07:21',0),(11,11,NULL,1,NULL,'2021-09-15 17:09:17','2021-09-15 17:09:17',0),(12,12,NULL,1,NULL,'2021-09-15 17:10:32','2021-09-15 17:10:32',0),(13,13,NULL,1,NULL,'2021-09-15 18:23:41','2021-09-15 18:23:41',0),(14,14,NULL,1,NULL,'2021-09-15 18:24:18','2021-09-15 18:24:18',0),(15,15,NULL,1,NULL,'2021-09-15 18:25:14','2021-09-15 18:25:14',0),(16,16,NULL,1,NULL,'2021-09-15 18:27:52','2021-09-15 18:27:52',0),(17,17,NULL,1,NULL,'2021-09-15 18:30:22','2021-09-15 18:30:22',0),(18,18,NULL,1,NULL,'2021-09-15 18:31:49','2021-09-15 18:31:49',0),(19,19,NULL,1,NULL,'2021-09-15 18:36:05','2021-09-15 18:36:05',0),(20,20,NULL,1,NULL,'2021-09-15 18:38:09','2021-09-15 18:38:09',0),(21,21,NULL,1,NULL,'2021-09-15 18:39:56','2021-09-15 18:39:56',0),(22,22,NULL,1,NULL,'2021-09-15 18:41:35','2021-09-15 18:41:35',0),(23,23,NULL,1,NULL,'2021-09-15 18:44:45','2021-09-15 18:44:45',0),(24,24,NULL,1,NULL,'2021-09-15 18:45:42','2021-09-15 18:45:42',0),(25,25,NULL,1,NULL,'2021-09-15 18:51:35','2021-09-15 18:51:35',0),(26,26,NULL,1,NULL,'2021-09-15 18:55:58','2021-09-15 18:55:58',0),(27,27,NULL,1,NULL,'2021-09-15 18:57:56','2021-09-15 18:57:56',0),(28,28,NULL,1,NULL,'2021-09-15 19:42:42','2021-09-15 19:42:42',0),(29,29,NULL,1,NULL,'2021-09-15 19:44:42','2021-09-15 19:44:42',0),(30,30,NULL,1,NULL,'2021-09-15 20:11:28','2021-09-15 20:11:28',0),(31,31,NULL,1,NULL,'2021-09-15 20:15:06','2021-09-15 20:15:06',0),(32,32,NULL,1,NULL,'2021-09-15 20:16:52','2021-09-15 20:16:52',0),(33,33,NULL,1,NULL,'2021-09-15 20:18:37','2021-09-15 20:18:37',0),(34,31,NULL,2,NULL,'2021-09-15 21:16:27','2021-09-15 21:16:27',0),(35,31,NULL,2,NULL,'2021-09-15 21:16:29','2021-09-15 21:16:29',0),(36,32,NULL,2,NULL,'2021-09-15 21:17:05','2021-09-15 21:17:05',0),(37,33,NULL,2,NULL,'2021-09-15 21:17:19','2021-09-15 21:17:19',0),(38,34,NULL,1,NULL,'2021-09-16 07:30:24','2021-09-16 07:30:24',0),(39,34,NULL,2,NULL,'2021-09-16 07:38:20','2021-09-16 07:38:20',0),(40,20,NULL,2,NULL,'2021-09-16 08:53:52','2021-09-16 08:53:52',0),(41,35,NULL,1,NULL,'2021-09-16 20:06:47','2021-09-16 20:06:47',0),(42,36,NULL,1,NULL,'2021-09-16 20:08:00','2021-09-16 20:08:00',0),(43,36,NULL,2,NULL,'2021-09-16 20:08:13','2021-09-16 20:08:13',0),(44,37,NULL,1,NULL,'2021-09-18 15:18:50','2021-09-18 15:18:50',0),(45,37,NULL,2,NULL,'2021-09-18 15:19:19','2021-09-18 15:19:19',0),(46,38,NULL,1,NULL,'2021-09-19 16:46:03','2021-09-19 16:46:03',0),(47,38,NULL,2,NULL,'2021-09-19 16:46:18','2021-09-19 16:46:18',0),(48,39,NULL,1,NULL,'2021-09-27 17:17:59','2021-09-27 17:17:59',0),(49,39,NULL,2,NULL,'2021-09-27 17:18:12','2021-09-27 17:18:12',0),(50,40,NULL,1,NULL,'2021-09-28 07:21:33','2021-09-28 07:21:33',0),(51,41,NULL,1,NULL,'2021-09-28 08:17:39','2021-09-28 08:17:39',0),(52,42,NULL,1,NULL,'2021-09-28 08:18:59','2021-09-28 08:18:59',0),(53,42,NULL,2,NULL,'2021-09-28 08:19:13','2021-09-28 08:19:13',0),(54,43,NULL,1,NULL,'2021-09-29 05:33:05','2021-09-29 05:33:05',0),(55,44,NULL,1,NULL,'2021-09-29 09:31:27','2021-09-29 09:31:27',0),(56,45,NULL,1,NULL,'2021-09-29 23:40:59','2021-09-29 23:40:59',0),(57,46,NULL,1,NULL,'2021-09-29 23:41:22','2021-09-29 23:41:22',0),(58,47,NULL,1,NULL,'2021-09-29 23:43:19','2021-09-29 23:43:19',0),(59,48,NULL,1,NULL,'2021-10-12 07:39:20','2021-10-12 07:39:20',0),(60,49,NULL,1,NULL,'2021-10-13 01:51:16','2021-10-13 01:51:16',0),(61,50,NULL,1,NULL,'2021-10-19 04:56:43','2021-10-19 04:56:43',0),(62,51,NULL,1,NULL,'2021-10-19 05:42:26','2021-10-19 05:42:26',0),(63,52,NULL,1,NULL,'2021-10-20 07:37:17','2021-10-20 07:37:17',0),(64,53,NULL,1,NULL,'2021-10-20 07:42:36','2021-10-20 07:42:36',0),(65,54,NULL,1,NULL,'2021-10-21 03:01:48','2021-10-21 03:01:48',0),(66,55,NULL,1,NULL,'2021-10-21 03:38:41','2021-10-21 03:38:41',0),(67,56,NULL,1,NULL,'2021-11-12 08:28:20','2021-11-12 08:28:20',0),(68,57,NULL,1,NULL,'2021-11-12 09:20:35','2021-11-12 09:20:35',0),(69,58,NULL,1,NULL,'2021-11-12 09:22:05','2021-11-12 09:22:05',0),(70,59,NULL,1,NULL,'2021-11-12 12:11:24','2021-11-12 12:11:24',0),(71,60,NULL,1,NULL,'2021-11-15 09:15:42','2021-11-15 09:15:42',0),(72,61,NULL,1,NULL,'2021-11-19 07:26:29','2021-11-19 07:26:29',0),(73,62,NULL,1,NULL,'2021-11-19 07:28:54','2021-11-19 07:28:54',0),(74,63,NULL,1,NULL,'2021-11-19 07:39:28','2021-11-19 07:39:28',0),(75,64,NULL,1,NULL,'2021-11-19 07:41:05','2021-11-19 07:41:05',0),(76,65,NULL,1,NULL,'2021-11-19 08:26:38','2021-11-19 08:26:38',0),(77,66,NULL,1,NULL,'2021-11-19 08:30:49','2021-11-19 08:30:49',0),(78,67,NULL,1,NULL,'2021-11-19 10:21:30','2021-11-19 10:21:30',0),(79,68,NULL,1,NULL,'2021-11-19 14:59:36','2021-11-19 14:59:36',0),(80,69,NULL,1,NULL,'2021-11-19 14:59:37','2021-11-19 14:59:37',0),(81,70,NULL,1,NULL,'2021-11-19 14:59:37','2021-11-19 14:59:37',0),(82,71,NULL,1,NULL,'2021-11-19 16:34:57','2021-11-19 16:34:57',0),(83,72,NULL,1,NULL,'2021-11-20 01:58:25','2021-11-20 01:58:25',0),(84,73,NULL,1,NULL,'2021-11-20 02:24:59','2021-11-20 02:24:59',0),(85,74,NULL,1,NULL,'2021-11-20 02:35:15','2021-11-20 02:35:15',0),(86,75,NULL,1,NULL,'2021-11-20 04:58:09','2021-11-20 04:58:09',0),(87,76,NULL,1,NULL,'2021-11-20 05:02:46','2021-11-20 05:02:46',0),(88,77,NULL,1,NULL,'2021-11-20 06:46:45','2021-11-20 06:46:45',0),(89,78,NULL,1,NULL,'2021-11-20 07:55:31','2021-11-20 07:55:31',0),(90,79,NULL,1,NULL,'2021-11-20 08:54:05','2021-11-20 08:54:05',0),(91,80,NULL,1,NULL,'2021-11-20 09:32:21','2021-11-20 09:32:21',0),(92,81,NULL,1,NULL,'2021-11-20 09:33:28','2021-11-20 09:33:28',0),(93,82,NULL,1,NULL,'2021-11-20 13:45:43','2021-11-20 13:45:43',0),(94,83,NULL,1,NULL,'2021-11-20 13:59:58','2021-11-20 13:59:58',0),(95,84,NULL,1,NULL,'2021-11-22 00:53:36','2021-11-22 00:53:36',0),(96,85,NULL,1,NULL,'2021-11-22 03:56:22','2021-11-22 03:56:22',0),(97,86,NULL,1,NULL,'2021-11-22 06:21:16','2021-11-22 06:21:16',0),(98,87,NULL,1,NULL,'2021-11-22 06:37:03','2021-11-22 06:37:03',0),(99,88,NULL,1,NULL,'2021-11-22 11:07:35','2021-11-22 11:07:35',0),(100,89,NULL,1,NULL,'2021-11-22 11:08:22','2021-11-22 11:08:22',0),(101,90,NULL,1,NULL,'2021-11-22 11:21:46','2021-11-22 11:21:46',0),(102,91,NULL,1,NULL,'2021-11-23 01:11:47','2021-11-23 01:11:47',0),(103,92,NULL,1,NULL,'2021-11-23 01:15:47','2021-11-23 01:15:47',0),(104,93,NULL,1,NULL,'2021-11-23 01:17:31','2021-11-23 01:17:31',0),(105,94,NULL,1,NULL,'2021-11-23 01:18:21','2021-11-23 01:18:21',0),(106,95,NULL,1,NULL,'2021-11-23 01:19:39','2021-11-23 01:19:39',0),(107,96,NULL,1,NULL,'2021-11-23 01:23:14','2021-11-23 01:23:14',0),(108,97,NULL,1,NULL,'2021-11-23 01:33:25','2021-11-23 01:33:25',0),(109,98,NULL,1,NULL,'2021-11-23 01:49:42','2021-11-23 01:49:42',0),(110,99,NULL,1,NULL,'2021-11-23 01:55:31','2021-11-23 01:55:31',0),(111,100,NULL,1,NULL,'2021-11-23 01:57:31','2021-11-23 01:57:31',0),(112,101,NULL,1,NULL,'2021-11-23 01:59:38','2021-11-23 01:59:38',0),(113,102,NULL,1,NULL,'2021-11-23 02:00:09','2021-11-23 02:00:09',0),(114,103,NULL,1,NULL,'2021-11-23 02:00:10','2021-11-23 02:00:10',0),(115,104,NULL,1,NULL,'2021-11-23 02:05:51','2021-11-23 02:05:51',0),(116,105,NULL,1,NULL,'2021-11-23 02:13:26','2021-11-23 02:13:26',0),(117,106,NULL,1,NULL,'2021-11-23 02:13:51','2021-11-23 02:13:51',0),(118,107,NULL,1,NULL,'2021-11-23 02:14:59','2021-11-23 02:14:59',0),(119,108,NULL,1,NULL,'2021-11-23 02:15:47','2021-11-23 02:15:47',0),(120,109,NULL,1,NULL,'2021-11-23 02:26:16','2021-11-23 02:26:16',0),(121,110,NULL,1,NULL,'2021-11-23 02:30:24','2021-11-23 02:30:24',0),(122,111,NULL,1,NULL,'2021-11-23 02:32:51','2021-11-23 02:32:51',0),(123,112,NULL,1,NULL,'2021-11-23 07:01:12','2021-11-23 07:01:12',0),(124,113,NULL,1,NULL,'2021-11-23 07:01:46','2021-11-23 07:01:46',0),(125,114,NULL,1,NULL,'2021-11-23 07:02:44','2021-11-23 07:02:44',0),(126,115,NULL,1,NULL,'2021-11-23 07:15:25','2021-11-23 07:15:25',0),(127,116,NULL,1,NULL,'2021-11-23 07:18:28','2021-11-23 07:18:28',0),(128,117,NULL,1,NULL,'2021-11-23 07:18:45','2021-11-23 07:18:45',0),(129,118,NULL,1,NULL,'2021-11-23 07:20:25','2021-11-23 07:20:25',0),(130,119,NULL,1,NULL,'2021-11-23 07:38:28','2021-11-23 07:38:28',0),(131,120,NULL,1,NULL,'2021-11-23 07:48:15','2021-11-23 07:48:15',0),(132,121,NULL,1,NULL,'2021-11-23 07:48:31','2021-11-23 07:48:31',0),(133,122,NULL,1,NULL,'2021-11-23 07:50:48','2021-11-23 07:50:48',0),(134,123,NULL,1,NULL,'2021-11-23 08:02:51','2021-11-23 08:02:51',0),(135,124,NULL,1,NULL,'2021-11-23 08:45:21','2021-11-23 08:45:21',0),(136,125,NULL,1,NULL,'2021-11-23 08:47:22','2021-11-23 08:47:22',0),(137,126,NULL,1,NULL,'2021-11-23 12:12:22','2021-11-23 12:12:22',0),(138,127,NULL,1,NULL,'2021-11-24 00:43:29','2021-11-24 00:43:29',0),(139,128,NULL,1,NULL,'2021-11-24 00:47:21','2021-11-24 00:47:21',0),(140,129,NULL,1,NULL,'2021-11-24 00:48:26','2021-11-24 00:48:26',0),(141,130,NULL,1,NULL,'2021-11-24 00:48:50','2021-11-24 00:48:50',0),(142,131,NULL,1,NULL,'2021-11-24 00:57:07','2021-11-24 00:57:07',0),(143,132,NULL,1,NULL,'2021-11-24 01:01:43','2021-11-24 01:01:43',0),(144,133,NULL,1,NULL,'2021-11-24 01:03:45','2021-11-24 01:03:45',0),(145,134,NULL,1,NULL,'2021-11-24 01:18:33','2021-11-24 01:18:33',0),(146,135,NULL,1,NULL,'2021-11-24 01:28:08','2021-11-24 01:28:08',0),(147,136,NULL,1,NULL,'2021-11-24 01:30:36','2021-11-24 01:30:36',0),(148,137,NULL,1,NULL,'2021-11-24 07:53:39','2021-11-24 07:53:39',0),(149,138,NULL,1,NULL,'2021-11-24 08:13:03','2021-11-24 08:13:03',0),(150,139,NULL,1,NULL,'2021-11-24 08:28:35','2021-11-24 08:28:35',0),(151,140,NULL,1,NULL,'2021-11-24 08:31:13','2021-11-24 08:31:13',0),(152,141,NULL,1,NULL,'2021-11-24 08:42:12','2021-11-24 08:42:12',0),(153,142,NULL,1,NULL,'2021-11-24 08:53:00','2021-11-24 08:53:00',0),(154,143,NULL,1,NULL,'2021-11-24 08:54:37','2021-11-24 08:54:37',0),(155,144,NULL,1,NULL,'2021-11-24 08:55:26','2021-11-24 08:55:26',0),(156,145,NULL,1,NULL,'2021-11-24 09:44:52','2021-11-24 09:44:52',0),(157,146,NULL,1,NULL,'2021-11-24 10:00:16','2021-11-24 10:00:16',0),(158,147,NULL,1,NULL,'2021-11-25 01:23:08','2021-11-25 01:23:08',0),(159,148,NULL,1,NULL,'2021-11-25 02:23:50','2021-11-25 02:23:50',0),(160,149,NULL,1,NULL,'2021-11-25 02:33:49','2021-11-25 02:33:49',0),(161,150,NULL,1,NULL,'2021-11-25 02:34:44','2021-11-25 02:34:44',0),(162,151,NULL,1,NULL,'2021-11-25 02:36:41','2021-11-25 02:36:41',0),(163,152,NULL,1,NULL,'2021-11-25 02:37:21','2021-11-25 02:37:21',0),(164,153,NULL,1,NULL,'2021-11-25 02:39:19','2021-11-25 02:39:19',0),(165,154,NULL,1,NULL,'2021-11-25 02:48:13','2021-11-25 02:48:13',0),(166,155,NULL,1,NULL,'2021-11-25 02:48:56','2021-11-25 02:48:56',0),(167,156,NULL,1,NULL,'2021-11-25 02:51:05','2021-11-25 02:51:05',0),(168,157,NULL,1,NULL,'2021-11-25 02:51:23','2021-11-25 02:51:23',0),(169,158,NULL,1,NULL,'2021-11-25 02:55:53','2021-11-25 02:55:53',0),(170,159,NULL,1,NULL,'2021-11-25 02:56:26','2021-11-25 02:56:26',0),(171,160,NULL,1,NULL,'2021-11-25 03:00:53','2021-11-25 03:00:53',0),(172,161,NULL,1,NULL,'2021-11-26 01:25:31','2021-11-26 01:25:31',0),(173,162,NULL,1,NULL,'2021-11-26 03:28:41','2021-11-26 03:28:41',0),(174,163,NULL,1,NULL,'2021-11-26 03:49:56','2021-11-26 03:49:56',0),(175,164,NULL,1,NULL,'2023-03-18 11:05:06','2023-03-18 11:05:06',0),(176,165,NULL,1,NULL,'2023-03-18 11:35:19','2023-03-18 11:35:19',0);

/*Table structure for table `order_return_apply` */

DROP TABLE IF EXISTS `order_return_apply`;

CREATE TABLE `order_return_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `merchant_id` bigint DEFAULT NULL COMMENT '团长门店id',
  `sku_id` bigint DEFAULT NULL COMMENT '退货商品id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `name` varchar(64) DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(100) DEFAULT NULL COMMENT '退货人电话',
  `status` int DEFAULT NULL COMMENT '申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `sku_pic` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `sku_num` int DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(10,2) DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) DEFAULT NULL COMMENT '原因',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `proof_pics` varchar(1000) DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(100) DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) DEFAULT NULL COMMENT '收货备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='订单退货申请';

/*Data for the table `order_return_apply` */

/*Table structure for table `order_return_reason` */

DROP TABLE IF EXISTS `order_return_reason`;

CREATE TABLE `order_return_reason` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '退货类型',
  `sort` int DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '状态：0->不启用；1->启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='退货原因表';

/*Data for the table `order_return_reason` */

/*Table structure for table `order_set` */

DROP TABLE IF EXISTS `order_set`;

CREATE TABLE `order_set` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seckill_order_overtime` int DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int DEFAULT NULL COMMENT '自动完成交易时间，不能申请售后（天）',
  `profit_rate` decimal(10,2) DEFAULT NULL COMMENT '佣金分成比例',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='订单设置表';

/*Data for the table `order_set` */

insert  into `order_set`(`id`,`seckill_order_overtime`,`normal_order_overtime`,`confirm_overtime`,`finish_overtime`,`profit_rate`,`create_time`,`update_time`,`is_deleted`) values (1,1,1,10,10,'0.10','2021-06-09 11:55:54','2021-07-01 15:14:55',0);

/*Table structure for table `payment_info` */

DROP TABLE IF EXISTS `payment_info`;

CREATE TABLE `payment_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单号',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `payment_type` varchar(20) DEFAULT NULL COMMENT '支付类型（微信 支付宝）',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '交易编号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `subject` varchar(200) DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
  `callback_content` text COMMENT '回调信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3 COMMENT='支付信息表';

/*Data for the table `payment_info` */

insert  into `payment_info`(`id`,`order_no`,`order_id`,`user_id`,`payment_type`,`trade_no`,`total_amount`,`subject`,`payment_status`,`callback_time`,`callback_content`,`create_time`,`update_time`,`is_deleted`) values (89,'1682416124200','180',27,'2',NULL,'0.01','userID:27下订单','1',NULL,NULL,'2023-04-25 17:49:09','2023-04-25 17:49:09',0),(90,'1682416819890','181',31,'2',NULL,'0.01','userID:31下订单','1',NULL,NULL,'2023-04-25 18:00:36','2023-04-25 18:00:36',0),(91,'1682417748665','182',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-25 18:16:02','2023-04-25 18:16:01',0),(92,'1682422583069','183',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-25 19:36:46','2023-04-25 19:36:46',0),(93,'1682422852768','184',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-25 19:41:06','2023-04-25 19:41:05',0),(94,'1682423016938','185',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 19:44:22','{transaction_id=4200001881202304257097036595, nonce_str=nD0qPvGj1lsO4b2W, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=0230A6A6877A76A52EB5E6139602B649, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682423016938, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425194413, is_subscribe=N, return_code=SUCCESS}','2023-04-25 19:43:49','2023-04-25 19:44:21',0),(95,'1682423276196','186',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 19:49:22','{transaction_id=4200001851202304257186767204, nonce_str=NS8Qtep3ju1qfmEV, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=8A0BF7772AE1055ACA5E17711420C9CA, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682423276196, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425194914, is_subscribe=N, return_code=SUCCESS}','2023-04-25 19:48:55','2023-04-25 19:49:22',0),(96,'1682423709092','187',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 19:55:55','{transaction_id=4200001796202304250736065730, nonce_str=qbd1Y8ofURycjH36, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=1DE6C7F26EDD158CCA6EA68FEA53712E, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682423709092, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425195547, is_subscribe=N, return_code=SUCCESS}','2023-04-25 19:55:25','2023-04-25 19:55:55',0),(97,'1682423947261','188',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 19:59:48','{transaction_id=4200001796202304258886602284, nonce_str=OXZ04i6i2XWQhNgG, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=D1EAF1D92AB5620BD600875B1712B66F, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682423947261, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425195940, is_subscribe=N, return_code=SUCCESS}','2023-04-25 19:59:24','2023-04-25 19:59:47',0),(98,'1682424457246','189',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 20:08:18','{transaction_id=4200001858202304257530011174, nonce_str=UecgohgDoUGXErHG, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=AD53D9412AC0252A883E72DB15035DC3, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682424457246, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425200811, is_subscribe=N, return_code=SUCCESS}','2023-04-25 20:07:58','2023-04-25 20:08:18',0),(99,'1682425039734','190',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 20:17:57','{transaction_id=4200001865202304253668575975, nonce_str=ueCqWTiwSkxHCHPK, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=5CB3BE4E69934DDC59C91A0E66BE2F53, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682425039734, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425201749, is_subscribe=N, return_code=SUCCESS}','2023-04-25 20:17:37','2023-04-25 20:17:57',0),(100,'1682425829654','191',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 20:31:04','{transaction_id=4200001881202304250553840897, nonce_str=PTkeJgkm7mlHFKtH, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=DE574D06B0EEBF5971DA1D21A8789915, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682425829654, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425203058, is_subscribe=N, return_code=SUCCESS}','2023-04-25 20:30:44','2023-04-25 20:31:04',0),(101,'1682426019007','192',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-25 20:33:52','2023-04-25 20:33:51',0),(102,'1682426174095','193',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-25 20:36:27','2023-04-25 20:36:27',0),(103,'1682426422674','194',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 20:40:55','{transaction_id=4200001855202304254583711999, nonce_str=eU2h27FllgVfoR8i, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=A68F42553279D663B56F2373F8C963E4, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682426422674, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425204044, is_subscribe=N, return_code=SUCCESS}','2023-04-25 20:40:31','2023-04-25 20:40:54',0),(104,'1682426743216','195',36,'2',NULL,'0.01','userID:36下订单','2','2023-04-25 20:46:22','{transaction_id=4200001801202304251699476411, nonce_str=kULXCsc7NEh7mCza, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=6A581EF57067EB838353215A009F51C0, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682426743216, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425204618, is_subscribe=N, return_code=SUCCESS}','2023-04-25 20:46:05','2023-04-25 20:46:22',0),(105,'1682429647121','196',36,'2','4200001791202304252109512929','0.01','userID:36下订单','2',NULL,'{transaction_id=4200001791202304252109512929, nonce_str=OFJ8jGo86NFspFc8, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=A887252E42D90C8412BB2932B7DC26CD, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682429647121, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425213525, is_subscribe=N, return_code=SUCCESS}','2023-04-25 21:35:03','2023-04-25 21:35:03',0),(106,'1682435683147','197',36,'2','4200001804202304256082851609','0.01','userID:36下订单','2',NULL,'{transaction_id=4200001804202304256082851609, nonce_str=e8G4kTG9AAGQQvUC, trade_state=SUCCESS, bank_type=OTHERS, openid=odo3j4i6KdS4jVu5667WGokoSrAQ, sign=F31980B22664DD18E6B6B570632938D0, return_msg=OK, fee_type=CNY, mch_id=1481962542, cash_fee=1, out_trade_no=1682435683147, cash_fee_type=CNY, appid=wxcc651fcbab275e33, total_fee=1, trade_state_desc=支付成功, trade_type=JSAPI, result_code=SUCCESS, attach=, time_end=20230425231655, is_subscribe=N, return_code=SUCCESS}','2023-04-25 23:16:13','2023-04-25 23:16:13',0),(107,'1682573952476','198',36,'2',NULL,'0.01','userID:36下订单','1',NULL,NULL,'2023-04-27 13:39:47','2023-04-27 13:39:47',0);

/*Table structure for table `refund_info` */

DROP TABLE IF EXISTS `refund_info`;

CREATE TABLE `refund_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '对外业务编号',
  `order_id` bigint DEFAULT NULL COMMENT '订单编号',
  `sku_id` bigint DEFAULT NULL,
  `payment_type` varchar(20) DEFAULT NULL COMMENT '支付类型（微信 支付宝）',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '交易编号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `subject` varchar(200) DEFAULT NULL COMMENT '交易内容',
  `refund_status` varchar(30) DEFAULT NULL COMMENT '退款状态',
  `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
  `callback_content` text COMMENT '回调信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_out_trade_no` (`out_trade_no`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='退款信息表';

/*Data for the table `refund_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
