CREATE TABLE `t_sys_user`
(
  `id`          BIGINT      NOT NULL COMMENT '主健',
  `del_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '删除标志',
  `create_by`   VARCHAR(20) COMMENT '创建人',
  `create_at`   DATETIME COMMENT '创建时间',
  `update_by`   VARCHAR(20) COMMENT '更新人',
  `update_at`   DATETIME COMMENT '更新时间',
  `username`    VARCHAR(20) NOT NULL COMMENT '登录用户名',
  `password`    VARCHAR(40) NOT NULL COMMENT '密码',
  `salt`        VARCHAR(20) COMMENT '加密盐',
  `name`        VARCHAR(20) NOT NULL COMMENT '姓名',
  `mail`        VARCHAR(40) COMMENT '邮箱',
  `mobile_phone` VARCHAR(16) COMMENT '手机号码',
  `remarks`     VARCHAR(20) COMMENT '管理员备注',
  `status`      TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0 有效 1 停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  CHARSET = `utf8mb4` COMMENT ='用户';

ALTER TABLE `t_sys_user`
  ADD INDEX `idx_page_query` (`create_at`);
