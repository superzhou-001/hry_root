ALTER TABLE scm_redeem_detail ADD COLUMN  `number` varchar(20) DEFAULT NULL COMMENT '区列行组成唯一标识';
ALTER table scm_redeem_record add COLUMN `redeemDate` datetime DEFAULT NULL COMMENT '解除质押日期';