ALTER TABLE `myshop`.`user`
MODIFY COLUMN `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST,
MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名' AFTER `id`,
MODIFY COLUMN `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码' AFTER `name`,
ADD COLUMN `test` varchar(255) NULL COMMENT 'flyway测试' AFTER `password`;