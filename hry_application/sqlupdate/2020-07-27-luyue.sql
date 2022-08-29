ALTER table scm_mortgage_project add column  `mortRate` decimal(10,6) DEFAULT NULL COMMENT '实际质押率';
alter table scm_mortgage_project add column  `actualEndDate` datetime DEFAULT NULL COMMENT '实际完结日期';