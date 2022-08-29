ALTER TABLE `fa_factoring_flow`
DROP COLUMN `projectId`;

ALTER TABLE `fa_factoring_cost`
DROP COLUMN `factoringId`;

ALTER TABLE `fa_factoring_rate`
DROP COLUMN `factoringId`;

ALTER TABLE `fa_factoring_bill`
DROP COLUMN `factoringId`;

ALTER TABLE `fa_factoring_contract`
CHANGE COLUMN `factoringId` `projectId`  bigint(20) NULL DEFAULT NULL COMMENT '项目id' AFTER `remark`;

ALTER TABLE `fa_factoring_flow`
ADD COLUMN `projectStatus`  tinyint(2) NULL DEFAULT 1 COMMENT '项目状态 1办理中，2办理完成  3结清  4已终止  5  已违约' AFTER `useFacility`,
ADD COLUMN `projectStatusDate`  datetime NULL DEFAULT NULL COMMENT '项目状态修改日期' AFTER `projectStatus`;

