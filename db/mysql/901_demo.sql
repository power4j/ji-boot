USE ji_boot;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

insert  into `t_sys_role`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`code`,`name`,`remarks`,`status`,`owner`)
values (1341424978898296834,0,NULL,'2020-12-23 00:46:52','2020-12-30 20:04:01','test','测试角色',NULL,'3',1);

insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,10010000001);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,10010000002);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,10020000001);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1338793651296350209);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1338795784578027521);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1338795995027230721);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1338796190397911041);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339095398288048129);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339095620003151874);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339141805648650242);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339141875248930817);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339141968685441026);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339142060758802434);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1339142219286716418);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1340676892001431553);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1340701292788572161);
insert  into `t_sys_resource_grantee`(`role_id`,`resource_id`) values (1341424978898296834,1340701705814269954);

insert  into `t_sys_user`(`id`,`sys_flag`,`del_flag`,`create_by`,`create_at`,`update_by`,`update_at`,`username`,`password`,`salt`,`name`,`mail`,`mobile_phone`,`remarks`,`status`)
values (1339613299839381505,0,NULL,'admin','2020-12-18 00:47:54','admin','2021-01-02 15:56:04','admin2','$2a$10$WNiWpSGX64Z3toh8z1Ba9OVGXq9jLb.3PRb9UXts0L1oTXXUv44sC',NULL,'测试235','','18081020301',NULL,'0');


insert  into `t_sys_dict`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`code`,`name`,`remarks`) values (9000000,1,NULL,'2020-12-30 13:57:19',NULL,'sys_flag','技术等级',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (9000001,1,NULL,'2020-12-30 13:57:19',NULL,9000000,'1','普通',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (9000002,1,NULL,'2020-12-30 13:57:19',NULL,9000000,'2','高级',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (9000003,1,NULL,'2020-12-30 13:57:19',NULL,9000000,'3','面向产品编程',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (9000004,1,NULL,'2020-12-30 13:57:19',NULL,9000000,'4','面向百度编程',NULL);
insert  into `t_sys_dict_item`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`dict_id`,`value`,`label`,`remarks`) values (9000005,1,NULL,'2020-12-30 13:57:19',NULL,9000000,'5','看脸',NULL);

insert  into `t_sys_user`(`id`, `sys_flag`, `del_flag`, `create_by`, `create_at`, `update_by`, `update_at`, `username`, `password`, `salt`, `name`, `mail`, `mobile_phone`, `remarks`, `status`)
values (2000, 1, NULL, NULL, NOW(), NULL, NULL, 'ops', '$2a$10$WNiWpSGX64Z3toh8z1Ba9OVGXq9jLb.3PRb9UXts0L1oTXXUv44sC', NULL, '集成测试账号', 'dev@power4j.com', '18088888888', '祝你好运', '0');

insert  into `t_api_token`(`id`, `token`, `uuid`, `username`, `expire_in`, `create_at`, `update_at`)
values (1, '1234567890abcdef', '2000', 'ops', '2030-01-13 17:20:33', '2020-11-30 15:25:06', NULL);

insert  into `t_sys_job`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `group_name`, `cron`, `task_bean`, `param`, `short_description`, `status`, `mis_fire_policy`, `fail_recover`, `create_by`, `update_by`, `error_retry`)
values (1352319388749946882, 1, NULL, '2021-01-22 02:17:22', '2021-01-26 01:52:14', '测试', '0 2/10 * * * ? *', 'demoTask', 'abc123', '演示任务', '0', '0', 1, 'admin', 'admin', 0001);

SET FOREIGN_KEY_CHECKS = 1;