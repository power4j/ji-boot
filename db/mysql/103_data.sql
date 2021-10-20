USE ji_boot;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*Data for the table `t_sys_dict` */
insert  into `t_sys_dict`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`code`,`name`,`remarks`) values (1000000,1,NULL,'2020-12-30 13:57:19',NULL,'sys_org_tags','组织标签',NULL);

/*Data for the table `t_sys_dict_item` */
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (1000001,1,NULL,'2020-12-30 13:57:19',NULL,1000000,'1','内部组织',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (1000002,1,NULL,'2020-12-30 13:57:19',NULL,1000000,'2','供应渠道',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (1000003,1,NULL,'2020-12-30 13:57:19',NULL,1000000,'3','市场片区',NULL);

/*Data for the table `t_org_node` */
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1, 1, 0);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1, 1372893325519036417, 1);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1, 1372900735176482818, 2);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1, 1372902329179774977, 3);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1, 1372904031341584386, 3);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372893325519036417, 1372893325519036417, 0);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372893325519036417, 1372900735176482818, 1);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372893325519036417, 1372902329179774977, 2);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372893325519036417, 1372904031341584386, 2);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372900735176482818, 1372900735176482818, 0);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372900735176482818, 1372902329179774977, 1);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372900735176482818, 1372904031341584386, 1);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372902329179774977, 1372902329179774977, 0);
insert  into `t_org_node`(`ancestor`, `descendant`, `distance`) VALUES (1372904031341584386, 1372904031341584386, 0);

/*Data for the table `t_sys_org` */
insert  into `t_sys_org`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `tag`, `code`, `name`, `sort`) VALUES (1, 1, NULL, '2020-12-20 23:31:10', NULL, '0', '0', '根节点', 0);
insert  into `t_sys_org`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `tag`, `code`, `name`, `sort`) VALUES (1372893325519036417, 1, NULL, '2020-12-20 23:31:10', NULL, '1', 'G0', 'G0集团', 0);
insert  into `t_sys_org`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `tag`, `code`, `name`, `sort`) VALUES (1372900735176482818, 1, NULL, '2021-03-19 21:20:17', NULL, '1', 'ES0', '西南分公司', 1);
insert  into `t_sys_org`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `tag`, `code`, `name`, `sort`) VALUES (1372902329179774977, 1, NULL, '2021-03-19 21:26:37', NULL, '1', 'ES02', '运营中心', 2);
insert  into `t_sys_org`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `tag`, `code`, `name`, `sort`) VALUES (1372904031341584386, 1, NULL, '2021-03-19 21:33:23', '2021-03-19 21:37:17', '2', 'RD0', '上海特斯拉', 0);

/*Data for the table `t_sys_position` */
insert  into `t_sys_position`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`code`,`name`,`remarks`,`status`,`owner`) values (1380525982978342913,1,NULL,'2021-04-09 22:20:18',NULL,'admin','IT admin','test','0',2000);

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`code`,`name`,`remarks`,`status`,`owner`) values (1338178645550809090,1,NULL,'2020-12-14 01:47:06',NULL,'admin','系统管理员',NULL,'0',NULL);

/*Data for the table `t_sys_role_grantee` */

insert  into `t_sys_role_grantee`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`user_id`,`role_id`,`grant_type`) values (1,1,NULL,'2020-12-20 23:31:10','2020-12-21 02:13:44',1,1338178645550809090,'1');

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`id`,`sys_flag`,`del_flag`,`create_by`,`create_at`,`update_by`,`update_at`,`username`,`password`,`salt`,`name`,`mail`,`mobile_phone`,`remarks`,`status`,`org_id`,`post_id`) values (1,1,NULL,NULL,'2020-10-29 15:51:39',NULL,NULL,'admin','$2a$10$OO/CElRgmt9nhFQsHDDs3ulWkTcZ5/4t09YVMVU9Y8AzDthkm6lWO',NULL,'管理员','power4j@outlook.com','18081020301','初始用户','0',1372893325519036417,1380525982978342913);

SET FOREIGN_KEY_CHECKS = 1;