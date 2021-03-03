USE ji_boot;
SET NAMES utf8mb4;

CREATE TABLE `t_sys_user`
(
  `id`          BIGINT      NOT NULL COMMENT '主健',
  `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
  `del_flag`    DATETIME    NULL COMMENT '删除标志',
  `create_at`   DATETIME COMMENT '创建时间',
  `update_at`   DATETIME COMMENT '更新时间',
  `username`    VARCHAR(20) NOT NULL COMMENT '登录用户名',
  `password`    VARCHAR(200) NOT NULL COMMENT '密码',
  `salt`        VARCHAR(20) COMMENT '加密盐',
  `name`        VARCHAR(20) NOT NULL COMMENT '姓名',
  `mail`        VARCHAR(40) COMMENT '邮箱',
  `mobile_phone` VARCHAR(16) COMMENT '手机号码',
  `remarks`     VARCHAR(20) COMMENT '管理员备注',
  `status`      CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态 0 有效 1 停用',
  `create_by`   VARCHAR(20) COMMENT '创建人',
  `update_by`   VARCHAR(20) COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='用户';

ALTER TABLE `t_sys_user` ADD INDEX `idx_1` (`sys_flag`);
ALTER TABLE `t_sys_user` ADD INDEX `idx_del_flag` (`del_flag`);
ALTER TABLE `t_sys_user` ADD INDEX `idx_create_at` (`create_at`);

CREATE TABLE `t_api_token`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `token`       VARCHAR(40) NOT NULL COMMENT '访问令牌',
    `uuid`        VARCHAR(20) NOT NULL COMMENT '用户UID',
    `username`    VARCHAR(200) NOT NULL COMMENT '登录用户名',
    `expire_in`   DATETIME NOT NULL COMMENT '过期时间',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_token` (`token`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='访问令牌';
ALTER TABLE `t_api_token` ADD INDEX `idx_1` (`uuid`,`expire_in`,`username`);

CREATE TABLE `t_sys_dict`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `code`    VARCHAR(40) NOT NULL COMMENT '代码',
    `name`    VARCHAR(40) NOT NULL COMMENT '名称',
    `remarks`     VARCHAR(20) COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='字典';

CREATE TABLE `t_sys_dict_item`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `dict_id`     BIGINT      NOT NULL COMMENT '字典ID',
    `value`       VARCHAR(255) NOT NULL COMMENT '值',
    `label`       VARCHAR(255) NOT NULL COMMENT '标签',
    `remarks`     VARCHAR(40) COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_dict_val` (`dict_id`,`value`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='字典项';

CREATE TABLE `t_sys_resource`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `type`        CHAR(2)  NOT NULL COMMENT '菜单类型',
    `name`        VARCHAR(255) NOT NULL COMMENT '路由名称',
    `title`       VARCHAR(20) NOT NULL COMMENT '显示名称',
    `permission`  VARCHAR(255) NULL COMMENT '权限代码',
    `path`        VARCHAR(255) NULL COMMENT '路由地址',
    `component`   VARCHAR(255) NULL COMMENT 'UI组件',
    `cache`       TINYINT NOT NULL DEFAULT '0' COMMENT '路由缓冲',
    `icon`        VARCHAR(40)  NULL COMMENT '图标',
    `sort`        INT NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='UI资源';


CREATE TABLE `t_resource_node`
(
    `ancestor`     BIGINT NOT NULL DEFAULT 0 COMMENT '祖先ID',
    `descendant`   BIGINT NOT NULL COMMENT '后代ID',
    `distance`   INT NOT NULL COMMENT '层距离',
    PRIMARY KEY (`ancestor`,`descendant`,`distance`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='UI节点';

CREATE TABLE `t_sys_param`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `param_key`   VARCHAR(255) NOT NULL COMMENT '参数名',
    `param_value` TEXT NOT NULL COMMENT '参数值',
    `remarks`     VARCHAR(20) COMMENT '备注',
    `status`      CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态 0 有效 1 停用',
    `create_by`   VARCHAR(20) COMMENT '创建人',
    `update_by`   VARCHAR(20) COMMENT '更新人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_param_key` (`param_key`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='系统参数';

CREATE TABLE `t_sys_role`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `code`        VARCHAR(20) NOT NULL COMMENT '编码',
    `name`        VARCHAR(20) NOT NULL COMMENT '名称',
    `remarks`     VARCHAR(20) COMMENT '备注',
    `status`      CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态 0 有效 1 停用',
    `owner`       BIGINT COMMENT '拥有者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='角色';

CREATE TABLE `t_sys_resource_grantee`
(
    `role_id`     BIGINT NOT NULL DEFAULT 0 COMMENT '角色ID',
    `resource_id` BIGINT NOT NULL COMMENT '资源ID',
    PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='资源授权';

CREATE TABLE `t_sys_role_grantee`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `user_id`     BIGINT NOT NULL COMMENT '资源ID',
    `role_id`     BIGINT NOT NULL DEFAULT 0 COMMENT '角色ID',
    `grant_type`  CHAR(1) NOT NULL DEFAULT 0 COMMENT '授权类型 0 普通 1 管理员',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='角色授权';

CREATE TABLE `t_sys_job`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `group_name`  VARCHAR(20) COMMENT '作业组',
    `cron`        VARCHAR(40) NOT NULL COMMENT 'Cron 表达式',
    `task_bean`   VARCHAR(255) NOT NULL  COMMENT 'bean名称',
    `param`       VARCHAR(255) COMMENT '任务参数',
    `short_description`     VARCHAR(20) COMMENT '任务说明',
    `status`      CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态 0 正常 1 停止调度',
    `mis_fire_policy` CHAR(1) NOT NULL DEFAULT '0' COMMENT '调度丢失补救策略',
    `fail_recover`  tinyint NOT NULL DEFAULT '0' COMMENT '允许故障转移',
    `error_retry`   tinyint NOT NULL DEFAULT '0' COMMENT '是否允许失败重试',
    `create_by`   VARCHAR(20) COMMENT '创建人',
    `update_by`   VARCHAR(20) COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='任务调度';

ALTER TABLE `t_sys_job` ADD INDEX `idx_sys_flag` (`sys_flag`);
ALTER TABLE `t_sys_job` ADD INDEX `idx_del_flag` (`del_flag`);

CREATE TABLE `t_sys_job_log`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `execution_id` VARCHAR(40) NOT NULL COMMENT '执行ID',
    `fire_by`      VARCHAR(100) COMMENT '触发者',
    `job_id`       BIGINT      NOT NULL COMMENT '作业ID',
    `group_name`   VARCHAR(20) COMMENT '作业组',
    `task_bean`    VARCHAR(255) COMMENT 'bean名称',
    `short_description`  VARCHAR(20) COMMENT '任务说明',
    `start_time`   DATETIME NOT NULL COMMENT '执行开始时间',
    `end_time`     DATETIME NOT NULL COMMENT '执行结束时间',
    `execute_ms`   BIGINT NOT NULL DEFAULT '0' COMMENT '执行耗时',
    `success`      TINYINT NOT NULL DEFAULT '0' COMMENT '是否成功',
    `ex`       VARCHAR(255) COMMENT '异常',
    `ex_msg`   VARCHAR(255) COMMENT '异常信息',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='任务执行日志';

ALTER TABLE `t_sys_job_log` ADD INDEX `idx_1` (`task_bean`,`success`,`ex`,`start_time`);

CREATE TABLE `t_ureport_data`
(
    `id`          BIGINT      NOT NULL COMMENT '主健',
    `sys_flag`    TINYINT     NOT NULL DEFAULT 0 COMMENT '数据标记 0 普通数据, 1 系统保护数据',
    `del_flag`    DATETIME    NULL COMMENT '删除标志',
    `create_at`   DATETIME COMMENT '创建时间',
    `update_at`   DATETIME COMMENT '更新时间',
    `file`    VARCHAR(255) NOT NULL COMMENT '名称',
    `data` MEDIUMBLOB NOT NULL COMMENT '数据',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`file`)
) ENGINE = InnoDB CHARSET = `utf8mb4` COMMENT ='UR报表';