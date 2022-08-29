/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.52
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 192.168.0.52:3306
 Source Schema         : hry_boot_zhudw

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 24/07/2020 11:29:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_product
-- ----------------------------
DROP TABLE IF EXISTS `app_product`;
CREATE TABLE `app_product` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                             `classId` bigint(20) DEFAULT NULL COMMENT '分类ID',
                             `customerType` tinyint(1) DEFAULT NULL COMMENT '客户类型1个人2企业',
                             `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
                             `loanRate` decimal(20,5) DEFAULT '0.00000' COMMENT '贷款利率',
                             `loanPeriod` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '贷款期限',
                             `costMark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '费用说明',
                             `outLoanPeriod` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '放款周期',
                             `created` datetime DEFAULT NULL,
                             `modified` datetime DEFAULT NULL,
                             `returnMoneyMark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '还款说明',
                             `productTrait` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '产品特点',
                             `adviserContent` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '顾问点评',
                             `productMark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '产品简介',
                             `orderNum` int(2) DEFAULT '0' COMMENT '排序',
                             `isEnable` tinyint(1) DEFAULT '0' COMMENT '是否启用0未启用1启用',
                             `productLogo` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标上传',
                             `productShow` text COLLATE utf8_bin COMMENT '产品介绍',
                             `productApproval` text COLLATE utf8_bin COMMENT '审批流程',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用流程表';

-- ----------------------------
-- Table structure for app_product_class
-- ----------------------------
DROP TABLE IF EXISTS `app_product_class`;
CREATE TABLE `app_product_class` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                   `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '流程名称',
                                   `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
                                   `created` datetime DEFAULT NULL,
                                   `modified` datetime DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用流程表';

SET FOREIGN_KEY_CHECKS = 1;


-- 增加贷款金额
alter table app_product add column loanMoney decimal(30,10) default 0 comment "贷款金额";
