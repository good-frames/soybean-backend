/*
 Navicat Premium Dump SQL

 Source Server         : soybean-mysql
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : soybean

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 09/01/2026 16:50:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 用户表
-- ----------------------------
CREATE TABLE `sys_user` (
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `username` varchar(30) NOT NULL COMMENT '用户账号',
    `nickname` varchar(30) NOT NULL COMMENT '用户昵称',
    `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
    `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
    `gender` char(1) DEFAULT '0' COMMENT '用户性别（0女 1男 2未知）',
    `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
    `password` varchar(100) DEFAULT '' COMMENT '密码',
    `status` char(1) DEFAULT '0' COMMENT '帐号状态（0停用 1正常）',
    `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

-- ----------------------------
-- 角色表
-- ----------------------------
CREATE TABLE `sys_role` (
    `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(30) NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `status` char(1) NOT NULL COMMENT '角色状态（0停用 1正常）',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';

-- ----------------------------
-- 菜单表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
    `menu_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单）',
    `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单标题',
    `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单（route）名称',
    `route_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
    `keep_alive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否缓存（0不缓存 1缓存）',
    `constant` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否是静态菜单（0：权限菜单，1：静态菜单）',
    `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
    `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
    `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '菜单状态（0停用 1正常）',
    `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `icon_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'icon图标类型 1：远程图标；2：本地图标',
    `is_frame` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否为外链（0否 1是）',
    `i18n_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '国际化键',
    `href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外链地址',
    `hide_in_menu` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '菜单状态（0隐藏 1显示）',
    `active_menu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '高亮路由名称',
    `multi_tab` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否多页签',
    `order` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '显示顺序',
    `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 按钮（按钮级别权限）表
-- ----------------------------
CREATE TABLE sys_btn (
    btn_id bigint NOT NULL AUTO_INCREMENT COMMENT '按钮ID',
    menu_id bigint NOT NULL COMMENT '菜单ID',
    btn_code varchar(100) NOT NULL COMMENT '按钮标识',
    create_by varchar(64) DEFAULT '' COMMENT '创建者',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    update_by varchar(64) DEFAULT '' COMMENT '更新者',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    remark varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (btn_id),
    KEY idx_menu_id (menu_id),
    KEY idx_btn_code (btn_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='按钮权限表';

-- ----------------------------
-- 用户和角色关联表
-- ----------------------------
CREATE TABLE `sys_user_role` (
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- 角色和菜单关联表
-- ----------------------------
CREATE TABLE `sys_role_menu` (
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';

-- ----------------------------
-- 创建角色-按钮关联表
-- ----------------------------
CREATE TABLE `sys_role_btn` (
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `btn_id` bigint NOT NULL COMMENT '按钮ID',
    PRIMARY KEY (`role_id`, `btn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色按钮关联表';

-- ----------------------------
-- 创建默认菜单数据
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'C', '首页', 'home', '/home', '0', '0', 'layout.base$view.home', 0, '1', NULL, 'mdi:monitor-dashboard', '1', '0', 'route.home', NULL, '0', NULL, NULL, 0000000000, NULL, '', NULL, '', '2026-01-09 09:05:38', '');
INSERT INTO `sys_menu` VALUES (2, 'M', '系统管理', 'manage', '/manage', '0', '0', 'layout.base', 0, '1', 'manage', 'carbon:cloud-service-management', '1', '0', 'route.manage', NULL, '0', NULL, NULL, 0000000001, '', 'admin', '2025-12-22 15:02:37', '', '2026-01-08 11:33:03', '系统管理目录');
INSERT INTO `sys_menu` VALUES (3, 'C', '菜单管理', 'manage_menu', '/manage/menu', '0', '0', 'view.manage_menu', 2, '1', 'manage:menu:list', 'material-symbols:route', '1', '0', 'route.manage_menu', NULL, '0', NULL, NULL, 0000000001, NULL, '', NULL, '', '2026-01-08 11:56:10', '');
INSERT INTO `sys_menu` VALUES (4, 'C', '权限管理', 'manage_role', '/manage/role', '0', '0', 'view.manage_role', 2, '1', 'manage:role:list', 'carbon:user-role', '1', '0', 'route.manage_role', NULL, '0', NULL, NULL, 0000000002, NULL, '', NULL, '', '2026-01-08 13:25:24', '');
INSERT INTO `sys_menu` VALUES (5, 'C', '用户管理', 'manage_user', '/manage/user', '0', '0', 'view.manage_user', 2, '1', 'manage:user:list', 'ic:round-manage-accounts', '1', '0', 'route.manage_user', NULL, '0', NULL, NULL, 0000000003, NULL, '', NULL, '', '2026-01-08 11:56:47', '');
INSERT INTO `sys_menu` VALUES (6, 'C', '登录', 'login', '/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?', '0', '1', 'layout.blank$view.login', 0, '1', '', 'logo', '2', '0', 'route.login', '', '1', NULL, NULL, 0000000999, NULL, '', '2026-01-09 08:21:25', '', '2026-01-09 09:10:20', '');
INSERT INTO `sys_menu` VALUES (7, 'C', '内嵌浏览器窗口', 'iframe-page', '/iframe-page/:url', '0', '1', 'layout.base$view.iframe-page', 0, '1', '', 'chrome', '2', '0', 'route.iframe-page', '', '1', NULL, NULL, 0000000999, NULL, '', '2026-01-09 08:22:34', '', '2026-01-09 09:06:18', '');
INSERT INTO `sys_menu` VALUES (8, 'C', '403', '403', '/403', '0', '1', 'layout.blank$view.403', 0, '1', '', 'no-permission', '2', '0', 'route.403', '', '1', NULL, NULL, 0000009999, NULL, '', '2026-01-09 08:24:24', '', '2026-01-09 09:06:22', '');
INSERT INTO `sys_menu` VALUES (9, 'C', '404', '404', '/404', '0', '1', 'layout.blank$view.404', 0, '1', '', 'not-found', '2', '0', 'route.404', '', '1', NULL, NULL, 0000009999, NULL, '', '2026-01-09 08:25:15', '', '2026-01-09 09:06:24', '');
INSERT INTO `sys_menu` VALUES (10, 'C', '500', '500', '/500', '0', '1', 'layout.blank$view.500', 0, '1', '', 'service-error', '2', '0', 'route.500', '', '1', NULL, NULL, 0000009999, NULL, '', '2026-01-09 08:26:09', '', '2026-01-09 09:06:28', '');

-- ----------------------------
-- 默认超级管理员用户
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', 'admin@soybean.com', '15888888888', '1', '', '$2a$10$7JB720yubVSOfvVWbfXCOOxjTOQcQjmrJF1ZM4nAVccp/.rkMlDWy', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', NULL, '管理员', '0');

-- ----------------------------
-- 默认角色
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '0', '0', 'admin', sysdate(), 'admin', sysdate(), '超级管理员');

-- ----------------------------
-- 用户角色关联
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

-- ----------------------------
-- 角色菜单关联
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);

SET FOREIGN_KEY_CHECKS = 1;
