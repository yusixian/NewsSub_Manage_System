
-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) COLLATE utf8_bin NOT NULL,
  `passWord` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES (1, '旅行者', '123');
INSERT INTO `admin` VALUES (2, '迪卢克', 'Diluc');
INSERT INTO `admin` VALUES (3, '莫娜', 'Mona');
INSERT INTO `admin` VALUES (4, '温迪', 'Venti');
INSERT INTO `admin` VALUES (5, '刻晴', 'Keqing');
COMMIT;


-- ----------------------------
-- Table structure for generalUser
-- ----------------------------
DROP TABLE IF EXISTS `generalUser`;
CREATE TABLE `generalUser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) COLLATE utf8_bin NOT NULL,
  `passWord` varchar(60) COLLATE utf8_bin NOT NULL,
  `realName` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `idCard` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),                   -- 主键 --
  KEY `id` (`id`)                       -- 普通索引 --
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COLLATE=utf8_bin; -- 区分大小写 --

-- ----------------------------
-- Records of generalUser
-- ----------------------------
BEGIN;
INSERT INTO `generalUser` VALUES (1, '芭芭拉', 'test', 'Barbara', '410102200107059004', '18302917670', 'Mond');
INSERT INTO `generalUser` VALUES (2, '菲谢尔', '123', 'Fischl', '110101200108073361', '14613842106', 'Mond');
INSERT INTO `generalUser` VALUES (3, '班尼特', '123', '六星真神', '', '', '');
INSERT INTO `generalUser` VALUES (4, '七七', 'Qiqi', '小僵尸', '', '', 'Liyue');
INSERT INTO `generalUser` VALUES (5, '凝光', 'Ningguang', '富婆', '', '', 'Liyue');
INSERT INTO `generalUser` VALUES (101, '砂糖', 'Sucrose', '风之子', '501238102', '3123', 'Mond');
INSERT INTO `generalUser` VALUES (102, '丽莎', '34546758', '该电一电了', '', '15403', 'Mond');
INSERT INTO `generalUser` VALUES (110, '凯亚', '123', '小心着凉', '', '', 'Mond');
INSERT INTO `generalUser` VALUES (112, '魈', '123', '提瓦特第一暴毙仙人', 'safaf', 'ffff', 'Liyue');
INSERT INTO `generalUser` VALUES (116, '香菱', '123', '开锅啦开锅啦', '', '', 'Liyue');
COMMIT;


-- ----------------------------
-- Table structure for magazine
-- ----------------------------
DROP TABLE IF EXISTS `magazine`;
CREATE TABLE `magazine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coverPath` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(60) COLLATE utf8_bin NOT NULL,
  `office` varchar(60) COLLATE utf8_bin NOT NULL,
  `cycle` varchar(60) COLLATE utf8_bin NOT NULL,
  `price` varchar(60) COLLATE utf8_bin NOT NULL,
  `intro` varchar(140) COLLATE utf8_bin NOT NULL,
  `classNumber` int(11) NOT NULL DEFAULT '2333',
  PRIMARY KEY (`id`),                       -- 主键 --
  KEY `classNumber` (`classNumber`)         -- 普通索引 --
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of magazine
-- ----------------------------
BEGIN;
INSERT INTO `magazine` VALUES (1, 'Reader.jpg', '读者', '甘肃人民出版社', '半月刊', '4', '《读者》杂志设置的主要栏目有文苑：卷首语、文苑、社会之窗、人物、杂谈随感、青年一代、人生之旅、人世间、在国外、风情录、知识窗、生活之友、心理人生、经营之道、趣闻轶事等等。', 1);
INSERT INTO `magazine` VALUES (2, 'YiLin.jpg.', '意林', '《意林》杂志社', '半月刊', '4', '《意林》的栏目大体上是按照生命、成功、生活、情感等方向来划分，设置了近百个不同栏目。栏目共分两级，每期有个一级栏目，包括励心小品、人与社会、生活锦囊、新知探索等', 1);
INSERT INTO `magazine` VALUES (3, 'Stories.jpg', '故事会', '上海世纪出版集团', '半月刊', '3', '《故事会》以发表反映中国当代社会生活的故事为主，同时兼收并蓄各类流传的民间故事和经典的外国故事。', 3);
INSERT INTO `magazine` VALUES (4, 'ChineseNationalGeography.jpg', '中国国家地理', '《中国国家地理》杂志社', '月刊', '26', '本刊内容以中国地理为主，兼具世界各地不同区域的自然、人文景观和事件，并揭示其背景和奥秘，另亦涉及天文、生物、历史和考古等领域。', 4);
INSERT INTO `magazine` VALUES (5, 'Comicguests.jpg', '知音漫客', '湖北知音传媒集团', '双月刊', '20', '我是二刺螈', 2);
INSERT INTO `magazine` VALUES (6, 'SaManHua.jpg', '飒漫画', 'xxx', '双月刊', '15', '呐呐呐', 2);
INSERT INTO `magazine` VALUES (7, 'test.jpg', '好漫画', 'xxx', '季刊', '100', 'ko~ko~da~yo~', 5);
COMMIT;

-- ----------------------------
-- Table structure for mClass
-- ----------------------------
DROP TABLE IF EXISTS `mClass`;
CREATE TABLE `mClass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2334 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of mClass
-- ----------------------------
BEGIN;
INSERT INTO `mClass` VALUES (1, '生活');
INSERT INTO `mClass` VALUES (2, '二次元');
INSERT INTO `mClass` VALUES (3, '故事');
INSERT INTO `mClass` VALUES (4, '地理');
INSERT INTO `mClass` VALUES (5, '未分类');
COMMIT;


