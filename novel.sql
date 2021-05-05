/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : novel

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 07/02/2020 22:42:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '外部id',
  `novel_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说名',
  `novel_author` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小说作者',
  `novel_detail` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小说详情',
  `novel_type` int(11) NULL DEFAULT NULL COMMENT '小说类型',
  `novel_size` double(6, 2) NULL DEFAULT NULL COMMENT '小说大小，单位mb',
  `file_size` double(6, 2) NULL DEFAULT NULL COMMENT '文件大小',
  `file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名，格式：小说名.作者',
  `file_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_out_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外部下载url',
  `file_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '获取状态 0 未获取 1 已获取',
  `like_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '获取状态 0 未获取 1 已获取',
  `zan` int(11) NULL DEFAULT NULL COMMENT '赞',
  `cai` int(11) NULL DEFAULT NULL COMMENT '踩',
  `creat_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `out_id`(`out_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for novel_type
-- ----------------------------
DROP TABLE IF EXISTS `novel_type`;
CREATE TABLE `novel_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zx_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


INSERT INTO `novel_type` VALUES (1, '23', '都市');
INSERT INTO `novel_type` VALUES (2, '36', '武侠');
INSERT INTO `novel_type` VALUES (3, '37', '仙侠');
INSERT INTO `novel_type` VALUES (4, '38', '奇幻');
INSERT INTO `novel_type` VALUES (5, '39', '玄幻');
INSERT INTO `novel_type` VALUES (6, '40', '科幻');
INSERT INTO `novel_type` VALUES (7, '41', '灵异');
INSERT INTO `novel_type` VALUES (8, '42', '历史');
INSERT INTO `novel_type` VALUES (9, '43', '军事');
INSERT INTO `novel_type` VALUES (10, '44', '竞技');
INSERT INTO `novel_type` VALUES (11, '45', '游戏');
INSERT INTO `novel_type` VALUES (12, '55', '二次元');

-- ----------------------------
-- Table structure for spider_error
-- ----------------------------
DROP TABLE IF EXISTS `spider_error`;
CREATE TABLE `spider_error`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `out_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '外部id',
  `fail_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '失败原因',
  `creat_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `out_id`(`out_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
