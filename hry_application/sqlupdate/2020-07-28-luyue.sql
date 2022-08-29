alter table scm_redeem_record add COLUMN   `loanDays` int DEFAULT '0' COMMENT '借款天数';

DROP TABLE IF EXISTS `scm_increase_money`;
CREATE TABLE `scm_increase_money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(32) DEFAULT NULL COMMENT '补款编号',
  `projectId` bigint(20) DEFAULT NULL COMMENT '订单id',
  `projectNumber` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `money` decimal(20,2) DEFAULT '0.00' COMMENT '补款金额',
  `imageUrl` varchar(100) DEFAULT NULL COMMENT '补款凭证照片路径',
  `applyId` bigint(20) DEFAULT NULL COMMENT '申请账户id',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='补款记录表';