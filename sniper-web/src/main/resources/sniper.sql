/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : sniper

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/10/2019 09:59:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` varchar(32) NOT NULL,
  `action` varchar(32) DEFAULT NULL,
  `ajax` int(11) DEFAULT NULL,
  `class_method` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `http_method` varchar(32) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `params` varchar(1024) DEFAULT NULL,
  `uri` varchar(100) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_resource
-- ----------------------------
DROP TABLE IF EXISTS `system_resource`;
CREATE TABLE `system_resource` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `permission` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `required` varchar(11) NOT NULL,
  `sort` int(11) NOT NULL,
  `create_person` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_resource
-- ----------------------------
BEGIN;
INSERT INTO `system_resource` VALUES ('081dd408a7cd42f7bd3681b77ba41172', '4ca408f9b63446a3afc86f8b6ef3a9af', '查询资源', 'system:resource:list', '1', '1', 1, 'admin', '2019-08-21 16:02:46', NULL, NULL);
INSERT INTO `system_resource` VALUES ('2803f1d6f6504eeab33dc9d9a7bc2111', '4ca408f9b63446a3afc86f8b6ef3a9af', '更新资源', 'system:resource:update', '1', '0', 3, 'admin', '2019-08-21 16:03:20', NULL, NULL);
INSERT INTO `system_resource` VALUES ('402882936d80f31b016d80f4ce030003', 'd16ab5231c8643579c47696f4dcbf36c', '查询日志', 'system:log:list', '1', '1', 1, 'admin', '2019-09-30 14:56:30', NULL, NULL);
INSERT INTO `system_resource` VALUES ('494c3505ff7d46ed8718758bd887bdd4', 'aee7cf2440d24506a2fb5420d4fb45cb', '角色管理', 'system:role:menu', '0', '0', 2, 'admin', '2019-08-21 15:54:40', NULL, NULL);
INSERT INTO `system_resource` VALUES ('4ca408f9b63446a3afc86f8b6ef3a9af', 'aee7cf2440d24506a2fb5420d4fb45cb', '资源管理', 'system:resource:menu', '0', '0', 3, 'admin', '2019-08-21 15:55:03', NULL, NULL);
INSERT INTO `system_resource` VALUES ('5f3e0988a6a34f75a4a598c0160ec632', '494c3505ff7d46ed8718758bd887bdd4', '添加角色', 'system:role:add', '1', '0', 2, 'admin', '2019-08-21 16:00:52', NULL, NULL);
INSERT INTO `system_resource` VALUES ('69c1660b02c24498a35d1a98c359bd29', '9448b8b20ec246429cca4efcb50e1e60', '更新用户', 'system:user:update', '1', '0', 2, 'admin', '2019-08-21 15:58:32', NULL, NULL);
INSERT INTO `system_resource` VALUES ('78eeeffd95bd4968b85ed9713c36d057', '494c3505ff7d46ed8718758bd887bdd4', '更新角色', 'system:role:update', '1', '0', 3, 'admin', '2019-08-21 16:01:07', NULL, NULL);
INSERT INTO `system_resource` VALUES ('9448b8b20ec246429cca4efcb50e1e60', 'aee7cf2440d24506a2fb5420d4fb45cb', '用户管理', 'system:user:menu', '0', '0', 1, 'admin', '2019-08-21 15:54:13', NULL, NULL);
INSERT INTO `system_resource` VALUES ('aee7cf2440d24506a2fb5420d4fb45cb', NULL, '系统管理', 'system:menu', '0', '0', 1, 'admin', NULL, NULL, NULL);
INSERT INTO `system_resource` VALUES ('b2ca4bc65c394b708552585a123eeeba', '9448b8b20ec246429cca4efcb50e1e60', '新增用户', 'system:user:add', '1', '0', 3, 'admin', '2019-08-21 15:57:46', NULL, NULL);
INSERT INTO `system_resource` VALUES ('b384fe2fc41444ee838d098292e86162', '4ca408f9b63446a3afc86f8b6ef3a9af', '删除资源', 'system:resource:delete', '1', '0', 4, 'admin', '2019-08-21 16:03:34', NULL, NULL);
INSERT INTO `system_resource` VALUES ('c56a1f739e464b29917fbdb327bd5d06', '9448b8b20ec246429cca4efcb50e1e60', '查询用户', 'system:user:list', '1', '1', 1, 'admin', '2019-08-21 15:57:16', NULL, NULL);
INSERT INTO `system_resource` VALUES ('d16ab5231c8643579c47696f4dcbf36c', 'aee7cf2440d24506a2fb5420d4fb45cb', '操作日志', 'system:log:menu', '0', '0', 4, 'admin', '2019-08-21 15:55:52', NULL, NULL);
INSERT INTO `system_resource` VALUES ('d1ca85d6fdec471ab3998b47f46ecd9e', '494c3505ff7d46ed8718758bd887bdd4', '查询角色', 'system:role:list', '1', '1', 1, 'admin', '2019-08-21 16:00:40', NULL, NULL);
INSERT INTO `system_resource` VALUES ('e65d3bdbefa54cdea4a4b9a7350df9ef', '4ca408f9b63446a3afc86f8b6ef3a9af', '添加资源', 'system:resource:add', '1', '0', 2, 'admin', '2019-08-21 16:03:02', NULL, NULL);
INSERT INTO `system_resource` VALUES ('ff8080816db9bbe5016db9ef74f80014', NULL, 'SQL监控', 'sql:menu', '0', '0', 2, 'admin', '2019-10-11 16:29:01', 'admin', '2019-10-11 16:30:30');
INSERT INTO `system_resource` VALUES ('ff8080816db9bbe5016db9f21e6e001f', 'ff8080816db9bbe5016db9ef74f80014', 'SQL监控', 'sql:monitor', '0', '0', 1, 'admin', '2019-10-11 16:31:55', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `create_person` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_person` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
BEGIN;
INSERT INTO `system_role` VALUES ('e1f4a0aecc0445298fbbe3077c1ba60c', '系统管理员', 'admin', '2019-08-21 16:06:20', NULL, NULL);
INSERT INTO `system_role` VALUES ('f9f618ab6d644d7995dfe13297b4acb6', 'test11', 'admin', '2019-08-21 16:06:44', 'admin', '2019-10-15 17:22:19');
COMMIT;