-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `cycleNum` int(11) NOT NULL,
  `copiesNum` int(11) NOT NULL,
  `totalPrice` int(11) NOT NULL,
  PRIMARY KEY (`id`),           -- 主键 --
  KEY `uid` (`uid`),            -- 普通索引 --
  KEY `magazineToOrder` (`mid`),
  CONSTRAINT `magazineToOrder` FOREIGN KEY (`mid`) REFERENCES `magazine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  -- cascade 表示级联操作，就是说，如果主键表中被参考字段更新，外键表中也更新，主键表中的记录被删除，外键表中该行也相应删除 --
  CONSTRAINT `order_uid` FOREIGN KEY (`uid`) REFERENCES `generalUser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of order
-- ----------------------------
BEGIN;              -- id uid mid cycleNum copiesNum totalPrice --
INSERT INTO `order` VALUES (1, 1, 5, 1, 1, 4);      -- 订单1 编号为1的读者订阅了编号为5的杂志1个周期1份，总金额为4 --
INSERT INTO `order` VALUES (2, 1, 3, 4, 1, 12);     -- 订单2 编号为1的读者订阅了编号为3的杂志4个周期1份，总金额为12 --
INSERT INTO `order` VALUES (3, 2, 6, 2, 5, 150);    -- 订单3 编号为2的读者订阅了编号为6的杂志2个周期5份，总金额为150 --
INSERT INTO `order` VALUES (4, 3, 2, 6, 1, 24);     -- 订单4 编号为3的读者订阅了编号为2的杂志6个周期1份，总金额为24 --
INSERT INTO `order` VALUES (5, 5, 7, 1, 3, 300);     -- 订单5 编号为5的读者订阅了编号为7的杂志1个周期3份，总金额为300 --
INSERT INTO `order` VALUES (6, 101, 1, 2, 2, 16);     -- 订单6 编号为101的读者订阅了编号为1的杂志2个周期2份，总金额为16 --
COMMIT;

-- ----------------------------
-- View structure for magazinereview
-- 创建报刊视图，包括报刊名称、总金额、订单数
-- 这里因为m.name可能会为null,所以要coalesce，空的就视为总数
-- ----------------------------
DROP VIEW IF EXISTS `magazinereview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `magazinereview` AS
 select coalesce(m.name,'总数') AS magazineName, -- 如果m.name为Null,返回总数 --
        sum(o.totalPrice) AS totalPrice,
        count(0) AS orderNum            -- count(0)返回所有匹配条件的行数，并且忽略所有列（对行数无影响）
 from (`order` o join `magazine` m)
where (m.id = o.mid) group by m.name with rollup;

-- ----------------------------
-- View structure for semagazinereview
-- 创建查询报刊视图,不包括总数的！
-- ----------------------------
DROP VIEW IF EXISTS `semagazinereview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `semagazinereview` AS
select `magazinereview`.magazineName AS magazineName,
       `magazinereview`.totalPrice AS totalPrice,
       `magazinereview`.orderNum AS orderNum
from `magazinereview`
where (not(`magazinereview`.totalPrice in (select max(`magazinereview`.totalPrice) from `magazinereview`)));

-- ----------------------------
-- 对应查询语句:
-- select * from `semagazinereview` as t where t.totalPrice = (select max(totalPrice) from `semagazinereview` as t1) --
-- ----------------------------

-- ----------------------------
-- View structure for userreview
-- 创建用户视图，包括用户名、该用户的订单总金额、该用户订单数
-- ----------------------------
DROP VIEW IF EXISTS `userreview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `userreview` AS
select coalesce(u.userName,'总数') AS userName,
       sum(o.totalPrice) AS totalPrice,
       count(0) AS orderNum
from (`order` o join `generalUser` u)
where (u.id = o.uid) group by u.username with rollup;

-- ----------------------------
-- View structure for seuserriview
-- 创建查询用户视图,不包括总数的！
-- ----------------------------
DROP VIEW IF EXISTS `seuserreview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `seuserreview` AS
select `userreview`.userName AS userName,
       `userreview`.totalPrice AS totalPrice,
       `userreview`.orderNum AS orderNum
from `userreview`
where (not(userreview.totalPrice in (select max(`userreview`.totalPrice) from `userreview`)));
-- ----------------------------
-- 对应查询语句:
-- select * from `seuserreview` as t where t.totalPrice = (select max(totalPrice) from `seuserreview` as t1) --
-- ----------------------------


-- ----------------------------
-- View structure for classreview
-- 创建分类视图，包括分类名、该分类的订单总金额、该分类订单数
-- ----------------------------
DROP VIEW IF EXISTS `classreview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `classreview` AS
select coalesce(c.className,'总数') AS className,
       sum(o.totalPrice) AS totalPrice,
       count(0) AS orderNum
from ((`order` o join `magazine` m) join `mclass` c)
where ((m.id = o.mid) and (c.id = m.classNumber)) group by c.className with rollup;

DROP VIEW IF EXISTS `seclassreview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
SQL SECURITY DEFINER VIEW `seclassreview` AS
select `classreview`.className AS className,
       `classreview`.totalPrice AS totalPrice,
       `classreview`.orderNum AS orderNum
from `classreview`
where (not(classreview.totalPrice in (select max(`classreview`.totalPrice) from `classreview`)));

-- select * from `seclassreview` --