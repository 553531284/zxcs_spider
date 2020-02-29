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

 Date: 01/03/2020 01:00:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

-- ----------------------------
-- Records of novel_type
-- ----------------------------
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

SET FOREIGN_KEY_CHECKS = 1;
