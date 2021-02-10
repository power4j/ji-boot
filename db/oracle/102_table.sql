--------------------------------------------------------
--  文件已创建 - 星期五-二月-12-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table QRTZ_BLOB_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_BLOB_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"BLOB_DATA" BLOB
   )
--------------------------------------------------------
--  DDL for Table QRTZ_CALENDARS
--------------------------------------------------------

  CREATE TABLE "QRTZ_CALENDARS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"CALENDAR_NAME" VARCHAR2(190), 
	"CALENDAR" BLOB
   )
--------------------------------------------------------
--  DDL for Table QRTZ_CRON_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_CRON_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"CRON_EXPRESSION" VARCHAR2(120), 
	"TIME_ZONE_ID" VARCHAR2(80)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_FIRED_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_FIRED_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"ENTRY_ID" VARCHAR2(95), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"INSTANCE_NAME" VARCHAR2(190), 
	"FIRED_TIME" NUMBER(*,0), 
	"SCHED_TIME" NUMBER(*,0), 
	"PRIORITY" NUMBER(*,0), 
	"STATE" VARCHAR2(16), 
	"JOB_NAME" VARCHAR2(190), 
	"JOB_GROUP" VARCHAR2(190), 
	"IS_NONCONCURRENT" CHAR(1), 
	"REQUESTS_RECOVERY" CHAR(1)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_JOB_DETAILS
--------------------------------------------------------

  CREATE TABLE "QRTZ_JOB_DETAILS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"JOB_NAME" VARCHAR2(190), 
	"JOB_GROUP" VARCHAR2(190), 
	"DESCRIPTION" VARCHAR2(250), 
	"JOB_CLASS_NAME" VARCHAR2(250), 
	"IS_DURABLE" CHAR(1), 
	"IS_NONCONCURRENT" CHAR(1), 
	"IS_UPDATE_DATA" CHAR(1), 
	"REQUESTS_RECOVERY" CHAR(1), 
	"JOB_DATA" BLOB
   )
--------------------------------------------------------
--  DDL for Table QRTZ_LOCKS
--------------------------------------------------------

  CREATE TABLE "QRTZ_LOCKS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"LOCK_NAME" VARCHAR2(40)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_PAUSED_TRIGGER_GRPS
--------------------------------------------------------

  CREATE TABLE "QRTZ_PAUSED_TRIGGER_GRPS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_GROUP" VARCHAR2(190)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_SCHEDULER_STATE
--------------------------------------------------------

  CREATE TABLE "QRTZ_SCHEDULER_STATE" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"INSTANCE_NAME" VARCHAR2(190), 
	"LAST_CHECKIN_TIME" NUMBER(*,0), 
	"CHECKIN_INTERVAL" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_SIMPLE_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_SIMPLE_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"REPEAT_COUNT" NUMBER(*,0), 
	"REPEAT_INTERVAL" NUMBER(*,0), 
	"TIMES_TRIGGERED" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_SIMPROP_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_SIMPROP_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"STR_PROP_1" VARCHAR2(512), 
	"STR_PROP_2" VARCHAR2(512), 
	"STR_PROP_3" VARCHAR2(512), 
	"INT_PROP_1" NUMBER(*,0), 
	"INT_PROP_2" NUMBER(*,0), 
	"LONG_PROP_1" NUMBER(*,0), 
	"LONG_PROP_2" NUMBER(*,0), 
	"DEC_PROP_1" NUMBER(13,4), 
	"DEC_PROP_2" NUMBER(13,4), 
	"BOOL_PROP_1" CHAR(1), 
	"BOOL_PROP_2" CHAR(1)
   )
--------------------------------------------------------
--  DDL for Table QRTZ_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120), 
	"TRIGGER_NAME" VARCHAR2(190), 
	"TRIGGER_GROUP" VARCHAR2(190), 
	"JOB_NAME" VARCHAR2(190), 
	"JOB_GROUP" VARCHAR2(190), 
	"DESCRIPTION" VARCHAR2(250), 
	"NEXT_FIRE_TIME" NUMBER(*,0), 
	"PREV_FIRE_TIME" NUMBER(*,0), 
	"PRIORITY" NUMBER(*,0), 
	"TRIGGER_STATE" VARCHAR2(16), 
	"TRIGGER_TYPE" VARCHAR2(8), 
	"START_TIME" NUMBER(*,0), 
	"END_TIME" NUMBER(*,0), 
	"CALENDAR_NAME" VARCHAR2(190), 
	"MISFIRE_INSTR" NUMBER(*,0), 
	"JOB_DATA" BLOB
   )
--------------------------------------------------------
--  DDL for Table T_API_TOKEN
--------------------------------------------------------

  CREATE TABLE "T_API_TOKEN" 
   (	"ID" NUMBER(*,0), 
	"TOKEN" VARCHAR2(40), 
	"UUID" VARCHAR2(20), 
	"USERNAME" VARCHAR2(200), 
	"EXPIRE_IN" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE
   )
