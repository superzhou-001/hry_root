alter table app_product modify column loanMoney varchar(255);
alter table app_product modify column loanRate varchar(255);
alter table pro_define_document add `viewModel` tinyint(1) DEFAULT 1 COMMENT '显示状态';
