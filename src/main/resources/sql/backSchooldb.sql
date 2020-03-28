/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/3/28 16:36:06                           */
/*==============================================================*/


drop table if exists activity;

drop table if exists activity_manager;

drop table if exists apply;

drop table if exists city;

drop table if exists county;

drop table if exists feedback;

drop table if exists province;

drop table if exists student;

drop table if exists user;

/*==============================================================*/
/* Table: activity                                              */
/*==============================================================*/
create table activity
(
   activity_id          int not null auto_increment comment '活动表id',
   activity_name        varchar(64) not null comment '活动名称',
   apply_start_time     datetime not null comment '活动开始时间',
   apply_end_time       datetime not null comment '活动结束时间',
   feedback_start_time  datetime not null comment '反馈开始时间',
   feedback_end_time    datetime not null comment '反馈结束时间',
   creator              varchar(16) not null comment '活动创建者',
   activity_create_time datetime not null comment '活动创建时间',
   content              longtext not null comment '活动内容',
   activity_file_path   varchar(256) comment '活动的材料文件路径',
   location             varchar(64) not null,
   need_examine         tinyint not null default 0 comment '是否需要审核',
   primary key (activity_id),
   unique key AK_Key_2 (activity_name)
)
charset = UTF8;

alter table activity comment '活动表';

/*==============================================================*/
/* Table: activity_manager                                      */
/*==============================================================*/
create table activity_manager
(
   activity_manager_id  int not null auto_increment comment '活动管理表id',
   activity_id          int not null comment '活动id',
   user_id              varchar(32) not null comment '用户id',
   primary key (activity_manager_id),
   unique key AK_Key_2 (activity_id, user_id)
)
charset = UTF8;

/*==============================================================*/
/* Table: apply                                                 */
/*==============================================================*/
create table apply
(
   apply_id             int not null auto_increment comment '申请表id',
   apply_create_time    datetime not null comment '申请时间',
   apply_status         tinyint not null default 0 comment '申请状态，0未审核，1通过，2不通过',
   description          text not null comment '个人简介',
   activity_id          int not null,
   student_id           varchar(32) not null comment '学生的学号',
   primary key (apply_id)
)
charset = UTF8;

alter table apply comment '报名申请表';

/*==============================================================*/
/* Table: city                                                  */
/*==============================================================*/
create table city
(
   city_id              int not null auto_increment comment '地级行政厅id',
   city_name            varchar(32) not null,
   province_id          int not null comment '省行政厅id，外键',
   primary key (city_id)
)
charset = UTF8;

alter table city comment '地级行政区表';

/*==============================================================*/
/* Table: county                                                */
/*==============================================================*/
create table county
(
   county_id            int not null auto_increment comment '县级行政厅id',
   county_name          varchar(32) not null comment '县级行政厅名称',
   city_id              int not null comment '地级行政厅id',
   primary key (county_id)
)
charset = UTF8;

alter table county comment '县级行政区表';

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   feedback_id          int not null auto_increment comment '反馈表id',
   apply_id             int not null comment '申请表id',
   level                tinyint not null default 3 comment '评价等级，0表示不合格,1表示合格，2表示优秀，3表示未处理',
   feedback_file_path   varchar(256) not null comment '反馈文件路径',
   primary key (feedback_id),
   unique key AK_Key_2 (apply_id)
)
charset = UTF8;

alter table feedback comment '反馈表';

/*==============================================================*/
/* Table: province                                              */
/*==============================================================*/
create table province
(
   province_id          int not null auto_increment comment '省行政厅id',
   province_name        varchar(32) not null comment '省行政厅名称',
   primary key (province_id)
)
charset = UTF8;

alter table province comment '省行政厅表';

/*==============================================================*/
/* Table: student                                               */
/*==============================================================*/
create table student
(
   name                 varchar(32) not null comment '学生姓名',
   gender               varchar(2) not null comment '学生性别,0表女，1表男',
   college              varchar(32) not null comment '学院',
   class                varchar(32) not null comment '班级',
   idCard               varchar(32) comment '身份证',
   qq                   varchar(32),
   bank_card            varchar(32) comment '银行卡号',
   phone                varchar(11),
   email                varchar(320),
   origin               varchar(64) comment '学生生源地',
   high_school          varchar(32) comment '毕业高中',
   student_id           varchar(32) not null comment '学生的学号',
   primary key (student_id)
)
charset = UTF8;

alter table student comment '学生表';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              varchar(32) not null comment '用户id',
   password             varchar(16) not null comment '密码',
   role                 tinyint not null default 2 comment '用户角色',
   group_loc            varchar(32) character set utf8 comment '管理地区',
   primary key (user_id)
)
charset = UTF8;

alter table user comment '用户账号信息表';

alter table activity_manager add constraint FK_Reference_11 foreign key (activity_id)
      references activity (activity_id) on delete restrict on update restrict;

alter table activity_manager add constraint FK_Reference_12 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table apply add constraint FK_Reference_2 foreign key (activity_id)
      references activity (activity_id) on delete restrict on update restrict;

alter table apply add constraint FK_Reference_3 foreign key (student_id)
      references student (student_id) on delete restrict on update restrict;

alter table city add constraint FK_Reference_8 foreign key (province_id)
      references province (province_id) on delete restrict on update restrict;

alter table county add constraint FK_Reference_9 foreign key (city_id)
      references city (city_id) on delete restrict on update restrict;

alter table feedback add constraint FK_feedback_apply foreign key (apply_id)
      references apply (apply_id) on delete restrict on update restrict;