-- ----------------------------
-- Table structure for system_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `system_role_resource`;
CREATE TABLE `system_role_resource` (
  `id` varchar(32) NOT NULL,
  `create_person` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `system_role_resource` VALUES ('402882936d7ffea3016d800a1440000a', 'admin', '2019-09-30 10:40:07', 'b384fe2fc41444ee838d098292e86162', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('402882936d807917016d807c71180012', 'admin', '2019-09-30 12:45:02', 'e65d3bdbefa54cdea4a4b9a7350df9ef', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('402882936d80f31b016d80f57fb2000e', 'admin', '2019-09-30 14:57:16', '402882936d80f31b016d80f4ce030003', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d71641e016d7210a35c0115', 'admin', '2019-09-27 17:32:36', 'c56a1f739e464b29917fbdb327bd5d06', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7ad536016d7b039f370052', 'admin', '2019-09-29 11:14:58', '4ca408f9b63446a3afc86f8b6ef3a9af', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7ad536016d7b039f3a0053', 'admin', '2019-09-29 11:14:58', '081dd408a7cd42f7bd3681b77ba41172', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7ad536016d7b039f460057', 'admin', '2019-09-29 11:14:58', 'aee7cf2440d24506a2fb5420d4fb45cb', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7c172c016d7c186ae2000c', 'admin', '2019-09-29 16:17:18', '4ca408f9b63446a3afc86f8b6ef3a9af', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7c172c016d7c186ae6000d', 'admin', '2019-09-29 16:17:18', '081dd408a7cd42f7bd3681b77ba41172', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7c172c016d7c186ae9000e', 'admin', '2019-09-29 16:17:18', 'e65d3bdbefa54cdea4a4b9a7350df9ef', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7c172c016d7c186aee000f', 'admin', '2019-09-29 16:17:18', '2803f1d6f6504eeab33dc9d9a7bc2111', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856d7c172c016d7c198aa50019', 'admin', '2019-09-29 16:18:32', 'd16ab5231c8643579c47696f4dcbf36c', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('4028b2856db45269016db47b070f002b', 'admin', '2019-10-10 15:03:45', '2803f1d6f6504eeab33dc9d9a7bc2111', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856db45269016db47b0712002c', 'admin', '2019-10-10 15:03:45', 'b384fe2fc41444ee838d098292e86162', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856db45269016db47bb5d40036', 'admin', '2019-10-10 15:04:29', 'c56a1f739e464b29917fbdb327bd5d06', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856db45269016db47bb5e10037', 'admin', '2019-10-10 15:04:29', '9448b8b20ec246429cca4efcb50e1e60', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856dceb7dd016dceb95dba0016', 'admin', '2019-10-15 17:21:58', 'ff8080816db9bbe5016db9ef74f80014', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('4028b2856dceb7dd016dceb95dc00017', 'admin', '2019-10-15 17:21:58', 'ff8080816db9bbe5016db9f21e6e001f', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('799df1fee0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', '494c3505ff7d46ed8718758bd887bdd4', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79a762dee0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', '5f3e0988a6a34f75a4a598c0160ec632', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79aa99b8e0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', '69c1660b02c24498a35d1a98c359bd29', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79adb030e0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', '78eeeffd95bd4968b85ed9713c36d057', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79b12d46e0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', '9448b8b20ec246429cca4efcb50e1e60', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79b44dfae0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', 'aee7cf2440d24506a2fb5420d4fb45cb', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79b7cd36e0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', 'b2ca4bc65c394b708552585a123eeeba', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('79cadc50e0f911e9b5b9c6f784add939', 'admin', '2019-09-27 15:36:10', 'd1ca85d6fdec471ab3998b47f46ecd9e', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('ff8080816db8a2bc016db8a390ba0007', 'admin', '2019-10-11 10:26:30', 'd1ca85d6fdec471ab3998b47f46ecd9e', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('ff8080816db8a2bc016db8a390cf0008', 'admin', '2019-10-11 10:26:30', '494c3505ff7d46ed8718758bd887bdd4', 'f9f618ab6d644d7995dfe13297b4acb6');
INSERT INTO `system_role_resource` VALUES ('ff8080816db9bbe5016db9f045ad0019', 'admin', '2019-10-11 16:29:54', 'ff8080816db9bbe5016db9ef74f80014', 'e1f4a0aecc0445298fbbe3077c1ba60c');
INSERT INTO `system_role_resource` VALUES ('ff8080816db9bbe5016db9f305930026', 'admin', '2019-10-11 16:32:55', 'ff8080816db9bbe5016db9f21e6e001f', 'e1f4a0aecc0445298fbbe3077c1ba60c');
COMMIT;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `telephone` varchar(32) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_person` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_person` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_account_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
BEGIN;
INSERT INTO `system_user` VALUES ('4028b2856dceb7dd016dceb92541000d', 'test1', 'test1', '6c209ce35d3a9d26793a982e71e85d33', '2f522fdb41bc4d33a8ffed8172267fdc', '23134@1321.com', '2131', '1', '2019-10-15 17:21:43', 'admin', NULL, NULL);
INSERT INTO `system_user` VALUES ('44a176e67a0f42dd8349813d39728234', 'test', 'test', '4c2475911012db9beb363bf341e3e674', 'd4d3133ded3442f2a3f03d21b90e11e5', '111211@11.com', '222211', '1', '2019-08-21 16:28:01', 'admin', '2019-10-15 17:21:11', 'admin');
INSERT INTO `system_user` VALUES ('5cd533ab934f4cb690a4f98abdf4406f', 'admin', 'admin', '2c6ccadb42ab81d68a6214985f6ed26e', '6d59fbc09df648968d05369339ceea94', '123@123.com', '23131', '1', '2019-08-21 15:47:31', NULL, '2019-10-15 16:53:56', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` varchar(32) NOT NULL,
  `create_person` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
BEGIN;
INSERT INTO `system_user_role` VALUES ('4028b2856d4318c0016d433b16fb0028', 'admin', '2019-09-18 15:16:49', 'e1f4a0aecc0445298fbbe3077c1ba60c', '5cd533ab934f4cb690a4f98abdf4406f');
INSERT INTO `system_user_role` VALUES ('4028b2856d71641e016d71a9a1650074', 'admin', '2019-09-27 15:40:05', 'f9f618ab6d644d7995dfe13297b4acb6', '44a176e67a0f42dd8349813d39728234');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
