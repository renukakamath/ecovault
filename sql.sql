/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - echovolt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`echovolt` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `echovolt`;

/*Table structure for table `assign` */

DROP TABLE IF EXISTS `assign`;

CREATE TABLE `assign` (
  `assing_id` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryboy_id` int(11) DEFAULT NULL,
  `request_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`assing_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `assign` */

insert  into `assign`(`assing_id`,`deliveryboy_id`,`request_id`,`date`,`status`) values 
(2,1,1,'2024-10-26','assign');

/*Table structure for table `deliveryboy` */

DROP TABLE IF EXISTS `deliveryboy`;

CREATE TABLE `deliveryboy` (
  `deliveryboy_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(100) DEFAULT NULL,
  `dname` varchar(100) DEFAULT NULL,
  `dplace` varchar(100) DEFAULT NULL,
  `dphone` varchar(100) DEFAULT NULL,
  `demail` varchar(100) DEFAULT NULL,
  `dlatitude` varchar(100) DEFAULT NULL,
  `dlongitude` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`deliveryboy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `deliveryboy` */

insert  into `deliveryboy`(`deliveryboy_id`,`login_id`,`dname`,`dplace`,`dphone`,`demail`,`dlatitude`,`dlongitude`,`status`) values 
(1,'3','sreenath','kaloor','8137924202','sreenath@gmail.com','9.97189384778182','76.32408142089844','not available');

/*Table structure for table `factory` */

DROP TABLE IF EXISTS `factory`;

CREATE TABLE `factory` (
  `factory_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `fplace` varchar(100) DEFAULT NULL,
  `fphone` varchar(100) DEFAULT NULL,
  `femail` varchar(100) DEFAULT NULL,
  `flatitude` varchar(100) DEFAULT NULL,
  `flongitude` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`factory_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `factory` */

insert  into `factory`(`factory_id`,`login_id`,`fname`,`fplace`,`fphone`,`femail`,`flatitude`,`flongitude`,`image`) values 
(1,4,'factory','aluva','9495795394','bih@gmail.com','10.103001503949644','76.35377052747407','static/image/005bb0a2-bc4f-4200-b9dd-6096fd896ad0download.jfif');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `usertype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'renuka','renuka','user'),
(3,'sreenath','sreenath','deliveryboy'),
(4,'factory','factory','factory');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment` int(11) NOT NULL AUTO_INCREMENT,
  `assing_id` int(11) DEFAULT NULL,
  `factory_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `total_distance` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`login_id`,`type_id`,`quantity`,`status`) values 
(1,2,1,'20','assign');

/*Table structure for table `type` */

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `factory_id` int(11) DEFAULT NULL,
  `types` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `type` */

insert  into `type`(`type_id`,`factory_id`,`types`,`image`) values 
(1,1,'plastic  waste','static/image/e1c5a56a-58d9-43a2-9da2-b0442c11ebbfth (1).jpeg'),
(2,1,'bio waste','static/image/eb612f21-e2e8-4800-beda-a29e75fa0ca5th.jpeg'),
(3,1,'human waste','static/image/a837b382-6298-45f3-826b-d03a24df90c4th (2).jpeg');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `uname` varchar(100) DEFAULT NULL,
  `uplace` varchar(100) DEFAULT NULL,
  `uphone` varchar(100) DEFAULT NULL,
  `uemail` varchar(100) DEFAULT NULL,
  `ulatitude` varchar(100) DEFAULT NULL,
  `ulongitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`uname`,`uplace`,`uphone`,`uemail`,`ulatitude`,`ulongitude`) values 
(1,2,'renuka','karanakodam','9495795304','renukakamath2@gmail.com','9.9868554','76.3040932');

/*Table structure for table `vehicle` */

DROP TABLE IF EXISTS `vehicle`;

CREATE TABLE `vehicle` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryboy_id` int(11) DEFAULT NULL,
  `vehicletype` varchar(100) DEFAULT NULL,
  `capacity` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `vehicle` */

insert  into `vehicle`(`vehicle_id`,`deliveryboy_id`,`vehicletype`,`capacity`,`image`) values 
(1,1,'luna ','50','static/image/e98aa3b6-7eae-4758-a600-a7eaa44e2ebcabc.jpg'),
(2,1,'auto','200','static/image/fe30ce60-c646-472f-b085-462ec570561babc.jpg'),
(3,1,'lorry','600','static/image/21ddde6e-9da0-4098-b240-bb1d746ae590abc.jpg'),
(4,1,'truck','1200','static/image/70791bc1-6033-4b4c-b2ba-55d4e6e76689abc.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
