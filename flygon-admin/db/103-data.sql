-- sys user
INSERT INTO `t_sys_user`(`id`, `sys_flag`, `del_flag`, `create_by`, `create_at`, `update_by`, `update_at`, `username`, `password`, `salt`, `name`, `mail`, `mobile_phone`, `remarks`, `status`)
VALUES (1, 1, NULL, NULL, NOW(), NULL, NULL, 'admin', '$2a$10$OO/CElRgmt9nhFQsHDDs3ulWkTcZ5/4t09YVMVU9Y8AzDthkm6lWO', NULL, '管理员', 'power4j@outlook.com', '18081020301', '初始用户', 0);

-- 字典
INSERT INTO `t_sys_dict`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `code`, `name`, `remarks`)
VALUES (1000001, 1, NULL, NOW(), NULL, 'sys_menu_type', '菜单类型', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2001001, 1, NULL, NOW(), NULL, 1000001, '1', '菜单', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2001002, 1, NULL, NOW(), NULL, 1000001, '2', '按钮', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2001003, 1, NULL, NOW(), NULL, 1000001, '3', '路由', '路由不会展示');

INSERT INTO `t_sys_dict`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `code`, `name`, `remarks`)
VALUES (1000002, 1, NULL, NOW(), NULL, 'sys_user_status', '用户状态', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2002001, 1, NULL, NOW(), NULL, 1000002, '0', '正常', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2002002, 1, NULL, NOW(), NULL, 1000002, '1', '禁用', NULL);

INSERT INTO `t_sys_dict`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `code`, `name`, `remarks`)
VALUES (1000003, 1, NULL, NOW(), NULL, 'sys_param_status', '参数状态', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2003001, 1, NULL, NOW(), NULL, 1000003, '0', '正常', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2003002, 1, NULL, NOW(), NULL, 1000003, '1', '禁用', NULL);

INSERT INTO `t_sys_dict`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `code`, `name`, `remarks`)
VALUES (1000004, 1, NULL, NOW(), NULL, 'sys_role_status', '角色状态', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2004001, 1, NULL, NOW(), NULL, 1000004, '0', '正常', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2004002, 1, NULL, NOW(), NULL, 1000004, '1', '禁用', NULL);

INSERT INTO `t_sys_dict`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `code`, `name`, `remarks`)
VALUES (1000005, 1, NULL, NOW(), NULL, 'sys_role_grant_type', '角色授权类型', NULL);
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2005001, 1, NULL, NOW(), NULL, 1000005, '0', '普通', '获得权限,但不能分发授权');
INSERT INTO `t_sys_dict_item`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `dict_id`, `value`, `label`, `remarks`)
VALUES (2005002, 1, NULL, NOW(), NULL, 1000005, '1', '管理员', '可进行授予,回收');

-- 资源根节点
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (0, 1, NULL, NOW(), NULL, 1, '根节点', '根节点', '', '', '', '', 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (0, 0, 0);

-- 一级 系统管理
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (10010000001, 0, NULL, NOW(), NULL, 1, 'sys', '系统管理', '', '/sys', 'layoutHeaderAside', '', 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,10010000001,distance+1 FROM t_resource_node WHERE descendant=0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (10010000001, 10010000001, 0);

-- 二级 权限管理
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (10020000001, 0, NULL, NOW(), NULL, 1, 'permission', '权限管理', '', '/permission', '', '', 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,10020000001,distance+1 FROM t_resource_node WHERE descendant=10010000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (10020000001, 10020000001, 0);

INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (10030000001, 0, NULL, NOW(), NULL, 1, 'perm-resource', '受限资源', '', '/permission/resource', '/permission/views/resource', 'address-book', 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,10030000001,distance+1 FROM t_resource_node WHERE descendant=10020000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (10030000001, 10030000001, 0);

INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (20000000001, 0, NULL, NOW(), NULL, 2, 'addResource', '添加资源', '', '', '', '', 1);
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (20000000002, 0, NULL, NOW(), NULL, 2, 'delResource', '删除资源', '', '', '', '', 2);
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (20000000003, 0, NULL, NOW(), NULL, 2, 'editResource', '修改资源', '', '', '', '', 3);
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (20000000004, 0, NULL, NOW(), NULL, 2, 'viewResource', '查看资源', '', '', '', '', 4);

INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,20000000001,distance+1 FROM t_resource_node WHERE descendant=10030000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (20000000001, 20000000001, 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,20000000002,distance+1 FROM t_resource_node WHERE descendant=10030000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (20000000002, 20000000002, 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,20000000003,distance+1 FROM t_resource_node WHERE descendant=10030000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (20000000003, 20000000003, 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,20000000004,distance+1 FROM t_resource_node WHERE descendant=10030000001);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (20000000004, 20000000004, 0);

-- 一级 用户管理
INSERT INTO `t_sys_resource`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `type`, `name`, `title`, `permission`, `path`, `component`, `icon`, `sort`)
VALUES (10010000002, 0, NULL, NOW(), NULL, 1, 'user', '用户管理', '', '/user', '', '', 0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) (SELECT ancestor,10010000002,distance+1 FROM t_resource_node WHERE descendant=0);
INSERT INTO `t_resource_node`(`ancestor`, `descendant`, `distance`) VALUES (10010000002, 10010000002, 0);




