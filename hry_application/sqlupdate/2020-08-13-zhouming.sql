
/*==============================================================*/
/* Table: `app_user_station` 用户信件表	                  */
/*==============================================================*/
DROP TABLE IF EXISTS `app_user_station`;
CREATE TABLE `app_user_station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `stationId` bigint(20) DEFAULT NULL COMMENT '信件ID',
  `readTime` datetime DEFAULT NULL COMMENT '阅读时间',
  `status` int(1) DEFAULT '0' COMMENT '状态 0 未阅读 1 已阅读 2 已删除',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信件表';

/*==============================================================*/
/* Table: `app_login_log` 用户登录日志	                  */
/*==============================================================*/
DROP TABLE IF EXISTS `app_login_log`;
CREATE TABLE `app_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`loginId` varchar(20) DEFAULT NULL COMMENT '登录唯一标识',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `onlineTime` varchar(255) DEFAULT NULL COMMENT '在线时长 单位毫秒',
  `loginTime` datetime DEFAULT NULL COMMENT '登录时间',
  `logoutTime` datetime DEFAULT NULL COMMENT '登出时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户登录日志';

alter table new_app_user add `headImage` varchar(255) DEFAULT NULL COMMENT '用户头像'
alter table new_app_user add `weChatAuthLogin` int(1) DEFAULT '0' COMMENT '是否授权微信登录 0 未授权 1 已授权'
alter table app_station_content add `userType` int(1) DEFAULT '0' COMMENT '用户类型 0 后台用户 1 前台用户'
alter table app_user_station add `userType` int(1) DEFAULT '0' COMMENT '用户类型 0 后台用户 1 前台用户'