-- 为 sys_menu 表添加 i18n_key 字段
ALTER TABLE sys_menu ADD COLUMN i18n_key varchar(100) DEFAULT '' COMMENT '国际化键' AFTER title;
