/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost
 Source Database       : multimif

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 11/23/2016 11:19:12 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `branche`
-- ----------------------------
DROP TABLE IF EXISTS `branche`;
CREATE TABLE `branche` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projet_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1lu5i74ccsv9ia201gydulu5o` (`projet_id`),
  CONSTRAINT `FK1lu5i74ccsv9ia201gydulu5o` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `droit`
-- ----------------------------
DROP TABLE IF EXISTS `droit`;
CREATE TABLE `droit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `droit`
-- ----------------------------
BEGIN;
INSERT INTO `droit` VALUES ('1', 'compilation'), ('2', 'execution'), ('3', 'lecture'), ('4', 'ecriture'), ('5', 'manager'), ('6', 'administrateur'), ('7', 'exporter un projet'), ('9', 'compilation');
COMMIT;

-- ----------------------------
--  Table structure for `droit_projet`
-- ----------------------------
DROP TABLE IF EXISTS `droit_projet`;
CREATE TABLE `droit_projet` (
  `id_projet` int(11) NOT NULL,
  `id_droit` int(11) NOT NULL,
  KEY `FKm26i7ptjxgskelnsfuql5xd7o` (`id_droit`),
  KEY `FKrb3rw3cljb7xmqdcw8sep7p7` (`id_projet`),
  CONSTRAINT `FKm26i7ptjxgskelnsfuql5xd7o` FOREIGN KEY (`id_droit`) REFERENCES `droit` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKrb3rw3cljb7xmqdcw8sep7p7` FOREIGN KEY (`id_projet`) REFERENCES `projet_droit` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `droit_projet`
-- ----------------------------
BEGIN;
INSERT INTO `droit_projet` VALUES ('49', '3'), ('49', '4'), ('49', '5'), ('162', '1'), ('162', '2'), ('162', '3'), ('162', '4'), ('162', '5'), ('162', '6'), ('162', '7'), ('162', '9'), ('163', '1'), ('163', '2'), ('163', '3'), ('163', '4'), ('163', '5'), ('163', '6'), ('163', '7'), ('163', '9'), ('164', '1'), ('165', '1'), ('165', '2'), ('165', '3'), ('165', '4'), ('165', '5'), ('165', '6'), ('165', '7'), ('165', '9'), ('168', '1'), ('168', '2'), ('168', '3'), ('168', '4'), ('168', '5'), ('168', '6'), ('168', '7'), ('168', '9'), ('171', '1'), ('171', '2'), ('171', '3'), ('171', '4'), ('171', '5'), ('171', '6'), ('171', '7'), ('171', '9'), ('174', '1'), ('174', '2'), ('174', '3'), ('174', '4'), ('174', '5'), ('174', '6'), ('174', '7'), ('174', '9'), ('175', '1'), ('175', '2'), ('175', '3'), ('175', '4'), ('175', '5'), ('175', '6'), ('175', '7'), ('175', '9'), ('176', '1'), ('176', '2'), ('176', '3'), ('176', '4'), ('176', '5'), ('176', '6'), ('176', '7'), ('176', '9');
COMMIT;

-- ----------------------------
--  Table structure for `element_projet`
-- ----------------------------
DROP TABLE IF EXISTS `element_projet`;
CREATE TABLE `element_projet` (
  `DTYPE` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `main` bit(1) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `projet_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4fm5i8cojrpi952sbo3papqly` (`projet_id`),
  KEY `FKo5tno4amli3n39rkvg82b73jy` (`parent_id`),
  CONSTRAINT `FK4fm5i8cojrpi952sbo3papqly` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKo5tno4amli3n39rkvg82b73jy` FOREIGN KEY (`parent_id`) REFERENCES `element_projet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1937 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `element_projet`
-- ----------------------------
BEGIN;
INSERT INTO `element_projet` VALUES ('Dossier', '1411', '__MACOSX', null, null, null, '57'), ('Dossier', '1412', 'zip', null, null, '1411', '57'), ('Fichier', '1413', '._', 'DS_Store', b'0', '1412', '57'), ('Dossier', '1414', 'src', null, null, '1412', '57'), ('Dossier', '1415', 'zip', null, null, null, '57'), ('Dossier', '1416', '.idea', null, null, '1415', '57'), ('Fichier', '1417', 'compiler', 'xml', b'0', '1416', '57'), ('Dossier', '1418', 'copyright', null, null, '1416', '57'), ('Fichier', '1419', 'profiles_settings', 'xml', b'0', '1418', '57'), ('Fichier', '1420', 'description', 'html', b'0', '1416', '57'), ('Fichier', '1421', 'encodings', 'xml', b'0', '1416', '57'), ('Fichier', '1422', 'misc', 'xml', b'0', '1416', '57'), ('Fichier', '1423', 'modules', 'xml', b'0', '1416', '57'), ('Fichier', '1424', 'vcs', 'xml', b'0', '1416', '57'), ('Fichier', '1425', 'workspace', 'xml', b'0', '1416', '57'), ('Fichier', '1426', 'xtextAutoBuilderState', 'xml', b'0', '1416', '57'), ('Dossier', '1427', 'src', null, null, '1415', '57'), ('Fichier', '1428', 'Main', 'java', b'0', '1427', '57'), ('Dossier', '1429', 'extract', null, null, '1427', '57'), ('Fichier', '1430', 'ExtractAllFiles', 'java', b'0', '1429', '57'), ('Fichier', '1431', 'ExtractAllFilesWithInputStreams', 'java', b'0', '1429', '57'), ('Fichier', '1432', 'ExtractByLoopAllFiles', 'java', b'0', '1429', '57'), ('Fichier', '1433', 'ExtractSelectFilesWithInputStream', 'java', b'0', '1429', '57'), ('Fichier', '1434', 'ExtractSingleFile', 'java', b'0', '1429', '57'), ('Dossier', '1435', 'misc', null, null, '1427', '57'), ('Fichier', '1436', 'CheckZipFileSplitArchive', 'java', b'0', '1435', '57'), ('Fichier', '1437', 'ListAllFilesInZipFile', 'java', b'0', '1435', '57'), ('Fichier', '1438', 'ProgressInformation', 'java', b'0', '1435', '57'), ('Fichier', '1439', 'RemoveFileFromZipFile', 'java', b'0', '1435', '57'), ('Dossier', '1440', 'zip', null, null, '1427', '57'), ('Fichier', '1441', 'AddFilesDeflateComp', 'java', b'0', '1440', '57'), ('Fichier', '1442', 'AddFilesStoreComp', 'java', b'0', '1440', '57'), ('Fichier', '1443', 'AddFilesToFolderInZip', 'java', b'0', '1440', '57'), ('Fichier', '1444', 'AddFilesWithAESEncryption', 'java', b'0', '1440', '57'), ('Fichier', '1445', 'AddFilesWithStandardZipEncryption', 'java', b'0', '1440', '57'), ('Fichier', '1446', 'AddFolder', 'java', b'0', '1440', '57'), ('Fichier', '1447', 'AddStreamToZip', 'java', b'0', '1440', '57'), ('Fichier', '1448', 'CreateSplitZipFile', 'java', b'0', '1440', '57'), ('Fichier', '1449', 'CreateSplitZipFileFromFolder', 'java', b'0', '1440', '57'), ('Fichier', '1450', 'CreateZipWithOutputStreams', 'java', b'0', '1440', '57'), ('Fichier', '1451', 'CreateZipWithOutputStreamsStandardEnc', 'java', b'0', '1440', '57'), ('Fichier', '1452', 'zip', 'iml', b'0', '1415', '57'), ('Dossier', '1800', '__MACOSX', null, null, null, '168'), ('Dossier', '1801', 'zip', null, null, '1800', '168'), ('Fichier', '1802', '._', 'DS_Store', b'0', '1801', '168'), ('Dossier', '1803', 'src', null, null, '1801', '168'), ('Dossier', '1804', 'zip', null, null, null, '168'), ('Dossier', '1805', '.idea', null, null, '1804', '168'), ('Fichier', '1806', 'compiler', 'xml', b'0', '1805', '168'), ('Dossier', '1807', 'copyright', null, null, '1805', '168'), ('Fichier', '1808', 'profiles_settings', 'xml', b'0', '1807', '168'), ('Fichier', '1809', 'description', 'html', b'0', '1805', '168'), ('Fichier', '1810', 'encodings', 'xml', b'0', '1805', '168'), ('Fichier', '1811', 'misc', 'xml', b'0', '1805', '168'), ('Fichier', '1812', 'modules', 'xml', b'0', '1805', '168'), ('Fichier', '1813', 'vcs', 'xml', b'0', '1805', '168'), ('Fichier', '1814', 'workspace', 'xml', b'0', '1805', '168'), ('Fichier', '1815', 'xtextAutoBuilderState', 'xml', b'0', '1805', '168'), ('Dossier', '1816', 'src', null, null, '1804', '168'), ('Fichier', '1817', 'Main', 'java', b'0', '1816', '168'), ('Dossier', '1818', 'extract', null, null, '1816', '168'), ('Fichier', '1819', 'ExtractAllFiles', 'java', b'0', '1818', '168'), ('Fichier', '1820', 'ExtractAllFilesWithInputStreams', 'java', b'0', '1818', '168'), ('Fichier', '1821', 'ExtractByLoopAllFiles', 'java', b'0', '1818', '168'), ('Fichier', '1822', 'ExtractSelectFilesWithInputStream', 'java', b'0', '1818', '168'), ('Fichier', '1823', 'ExtractSingleFile', 'java', b'0', '1818', '168'), ('Dossier', '1824', 'misc', null, null, '1816', '168'), ('Fichier', '1825', 'CheckZipFileSplitArchive', 'java', b'0', '1824', '168'), ('Fichier', '1826', 'ListAllFilesInZipFile', 'java', b'0', '1824', '168'), ('Fichier', '1827', 'ProgressInformation', 'java', b'0', '1824', '168'), ('Fichier', '1828', 'RemoveFileFromZipFile', 'java', b'0', '1824', '168'), ('Dossier', '1829', 'zip', null, null, '1816', '168'), ('Fichier', '1830', 'AddFilesDeflateComp', 'java', b'0', '1829', '168'), ('Fichier', '1831', 'AddFilesStoreComp', 'java', b'0', '1829', '168'), ('Fichier', '1832', 'AddFilesToFolderInZip', 'java', b'0', '1829', '168'), ('Fichier', '1833', 'AddFilesWithAESEncryption', 'java', b'0', '1829', '168'), ('Fichier', '1834', 'AddFilesWithStandardZipEncryption', 'java', b'0', '1829', '168'), ('Fichier', '1835', 'AddFolder', 'java', b'0', '1829', '168'), ('Fichier', '1836', 'AddStreamToZip', 'java', b'0', '1829', '168'), ('Fichier', '1837', 'CreateSplitZipFile', 'java', b'0', '1829', '168'), ('Fichier', '1838', 'CreateSplitZipFileFromFolder', 'java', b'0', '1829', '168'), ('Fichier', '1839', 'CreateZipWithOutputStreams', 'java', b'0', '1829', '168'), ('Fichier', '1840', 'CreateZipWithOutputStreamsStandardEnc', 'java', b'0', '1829', '168'), ('Fichier', '1841', 'zip', 'iml', b'0', '1804', '168'), ('Dossier', '1843', '__MACOSX', null, null, null, '167'), ('Dossier', '1844', 'zip', null, null, '1843', '167'), ('Fichier', '1845', '._', 'DS_Store', b'0', '1844', '167'), ('Dossier', '1846', 'src', null, null, '1844', '167'), ('Dossier', '1847', 'zip', null, null, null, '167'), ('Dossier', '1848', '.idea', null, null, '1847', '167'), ('Fichier', '1849', 'compiler', 'xml', b'0', '1848', '167'), ('Dossier', '1850', 'copyright', null, null, '1848', '167'), ('Fichier', '1851', 'profiles_settings', 'xml', b'0', '1850', '167'), ('Fichier', '1852', 'description', 'html', b'0', '1848', '167'), ('Fichier', '1853', 'encodings', 'xml', b'0', '1848', '167'), ('Fichier', '1854', 'misc', 'xml', b'0', '1848', '167'), ('Fichier', '1855', 'modules', 'xml', b'0', '1848', '167'), ('Fichier', '1856', 'vcs', 'xml', b'0', '1848', '167'), ('Fichier', '1857', 'workspace', 'xml', b'0', '1848', '167'), ('Fichier', '1858', 'xtextAutoBuilderState', 'xml', b'0', '1848', '167'), ('Dossier', '1859', 'src', null, null, '1847', '167'), ('Fichier', '1860', 'Main', 'java', b'0', '1859', '167'), ('Dossier', '1861', 'extract', null, null, '1859', '167'), ('Fichier', '1862', 'ExtractAllFiles', 'java', b'0', '1861', '167'), ('Fichier', '1863', 'ExtractAllFilesWithInputStreams', 'java', b'0', '1861', '167'), ('Fichier', '1864', 'ExtractByLoopAllFiles', 'java', b'0', '1861', '167'), ('Fichier', '1865', 'ExtractSelectFilesWithInputStream', 'java', b'0', '1861', '167'), ('Fichier', '1866', 'ExtractSingleFile', 'java', b'0', '1861', '167'), ('Dossier', '1867', 'misc', null, null, '1859', '167'), ('Fichier', '1868', 'CheckZipFileSplitArchive', 'java', b'0', '1867', '167'), ('Fichier', '1869', 'ListAllFilesInZipFile', 'java', b'0', '1867', '167'), ('Fichier', '1870', 'ProgressInformation', 'java', b'0', '1867', '167'), ('Fichier', '1871', 'RemoveFileFromZipFile', 'java', b'0', '1867', '167'), ('Dossier', '1872', 'zip', null, null, '1859', '167'), ('Fichier', '1873', 'AddFilesDeflateComp', 'java', b'0', '1872', '167'), ('Fichier', '1874', 'AddFilesStoreComp', 'java', b'0', '1872', '167'), ('Fichier', '1875', 'AddFilesToFolderInZip', 'java', b'0', '1872', '167'), ('Fichier', '1876', 'AddFilesWithAESEncryption', 'java', b'0', '1872', '167'), ('Fichier', '1877', 'AddFilesWithStandardZipEncryption', 'java', b'0', '1872', '167'), ('Fichier', '1878', 'AddFolder', 'java', b'0', '1872', '167'), ('Fichier', '1879', 'AddStreamToZip', 'java', b'0', '1872', '167'), ('Fichier', '1880', 'CreateSplitZipFile', 'java', b'0', '1872', '167'), ('Fichier', '1881', 'CreateSplitZipFileFromFolder', 'java', b'0', '1872', '167'), ('Fichier', '1882', 'CreateZipWithOutputStreams', 'java', b'0', '1872', '167'), ('Fichier', '1883', 'CreateZipWithOutputStreamsStandardEnc', 'java', b'0', '1872', '167'), ('Fichier', '1884', 'zip', 'iml', b'0', '1847', '167'), ('Dossier', '1885', '__MACOSX', null, null, null, '169'), ('Dossier', '1886', 'zip', null, null, '1885', '169'), ('Fichier', '1887', '._', 'DS_Store', b'0', '1886', '169'), ('Dossier', '1888', 'src', null, null, '1886', '169'), ('Dossier', '1889', 'zip', null, null, null, '169'), ('Dossier', '1890', '.idea', null, null, '1889', '169'), ('Fichier', '1891', 'compiler', 'xml', b'0', '1890', '169'), ('Dossier', '1892', 'copyright', null, null, '1890', '169'), ('Fichier', '1893', 'profiles_settings', 'xml', b'0', '1892', '169'), ('Fichier', '1894', 'description', 'html', b'0', '1890', '169'), ('Fichier', '1895', 'encodings', 'xml', b'0', '1890', '169'), ('Fichier', '1896', 'misc', 'xml', b'0', '1890', '169'), ('Fichier', '1897', 'modules', 'xml', b'0', '1890', '169'), ('Fichier', '1898', 'vcs', 'xml', b'0', '1890', '169'), ('Fichier', '1899', 'workspace', 'xml', b'0', '1890', '169'), ('Fichier', '1900', 'xtextAutoBuilderState', 'xml', b'0', '1890', '169'), ('Dossier', '1901', 'src', null, null, '1889', '169'), ('Fichier', '1902', 'Main', 'java', b'0', '1901', '169'), ('Dossier', '1903', 'extract', null, null, '1901', '169'), ('Fichier', '1904', 'ExtractAllFiles', 'java', b'0', '1903', '169'), ('Fichier', '1905', 'ExtractAllFilesWithInputStreams', 'java', b'0', '1903', '169'), ('Fichier', '1906', 'ExtractByLoopAllFiles', 'java', b'0', '1903', '169'), ('Fichier', '1907', 'ExtractSelectFilesWithInputStream', 'java', b'0', '1903', '169'), ('Fichier', '1908', 'ExtractSingleFile', 'java', b'0', '1903', '169'), ('Dossier', '1909', 'misc', null, null, '1901', '169'), ('Fichier', '1910', 'CheckZipFileSplitArchive', 'java', b'0', '1909', '169'), ('Fichier', '1911', 'ListAllFilesInZipFile', 'java', b'0', '1909', '169'), ('Fichier', '1912', 'ProgressInformation', 'java', b'0', '1909', '169'), ('Fichier', '1913', 'RemoveFileFromZipFile', 'java', b'0', '1909', '169'), ('Dossier', '1914', 'zip', null, null, '1901', '169'), ('Fichier', '1915', 'AddFilesDeflateComp', 'java', b'0', '1914', '169'), ('Fichier', '1916', 'AddFilesStoreComp', 'java', b'0', '1914', '169'), ('Fichier', '1917', 'AddFilesToFolderInZip', 'java', b'0', '1914', '169'), ('Fichier', '1918', 'AddFilesWithAESEncryption', 'java', b'0', '1914', '169'), ('Fichier', '1919', 'AddFilesWithStandardZipEncryption', 'java', b'0', '1914', '169'), ('Fichier', '1920', 'AddFolder', 'java', b'0', '1914', '169'), ('Fichier', '1921', 'AddStreamToZip', 'java', b'0', '1914', '169'), ('Fichier', '1922', 'CreateSplitZipFile', 'java', b'0', '1914', '169'), ('Fichier', '1923', 'CreateSplitZipFileFromFolder', 'java', b'0', '1914', '169'), ('Fichier', '1924', 'CreateZipWithOutputStreams', 'java', b'0', '1914', '169'), ('Fichier', '1925', 'CreateZipWithOutputStreamsStandardEnc', 'java', b'0', '1914', '169'), ('Fichier', '1926', 'zip', 'iml', b'0', '1889', '169'), ('Fichier', '1933', 'dsqd', 'java', b'0', null, '179'), ('Fichier', '1936', 'main', 'java', b'0', null, '180');
COMMIT;

-- ----------------------------
--  Table structure for `element_projet_element_projet`
-- ----------------------------
DROP TABLE IF EXISTS `element_projet_element_projet`;
CREATE TABLE `element_projet_element_projet` (
  `Dossier_id` int(11) NOT NULL,
  `elementProjets_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ml694hvse6qefuv0cirhi6mti` (`elementProjets_id`),
  KEY `FK7j0ws98cqo6nw7f5nlx1oagvq` (`Dossier_id`),
  CONSTRAINT `FK7j0ws98cqo6nw7f5nlx1oagvq` FOREIGN KEY (`Dossier_id`) REFERENCES `element_projet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKmk8jdd09bafjl9ng1strcdobm` FOREIGN KEY (`elementProjets_id`) REFERENCES `element_projet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `javadoc_attribut`
-- ----------------------------
DROP TABLE IF EXISTS `javadoc_attribut`;
CREATE TABLE `javadoc_attribut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `javaDocMethode_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3lg0gcduj8pdmtq3dtsb95y9x` (`javaDocMethode_id`),
  CONSTRAINT `FK3lg0gcduj8pdmtq3dtsb95y9x` FOREIGN KEY (`javaDocMethode_id`) REFERENCES `javadoc_methode` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `javadoc_attribut`
-- ----------------------------
BEGIN;
INSERT INTO `javadoc_attribut` VALUES ('13', 'args', 'String[]', '9'), ('14', 'args', 'String[]', '10'), ('15', 'is', 'ZipInputStream', '11'), ('16', 'os', 'OutputStream', '11'), ('17', 'args', 'String[]', '12');
COMMIT;

-- ----------------------------
--  Table structure for `javadoc_classe`
-- ----------------------------
DROP TABLE IF EXISTS `javadoc_classe`;
CREATE TABLE `javadoc_classe` (
  `DTYPE` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `javaDocFichier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg6dlekwl9twg24wmxd3scl92` (`javaDocFichier_id`),
  CONSTRAINT `FKg6dlekwl9twg24wmxd3scl92` FOREIGN KEY (`javaDocFichier_id`) REFERENCES `javadoc_fichier` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `javadoc_fichier`
-- ----------------------------
DROP TABLE IF EXISTS `javadoc_fichier`;
CREATE TABLE `javadoc_fichier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classeName` varchar(255) DEFAULT NULL,
  `packageName` varchar(255) DEFAULT NULL,
  `fichier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKljylja1urq265tcm3i29nj1tx` (`fichier_id`),
  CONSTRAINT `FKljylja1urq265tcm3i29nj1tx` FOREIGN KEY (`fichier_id`) REFERENCES `element_projet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1438 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `javadoc_fichier`
-- ----------------------------
BEGIN;
INSERT INTO `javadoc_fichier` VALUES ('1075', null, null, '1413'), ('1076', null, null, '1417'), ('1077', null, null, '1419'), ('1078', null, null, '1420'), ('1079', null, null, '1421'), ('1080', null, null, '1422'), ('1081', null, null, '1423'), ('1082', null, null, '1424'), ('1083', null, null, '1425'), ('1084', null, null, '1426'), ('1085', null, null, '1428'), ('1086', null, null, '1430'), ('1087', null, null, '1431'), ('1088', null, null, '1432'), ('1089', null, null, '1433'), ('1090', null, null, '1434'), ('1091', null, null, '1436'), ('1092', null, null, '1437'), ('1093', null, null, '1438'), ('1094', null, null, '1439'), ('1095', null, null, '1441'), ('1096', null, null, '1442'), ('1097', null, null, '1443'), ('1098', null, null, '1444'), ('1099', null, null, '1445'), ('1100', null, null, '1446'), ('1101', null, null, '1447'), ('1102', null, null, '1448'), ('1103', null, null, '1449'), ('1104', null, null, '1450'), ('1105', null, null, '1451'), ('1106', null, null, '1452'), ('1371', null, null, '1802'), ('1372', null, null, '1806'), ('1373', null, null, '1808'), ('1374', null, null, '1809'), ('1375', null, null, '1810'), ('1376', null, null, '1811'), ('1377', null, null, '1812'), ('1378', null, null, '1813'), ('1379', null, null, '1814'), ('1380', null, null, '1815'), ('1381', null, null, '1817'), ('1382', null, null, '1819'), ('1383', null, null, '1820'), ('1384', null, null, '1821'), ('1385', null, null, '1822'), ('1386', 'ExtractSingleFile', 'extractsinglefile', '1823'), ('1387', null, null, '1825'), ('1388', null, null, '1826'), ('1389', null, null, '1827'), ('1390', null, null, '1828'), ('1391', null, null, '1830'), ('1392', null, null, '1831'), ('1393', null, null, '1832'), ('1394', null, null, '1833'), ('1395', null, null, '1834'), ('1396', null, null, '1835'), ('1397', null, null, '1836'), ('1398', null, null, '1837'), ('1399', null, null, '1838'), ('1400', null, null, '1839'), ('1401', null, null, '1840'), ('1402', null, null, '1841'), ('1404', null, null, '1845'), ('1405', null, null, '1849'), ('1406', null, null, '1851'), ('1407', null, null, '1852'), ('1408', null, null, '1853'), ('1409', null, null, '1854'), ('1410', null, null, '1855'), ('1411', null, null, '1856'), ('1412', null, null, '1857'), ('1413', null, null, '1858'), ('1414', null, null, '1860'), ('1415', null, null, '1862'), ('1416', null, null, '1863'), ('1417', null, null, '1864'), ('1418', null, null, '1865'), ('1419', null, null, '1866'), ('1420', null, null, '1868'), ('1421', null, null, '1869'), ('1422', null, null, '1870'), ('1423', null, null, '1871'), ('1424', null, null, '1873'), ('1425', null, null, '1874'), ('1426', null, null, '1875'), ('1427', null, null, '1876'), ('1428', null, null, '1877'), ('1429', null, null, '1878'), ('1430', null, null, '1879'), ('1431', null, null, '1880'), ('1432', null, null, '1881'), ('1433', null, null, '1882'), ('1434', null, null, '1883'), ('1435', null, null, '1884'), ('1436', 'ListAllFilesInZipFile', 'misc', '1911'), ('1437', 'ExtractAllFilesWithInputStreams', 'extract', '1905');
COMMIT;

-- ----------------------------
--  Table structure for `javadoc_import`
-- ----------------------------
DROP TABLE IF EXISTS `javadoc_import`;
CREATE TABLE `javadoc_import` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `javaDocFichier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4l6qcueyo8l7y62bb19v3ifys` (`javaDocFichier_id`),
  CONSTRAINT `FK4l6qcueyo8l7y62bb19v3ifys` FOREIGN KEY (`javaDocFichier_id`) REFERENCES `javadoc_fichier` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `javadoc_import`
-- ----------------------------
BEGIN;
INSERT INTO `javadoc_import` VALUES ('21', 'ZipFile', '1386'), ('22', 'ZipException', '1386'), ('23', 'List', '1436'), ('24', 'ZipFile', '1436'), ('25', 'ZipException', '1436'), ('26', 'FileHeader', '1436'), ('27', 'File', '1437'), ('28', 'FileNotFoundException', '1437'), ('29', 'FileOutputStream', '1437'), ('30', 'IOException', '1437'), ('31', 'OutputStream', '1437'), ('32', 'List', '1437'), ('33', 'ZipFile', '1437'), ('34', 'ZipException', '1437'), ('35', 'ZipInputStream', '1437'), ('36', 'FileHeader', '1437'), ('37', 'UnzipUtil', '1437');
COMMIT;

-- ----------------------------
--  Table structure for `javadoc_methode`
-- ----------------------------
DROP TABLE IF EXISTS `javadoc_methode`;
CREATE TABLE `javadoc_methode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contentJavaDoc` longblob,
  `declaration` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `javaDocFichier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8x9wi15rbn5u5g5mgm38ru94c` (`javaDocFichier_id`),
  CONSTRAINT `FK8x9wi15rbn5u5g5mgm38ru94c` FOREIGN KEY (`javaDocFichier_id`) REFERENCES `javadoc_fichier` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `javadoc_methode`
-- ----------------------------
BEGIN;
INSERT INTO `javadoc_methode` VALUES ('9', 0x0a09202a2040706172616d20617267730a0920, 'public static void main(String[] args)', 'main', '1386'), ('10', 0x0a09202a2040706172616d20617267730a0920, 'public static void main(String[] args)', 'main', '1436'), ('11', '', 'private void closeFileHandlers(ZipInputStream is, OutputStream os) throws IOException', 'closeFileHandlers', '1437'), ('12', 0x0a09202a2040706172616d20617267730a0920, 'public static void main(String[] args)', 'main', '1437');
COMMIT;

-- ----------------------------
--  Table structure for `page`
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `home` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `wiki_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiwqpyiu6sduonetwg0f2g74st` (`wiki_id`),
  CONSTRAINT `FKiwqpyiu6sduonetwg0f2g74st` FOREIGN KEY (`wiki_id`) REFERENCES `wiki` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `page`
-- ----------------------------
BEGIN;
INSERT INTO `page` VALUES ('3', 'dsq', b'1', 'dsqd', '3'), ('5', 'dqs', b'1', 'dsq', '4');
COMMIT;

-- ----------------------------
--  Table structure for `priorite_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `priorite_ticket`;
CREATE TABLE `priorite_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `priorite_ticket`
-- ----------------------------
BEGIN;
INSERT INTO `priorite_ticket` VALUES ('1', 'Urgent'), ('2', 'Normal');
COMMIT;

-- ----------------------------
--  Table structure for `projet`
-- ----------------------------
DROP TABLE IF EXISTS `projet`;
CREATE TABLE `projet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `projet`
-- ----------------------------
BEGIN;
INSERT INTO `projet` VALUES ('57', '', 'kjnnvnnvgg'), ('167', '', 'jj'), ('168', '', 'flsdkfds'), ('169', '', 'plop'), ('172', '', 'jnhbg'), ('175', '', 'dsqddmmdmd'), ('177', '', 'mplokij'), ('178', 'dsq', 'mmmm'), ('179', 'mm', 'mdmdmmd'), ('180', '', 'totoplop');
COMMIT;

-- ----------------------------
--  Table structure for `projet_droit`
-- ----------------------------
DROP TABLE IF EXISTS `projet_droit`;
CREATE TABLE `projet_droit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projet_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6bq6g5jfmv2tvd3cr0lubsec3` (`projet_id`),
  KEY `FKg7pajkddmnjnua3v18gsd620b` (`user_id`),
  CONSTRAINT `FK6bq6g5jfmv2tvd3cr0lubsec3` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKg7pajkddmnjnua3v18gsd620b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `projet_droit`
-- ----------------------------
BEGIN;
INSERT INTO `projet_droit` VALUES ('49', '57', '11'), ('162', '167', '12'), ('163', '168', '12'), ('164', '168', '11'), ('165', '169', '12'), ('168', '172', '18'), ('171', '175', '18'), ('174', '178', '18'), ('175', '179', '18'), ('176', '180', '18');
COMMIT;

-- ----------------------------
--  Table structure for `statut_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `statut_ticket`;
CREATE TABLE `statut_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `statut_ticket`
-- ----------------------------
BEGIN;
INSERT INTO `statut_ticket` VALUES ('1', 'New'), ('2', 'In progress');
COMMIT;

-- ----------------------------
--  Table structure for `ticket`
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `realisation` varchar(255) DEFAULT NULL,
  `sujet` varchar(255) DEFAULT NULL,
  `tempsEstime` varchar(255) DEFAULT NULL,
  `prioriteTicket_id` int(11) DEFAULT NULL,
  `projet_id` int(11) DEFAULT NULL,
  `statutTicket_id` int(11) DEFAULT NULL,
  `tracker_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmvap3bsnyycm5ejl3ygsi9pas` (`prioriteTicket_id`),
  KEY `FKjqwwy0h8htyqoeut8nidaqq0l` (`statutTicket_id`),
  KEY `FKe8c5hbw9jun9rpy8te9b5f1ip` (`tracker_id`),
  KEY `FKdvt57mcco3ogsosi97odw563o` (`user_id`),
  KEY `FKq616n53g5hl27igca9t1lwnl0` (`projet_id`),
  CONSTRAINT `FKdvt57mcco3ogsosi97odw563o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKe8c5hbw9jun9rpy8te9b5f1ip` FOREIGN KEY (`tracker_id`) REFERENCES `tracker` (`id`),
  CONSTRAINT `FKjqwwy0h8htyqoeut8nidaqq0l` FOREIGN KEY (`statutTicket_id`) REFERENCES `statut_ticket` (`id`),
  CONSTRAINT `FKmvap3bsnyycm5ejl3ygsi9pas` FOREIGN KEY (`prioriteTicket_id`) REFERENCES `priorite_ticket` (`id`),
  CONSTRAINT `FKq616n53g5hl27igca9t1lwnl0` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ticket`
-- ----------------------------
BEGIN;
INSERT INTO `ticket` VALUES ('52', '<p>fsd</p>', '0', 'fdsfs', '0', '1', '168', '1', '1', '11'), ('53', '<p>d</p>', '0', 'dsq', '0', '1', '172', '1', '1', '18');
COMMIT;

-- ----------------------------
--  Table structure for `tracker`
-- ----------------------------
DROP TABLE IF EXISTS `tracker`;
CREATE TABLE `tracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tracker`
-- ----------------------------
BEGIN;
INSERT INTO `tracker` VALUES ('1', 'Bug'), ('2', 'Build'), ('3', 'Feature');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `branche_id` int(11) DEFAULT NULL,
  `activate` bit(1) NOT NULL,
  `googleAccount` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqgbeeiftef9kbf4e1nlq49hcw` (`branche_id`),
  CONSTRAINT `FKqgbeeiftef9kbf4e1nlq49hcw` FOREIGN KEY (`branche_id`) REFERENCES `branche` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('11', 'p@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'1', b'0'), ('12', 'encomm.agency@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('13', 'kkkkkkjh@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('14', 'mmm@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('18', 'encom.agency@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'1', b'0'), ('19', 'encomdsqd.agency@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('20', 'mdmdmmd@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('21', 'lnjbhhbhb@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('23', 'kvkkbkbkbk@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('29', 'multimifgr6lyon1@gmail.com', null, '0174a4f2db2c7e94bccfc150d478305e', null, b'1', b'1'), ('30', 'kfkfkkfkfkfkfk@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('31', 'nhnhnhnhndhgbvb@gmail.com', null, '4a7d1ed414474e4033ac29ccb8653d9b', null, b'0', b'0'), ('32', 'lpkfjgnvgdt@gmail.com', null, 'c6f057b86584942e415435ffb1fa93d4', null, b'1', b'0'), ('59', 'encom.agency@gmail.com', null, '3d4ac174864460fc4443445a4878e689', null, b'1', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `user_projet`
-- ----------------------------
DROP TABLE IF EXISTS `user_projet`;
CREATE TABLE `user_projet` (
  `id_user` int(11) NOT NULL,
  `id_projet` int(11) NOT NULL,
  KEY `FKht2d5nxcetwoxf1t748377cxm` (`id_user`),
  KEY `FK9s05uhs03cyg238mxjhkuwnb3` (`id_projet`),
  CONSTRAINT `FK9s05uhs03cyg238mxjhkuwnb3` FOREIGN KEY (`id_projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKht2d5nxcetwoxf1t748377cxm` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user_projet`
-- ----------------------------
BEGIN;
INSERT INTO `user_projet` VALUES ('12', '167'), ('12', '168'), ('12', '169'), ('11', '57'), ('11', '168'), ('18', '172'), ('18', '175'), ('18', '178'), ('18', '179'), ('18', '180');
COMMIT;

-- ----------------------------
--  Table structure for `wiki`
-- ----------------------------
DROP TABLE IF EXISTS `wiki`;
CREATE TABLE `wiki` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projet_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp1wrohl510e2qird0csloid5o` (`projet_id`),
  CONSTRAINT `FKp1wrohl510e2qird0csloid5o` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `wiki`
-- ----------------------------
BEGIN;
INSERT INTO `wiki` VALUES ('3', '57'), ('4', '178');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
