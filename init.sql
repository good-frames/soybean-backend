CREATE TABLE `admin_user` (
  `uid` bigint NOT NULL COMMENT '用户编号',
  `username` char(12) NOT NULL COMMENT '用户名',
  `password` char(255) NOT NULL COMMENT '用户登录密码',
  `nickname` char(30) DEFAULT NULL COMMENT '用户昵称',
  `phone` char(15) DEFAULT NULL COMMENT '用户手机号',
  `email` char(100) DEFAULT NULL COMMENT '用户邮箱',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '账号状态',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uk_uid` (`uid`)
) COMMENT='后台管理员表';