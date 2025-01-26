CREATE TABLE `dept` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `status` int NOT NULL DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
                        `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除 0-未删除 1-删除',
                        `create_by` varchar(100) DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;