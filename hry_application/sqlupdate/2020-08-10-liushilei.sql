
alter table fa_factoring_flow modify column projectCode varchar(255);
alter table fa_factoring_flow add projectName varchar(255) default  '' null comment '项目名称';
alter table fa_factoring_flow add facilityFlowId bigint(20) default   null comment '授信Id';
alter table fa_factoring_flow add useFacility tinyint(1) default  1  null comment '是否占用授信 1占用 2不占用 ';