--------------------------------------------------------
--  DDL for Table T_RESOURCE_NODE
--------------------------------------------------------

  CREATE TABLE "T_RESOURCE_NODE" 
   (	"ANCESTOR" NUMBER(*,0), 
	"DESCENDANT" NUMBER(*,0), 
	"DISTANCE" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_DICT
--------------------------------------------------------

  CREATE TABLE "T_SYS_DICT" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"CODE" VARCHAR2(40), 
	"NAME" VARCHAR2(40), 
	"REMARKS" VARCHAR2(20)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_DICT_ITEM
--------------------------------------------------------

  CREATE TABLE "T_SYS_DICT_ITEM" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"DICT_ID" NUMBER(*,0), 
	"VALUE" VARCHAR2(255), 
	"LABEL" VARCHAR2(255), 
	"REMARKS" VARCHAR2(40)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_JOB
--------------------------------------------------------

  CREATE TABLE "T_SYS_JOB" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"GROUP_NAME" VARCHAR2(20), 
	"CRON" VARCHAR2(40), 
	"TASK_BEAN" VARCHAR2(255), 
	"PARAM" VARCHAR2(255), 
	"SHORT_DESCRIPTION" VARCHAR2(20), 
	"STATUS" CHAR(1), 
	"MIS_FIRE_POLICY" CHAR(1), 
	"FAIL_RECOVER" NUMBER(*,0), 
	"CREATE_BY" VARCHAR2(20), 
	"UPDATE_BY" VARCHAR2(20), 
	"ERROR_RETRY" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_JOB_LOG
--------------------------------------------------------

  CREATE TABLE "T_SYS_JOB_LOG" 
   (	"ID" NUMBER(*,0), 
	"EXECUTION_ID" VARCHAR2(40), 
	"FIRE_BY" VARCHAR2(100), 
	"JOB_ID" NUMBER(*,0), 
	"GROUP_NAME" VARCHAR2(20), 
	"TASK_BEAN" VARCHAR2(255), 
	"SHORT_DESCRIPTION" VARCHAR2(20), 
	"START_TIME" DATE, 
	"END_TIME" DATE, 
	"EXECUTE_MS" NUMBER(*,0), 
	"SUCCESS" NUMBER(*,0), 
	"EX" VARCHAR2(255), 
	"EX_MSG" VARCHAR2(255)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_PARAM
--------------------------------------------------------

  CREATE TABLE "T_SYS_PARAM" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"PARAM_KEY" VARCHAR2(255), 
	"PARAM_VALUE" CLOB, 
	"REMARKS" VARCHAR2(20), 
	"STATUS" CHAR(1), 
	"CREATE_BY" VARCHAR2(20), 
	"UPDATE_BY" VARCHAR2(20)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_RESOURCE
--------------------------------------------------------

  CREATE TABLE "T_SYS_RESOURCE" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"TYPE" NUMBER(*,0), 
	"NAME" VARCHAR2(255), 
	"TITLE" VARCHAR2(20), 
	"PERMISSION" VARCHAR2(255), 
	"PATH" VARCHAR2(255), 
	"COMPONENT" VARCHAR2(255), 
	"ICON" VARCHAR2(40), 
	"SORT" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_RESOURCE_GRANTEE
--------------------------------------------------------

  CREATE TABLE "T_SYS_RESOURCE_GRANTEE" 
   (	"ROLE_ID" NUMBER(*,0), 
	"RESOURCE_ID" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_ROLE
--------------------------------------------------------

  CREATE TABLE "T_SYS_ROLE" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"CODE" VARCHAR2(20), 
	"NAME" VARCHAR2(20), 
	"REMARKS" VARCHAR2(20), 
	"STATUS" CHAR(1), 
	"OWNER" NUMBER(*,0)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_ROLE_GRANTEE
--------------------------------------------------------

  CREATE TABLE "T_SYS_ROLE_GRANTEE" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_AT" DATE, 
	"UPDATE_AT" DATE, 
	"USER_ID" NUMBER(*,0), 
	"ROLE_ID" NUMBER(*,0), 
	"GRANT_TYPE" CHAR(1)
   )
--------------------------------------------------------
--  DDL for Table T_SYS_USER
--------------------------------------------------------

  CREATE TABLE "T_SYS_USER" 
   (	"ID" NUMBER(*,0), 
	"SYS_FLAG" NUMBER(*,0), 
	"DEL_FLAG" DATE, 
	"CREATE_BY" VARCHAR2(20), 
	"CREATE_AT" DATE, 
	"UPDATE_BY" VARCHAR2(20), 
	"UPDATE_AT" DATE, 
	"USERNAME" VARCHAR2(20), 
	"PASSWORD" VARCHAR2(200), 
	"SALT" VARCHAR2(20), 
	"NAME" VARCHAR2(20), 
	"MAIL" VARCHAR2(40), 
	"MOBILE_PHONE" VARCHAR2(16), 
	"REMARKS" VARCHAR2(20), 
	"STATUS" CHAR(1)
   )
