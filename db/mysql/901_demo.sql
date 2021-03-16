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

insert  into `t_schedule_job`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `group_name`, `cron`, `task_bean`, `param`, `short_description`, `status`, `mis_fire_policy`, `fail_recover`, `create_by`, `update_by`, `error_retry`)
values (1352319388749946882, 1, NULL, '2021-01-22 02:17:22', '2021-01-26 01:52:14', '测试', '0 1/20 * * * ? *', 'demoTask', 'abc123', '演示任务', '0', '0', 1, 'admin', 'admin', 0001);

insert  into `t_sys_param`(`id`,`sys_flag`,`del_flag`,`create_at`,`update_at`,`param_key`,`param_value`,`remarks`,`status`,`create_by`,`update_by`) values (1336568657526984706,1,NULL,'2020-12-09 15:09:35',NULL,'test','sfsdfsf','sdfdf','0','admin',NULL);

insert  into `t_ureport_data`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `file`, `data`)
values (1360264073216811010, 1, NULL, '2021-02-13 00:26:42', '2021-02-25 19:57:04', 'ji-boot_任务耗时统计.ureport.xml', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E3C757265706F72743E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241312220726F773D22312220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231322220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE4BBBBE58AA14265616E5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242312220726F773D22312220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231322220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE8B083E5BAA649445D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243312220726F773D22312220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231322220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE5BC80E5A78BE697B6E997B45D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244312220726F773D22312220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231322220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE5BC82E5B8B85D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2245312220726F773D22312220636F6C3D2235223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231322220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE88097E697B628E6AFABE7A792295D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2241322220726F773D22322220636F6C3D22312220726F772D7370616E3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D226A6F625F6C6F6722206167677265676174653D2267726F7570222070726F70657274793D227461736B5F6265616E22206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2242322220726F773D22322220636F6C3D22322220726F772D7370616E3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D226A6F625F6C6F6722206167677265676174653D2267726F7570222070726F70657274793D22657865637574696F6E5F696422206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2243322220726F773D22322220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D226A6F625F6C6F6722206167677265676174653D2273656C656374222070726F70657274793D2273746172745F74696D6522206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2244322220726F773D22322220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D226A6F625F6C6F6722206167677265676174653D2273656C656374222070726F70657274793D2265785F6D736722206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2245322220726F773D22322220636F6C3D2235223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D226A6F625F6C6F6722206167677265676174653D2273656C656374222070726F70657274793D22657865637574655F6D7322206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243332220726F773D22332220636F6C3D22332220636F6C2D7370616E3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D22313022206267636F6C6F723D223235322C3233362C38382220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C65787072657373696F6E2D76616C75653E3C215B43444154415B27E5B08FE8AEA13A20272B73756D284532292B2720E6AFABE7A792275D5D3E3C2F65787072657373696F6E2D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242342220726F773D22342220636F6C3D22322220636F6C2D7370616E3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D22313022206267636F6C6F723D223235322C38382C3234362220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C65787072657373696F6E2D76616C75653E3C215B43444154415B73756D284532295D5D3E3C2F65787072657373696F6E2D76616C75653E3C2F63656C6C3E3C726F7720726F772D6E756D6265723D223122206865696768743D223139222062616E643D227469746C65222F3E3C726F7720726F772D6E756D6265723D223222206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223322206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223422206865696768743D223138222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2231222077696474683D22313130222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2232222077696474683D22313334222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2233222077696474683D22313334222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2234222077696474683D22313239222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2235222077696474683D22313039222F3E3C64617461736F75726365206E616D653D2278782220747970653D226A6462632220757365726E616D653D22726F6F74222070617373776F72643D22726F6F74222075726C3D226A6462633A6D7973716C3A2F2F3A31302E31312E31322E383A353330362F6A695F626F6F7422206472697665723D22636F6D2E6D7973716C2E636A2E6A6462632E447269766572223E3C2F64617461736F757263653E3C64617461736F75726365206E616D653D2244656661756C742220747970653D226275696C64696E223E3C64617461736574206E616D653D226A6F625F6C6F672220747970653D2273716C223E3C73716C3E3C215B43444154415B73656C656374202A2066726F6D20745F7379735F6A6F625F6C6F675D5D3E3C2F73716C3E3C6669656C64206E616D653D226964222F3E3C6669656C64206E616D653D22657865637574696F6E5F6964222F3E3C6669656C64206E616D653D22666972655F6279222F3E3C6669656C64206E616D653D226A6F625F6964222F3E3C6669656C64206E616D653D2267726F75705F6E616D65222F3E3C6669656C64206E616D653D227461736B5F6265616E222F3E3C6669656C64206E616D653D2273686F72745F6465736372697074696F6E222F3E3C6669656C64206E616D653D2273746172745F74696D65222F3E3C6669656C64206E616D653D22656E645F74696D65222F3E3C6669656C64206E616D653D22657865637574655F6D73222F3E3C6669656C64206E616D653D2273756363657373222F3E3C6669656C64206E616D653D226578222F3E3C6669656C64206E616D653D2265785F6D7367222F3E3C2F646174617365743E3C2F64617461736F757263653E3C706170657220747970653D22413422206C6566742D6D617267696E3D223930222072696768742D6D617267696E3D223930220A20202020746F702D6D617267696E3D2237322220626F74746F6D2D6D617267696E3D2237322220706167696E672D6D6F64653D22666974706167652220666978726F77733D2230220A2020202077696474683D2235393522206865696768743D2238343222206F7269656E746174696F6E3D22706F727472616974222068746D6C2D7265706F72742D616C69676E3D226C656674222062672D696D6167653D22222068746D6C2D696E74657276616C2D726566726573682D76616C75653D22302220636F6C756D6E2D656E61626C65643D2266616C7365223E3C2F70617065723E3C7365617263682D666F726D20666F726D2D706F736974696F6E3D227570222F3E3C2F757265706F72743E);
insert  into `t_ureport_data`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `file`, `data`)
values (1364920029859098626, 1, NULL, '2021-02-25 20:47:49', NULL, 'ji-boot_菜单统计.ureport.xml', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E3C757265706F72743E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241312220726F773D22312220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231312220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE88F9CE58D95E7B1BBE59E8B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242312220726F773D22312220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231312220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE5908DE7A7B05D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243312220726F773D22312220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231312220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE5B195E7A4BAE5908DE7A7B05D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244312220726F773D22312220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231312220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220626F6C643D22747275652220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415BE8B7AFE5BE845D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2241322220726F773D22322220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D22E88F9CE58D9522206167677265676174653D2267726F7570222070726F70657274793D227479706522206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2242322220726F773D22322220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D22E88F9CE58D9522206167677265676174653D2273656C656374222070726F70657274793D226E616D6522206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2243322220726F773D22322220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D22E88F9CE58D9522206167677265676174653D2273656C656374222070726F70657274793D227469746C6522206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D22446F776E22206E616D653D2244322220726F773D22322220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C6C6566742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C72696768742D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C746F702D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C626F74746F6D2D626F726465722077696474683D223122207374796C653D22736F6C69642220636F6C6F723D22302C302C30222F3E3C2F63656C6C2D7374796C653E3C646174617365742D76616C756520646174617365742D6E616D653D22E88F9CE58D9522206167677265676174653D2267726F7570222070726F70657274793D227061746822206F726465723D226E6F6E6522206D617070696E672D747970653D2273696D706C65223E3C2F646174617365742D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241332220726F773D22332220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242332220726F773D22332220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243332220726F773D22332220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244332220726F773D22332220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241342220726F773D22342220636F6C3D2231223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2242342220726F773D22342220636F6C3D2232223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2243342220726F773D22342220636F6C3D2233223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2244342220726F773D22342220636F6C3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C726F7720726F772D6E756D6265723D223122206865696768743D223139222062616E643D227469746C65222F3E3C726F7720726F772D6E756D6265723D223222206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223322206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223422206865696768743D223138222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2231222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2232222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2233222077696474683D223830222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2234222077696474683D223830222F3E3C64617461736F75726365206E616D653D2244656661756C742220747970653D226275696C64696E223E3C64617461736574206E616D653D22E88F9CE58D952220747970653D2273716C223E3C73716C3E3C215B43444154415B73656C656374202A2066726F6D20745F7379735F7265736F757263655D5D3E3C2F73716C3E3C6669656C64206E616D653D226964222F3E3C6669656C64206E616D653D227379735F666C6167222F3E3C6669656C64206E616D653D2264656C5F666C6167222F3E3C6669656C64206E616D653D226372656174655F6174222F3E3C6669656C64206E616D653D227570646174655F6174222F3E3C6669656C64206E616D653D2274797065222F3E3C6669656C64206E616D653D226E616D65222F3E3C6669656C64206E616D653D227469746C65222F3E3C6669656C64206E616D653D227065726D697373696F6E222F3E3C6669656C64206E616D653D2270617468222F3E3C6669656C64206E616D653D22636F6D706F6E656E74222F3E3C6669656C64206E616D653D2269636F6E222F3E3C6669656C64206E616D653D22736F7274222F3E3C2F646174617365743E3C2F64617461736F757263653E3C706170657220747970653D22413422206C6566742D6D617267696E3D223930222072696768742D6D617267696E3D223930220A20202020746F702D6D617267696E3D2237322220626F74746F6D2D6D617267696E3D2237322220706167696E672D6D6F64653D22666974706167652220666978726F77733D2230220A2020202077696474683D2235393522206865696768743D2238343222206F7269656E746174696F6E3D22706F727472616974222068746D6C2D7265706F72742D616C69676E3D226C656674222062672D696D6167653D22222068746D6C2D696E74657276616C2D726566726573682D76616C75653D22302220636F6C756D6E2D656E61626C65643D2266616C7365223E3C2F70617065723E3C2F757265706F72743E);
insert  into `t_ureport_data`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `file`, `data`)
values (1364949864417452034, 1, NULL, '2021-02-25 22:46:22', NULL, 'ji-boot_任务延迟折线图.ureport.xml', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E3C757265706F72743E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241312220726F773D22312220636F6C3D22312220636F6C2D7370616E3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241322220726F773D22322220636F6C3D22312220726F772D7370616E3D22332220636F6C2D7370616E3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C63686172742D76616C75653E3C6461746173657420646174617365742D6E616D653D226A6F626C6F672220747970653D226C696E65222063617465676F72792D70726F70657274793D2273746172745F74696D6522207365726965732D70726F70657274793D227461736B5F6265616E22207365726965732D747970653D2270726F7065727479222076616C75652D70726F70657274793D22657865637574655F6D732220636F6C6C6563742D747970653D2273656C656374222F3E3C6F7074696F6E20747970653D227469746C652220706F736974696F6E3D22746F702220646973706C61793D22747275652220746578743D22E4BBBBE58AA1E5BBB6E8BF9F222F3E3C706C7567696E206E616D653D22646174612D6C6162656C732220646973706C61793D2274727565222F3E3C2F63686172742D76616C75653E3C2F63656C6C3E3C726F7720726F772D6E756D6265723D223122206865696768743D223139222F3E3C726F7720726F772D6E756D6265723D223222206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223322206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223422206865696768743D22333138222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2231222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2232222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2233222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2234222077696474683D22353430222F3E3C64617461736F75726365206E616D653D2244656661756C742220747970653D226275696C64696E223E3C64617461736574206E616D653D226A6F626C6F672220747970653D2273716C223E3C73716C3E3C215B43444154415B73656C656374202A2066726F6D20745F7379735F6A6F625F6C6F675D5D3E3C2F73716C3E3C6669656C64206E616D653D226964222F3E3C6669656C64206E616D653D22657865637574696F6E5F6964222F3E3C6669656C64206E616D653D22666972655F6279222F3E3C6669656C64206E616D653D226A6F625F6964222F3E3C6669656C64206E616D653D2267726F75705F6E616D65222F3E3C6669656C64206E616D653D227461736B5F6265616E222F3E3C6669656C64206E616D653D2273686F72745F6465736372697074696F6E222F3E3C6669656C64206E616D653D2273746172745F74696D65222F3E3C6669656C64206E616D653D22656E645F74696D65222F3E3C6669656C64206E616D653D22657865637574655F6D73222F3E3C6669656C64206E616D653D2273756363657373222F3E3C6669656C64206E616D653D226578222F3E3C6669656C64206E616D653D2265785F6D7367222F3E3C2F646174617365743E3C2F64617461736F757263653E3C706170657220747970653D22413422206C6566742D6D617267696E3D223930222072696768742D6D617267696E3D223930220A20202020746F702D6D617267696E3D2237322220626F74746F6D2D6D617267696E3D2237322220706167696E672D6D6F64653D22666974706167652220666978726F77733D2230220A2020202077696474683D2235393522206865696768743D2238343222206F7269656E746174696F6E3D22706F727472616974222068746D6C2D7265706F72742D616C69676E3D226C656674222062672D696D6167653D22222068746D6C2D696E74657276616C2D726566726573682D76616C75653D22302220636F6C756D6E2D656E61626C65643D2266616C7365223E3C2F70617065723E3C2F757265706F72743E);
insert  into `t_ureport_data`(`id`, `sys_flag`, `del_flag`, `create_at`, `update_at`, `file`, `data`)
values (1364950541571055617, 1, NULL, '2021-02-25 22:49:03', '2021-02-25 22:50:57', 'ji-boot_任务耗时柱状图.ureport.xml', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E3C757265706F72743E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241312220726F773D22312220636F6C3D22312220636F6C2D7370616E3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D22392220666F7265636F6C6F723D22302C302C302220666F6E742D66616D696C793D22E5AE8BE4BD932220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C73696D706C652D76616C75653E3C215B43444154415B5D5D3E3C2F73696D706C652D76616C75653E3C2F63656C6C3E3C63656C6C20657870616E643D224E6F6E6522206E616D653D2241322220726F773D22322220636F6C3D22312220726F772D7370616E3D22332220636F6C2D7370616E3D2234223E3C63656C6C2D7374796C6520666F6E742D73697A653D2231302220616C69676E3D2263656E746572222076616C69676E3D226D6964646C65223E3C2F63656C6C2D7374796C653E3C63686172742D76616C75653E3C6461746173657420646174617365742D6E616D653D226A6F626C6F672220747970653D22686F72697A6F6E74616C426172222063617465676F72792D70726F70657274793D22657865637574696F6E5F696422207365726965732D70726F70657274793D2273686F72745F6465736372697074696F6E22207365726965732D747970653D2270726F7065727479222076616C75652D70726F70657274793D22657865637574655F6D732220636F6C6C6563742D747970653D2273656C656374222F3E3C78617865733E3C7363616C652D6C6162656C20646973706C61793D227472756522206C6162656C2D737472696E673D22E88097E697B628E6AFABE7A79229222F3E3C2F78617865733E3C79617865733E3C7363616C652D6C6162656C20646973706C61793D227472756522206C6162656C2D737472696E673D22E8B083E5BAA64944222F3E3C2F79617865733E3C6F7074696F6E20747970653D227469746C652220706F736974696F6E3D22746F702220646973706C61793D22747275652220746578743D22E4BBBBE58AA1E88097E697B6E69FB1E78AB6E59BBE222F3E3C706C7567696E206E616D653D22646174612D6C6162656C732220646973706C61793D2274727565222F3E3C2F63686172742D76616C75653E3C2F63656C6C3E3C726F7720726F772D6E756D6265723D223122206865696768743D223139222F3E3C726F7720726F772D6E756D6265723D223222206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223322206865696768743D223138222F3E3C726F7720726F772D6E756D6265723D223422206865696768743D22333137222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2231222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2232222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2233222077696474683D22313033222F3E3C636F6C756D6E20636F6C2D6E756D6265723D2234222077696474683D22353339222F3E3C64617461736F75726365206E616D653D2244656661756C742220747970653D226275696C64696E223E3C64617461736574206E616D653D226A6F626C6F672220747970653D2273716C223E3C73716C3E3C215B43444154415B73656C656374202A2066726F6D20745F7379735F6A6F625F6C6F675D5D3E3C2F73716C3E3C6669656C64206E616D653D226964222F3E3C6669656C64206E616D653D22657865637574696F6E5F6964222F3E3C6669656C64206E616D653D22666972655F6279222F3E3C6669656C64206E616D653D226A6F625F6964222F3E3C6669656C64206E616D653D2267726F75705F6E616D65222F3E3C6669656C64206E616D653D227461736B5F6265616E222F3E3C6669656C64206E616D653D2273686F72745F6465736372697074696F6E222F3E3C6669656C64206E616D653D2273746172745F74696D65222F3E3C6669656C64206E616D653D22656E645F74696D65222F3E3C6669656C64206E616D653D22657865637574655F6D73222F3E3C6669656C64206E616D653D2273756363657373222F3E3C6669656C64206E616D653D226578222F3E3C6669656C64206E616D653D2265785F6D7367222F3E3C2F646174617365743E3C2F64617461736F757263653E3C706170657220747970653D22413422206C6566742D6D617267696E3D223930222072696768742D6D617267696E3D223930220A20202020746F702D6D617267696E3D2237322220626F74746F6D2D6D617267696E3D2237322220706167696E672D6D6F64653D22666974706167652220666978726F77733D2230220A2020202077696474683D2235393522206865696768743D2238343222206F7269656E746174696F6E3D22706F727472616974222068746D6C2D7265706F72742D616C69676E3D226C656674222062672D696D6167653D22222068746D6C2D696E74657276616C2D726566726573682D76616C75653D22302220636F6C756D6E2D656E61626C65643D2266616C7365223E3C2F70617065723E3C2F757265706F72743E);

SET FOREIGN_KEY_CHECKS = 1;