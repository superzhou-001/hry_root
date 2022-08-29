/*==============================================================*/
/* Table: `app_holidays_year` 节假日年份	                  		*/
/*==============================================================*/
DROP TABLE IF EXISTS `app_holidays_year`;
CREATE TABLE `app_holidays_year` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `year` varchar(20) DEFAULT NULL COMMENT '年份',
	`status` int(1) DEFAULT '0' COMMENT '是否启用 0 启用 1 未启用',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COLLATE=utf8_bin COMMENT='节假日年份';

/*==============================================================*/
/* Table: `app_holidays_info` 节假日明细	                  		*/
/*==============================================================*/
DROP TABLE IF EXISTS `app_holidays_info`;
CREATE TABLE `app_holidays_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`yearId` bigint(20) DEFAULT NULL COMMENT '年份Id',
  `year` varchar(20) DEFAULT NULL COMMENT '年份',
	`dateName` varchar(255) DEFAULT NULL COMMENT '日期名称',
  `dateType` int(1) DEFAULT '1' COMMENT '日期类型 1 节假日 2 工作日',
	`holidayDate` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '日期',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COLLATE=utf8_bin COMMENT='节假日明细';