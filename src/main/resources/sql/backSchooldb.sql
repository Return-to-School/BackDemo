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
   activity_id          int not null auto_increment comment '���id',
   activity_name        varchar(64) not null comment '�����',
   apply_start_time     datetime not null comment '���ʼʱ��',
   apply_end_time       datetime not null comment '�����ʱ��',
   feedback_start_time  datetime not null comment '������ʼʱ��',
   feedback_end_time    datetime not null comment '��������ʱ��',
   creator              varchar(16) not null comment '�������',
   activity_create_time datetime not null comment '�����ʱ��',
   content              longtext not null comment '�����',
   activity_file_path   varchar(256) comment '��Ĳ����ļ�·��',
   location             varchar(64) not null,
   need_examine         tinyint not null default 0 comment '�Ƿ���Ҫ���',
   primary key (activity_id),
   unique key AK_Key_2 (activity_name)
)
charset = UTF8;

alter table activity comment '���';

/*==============================================================*/
/* Table: activity_manager                                      */
/*==============================================================*/
create table activity_manager
(
   activity_manager_id  int not null auto_increment comment '������id',
   activity_id          int not null comment '�id',
   user_id              varchar(32) not null comment '�û�id',
   primary key (activity_manager_id),
   unique key AK_Key_2 (activity_id, user_id)
)
charset = UTF8;

/*==============================================================*/
/* Table: apply                                                 */
/*==============================================================*/
create table apply
(
   apply_id             int not null auto_increment comment '�����id',
   apply_create_time    datetime not null comment '����ʱ��',
   apply_status         tinyint not null default 0 comment '����״̬��0δ��ˣ�1ͨ����2��ͨ��',
   description          text not null comment '���˼��',
   activity_id          int not null,
   student_id           varchar(32) not null comment 'ѧ����ѧ��',
   primary key (apply_id)
)
charset = UTF8;

alter table apply comment '���������';

/*==============================================================*/
/* Table: city                                                  */
/*==============================================================*/
create table city
(
   city_id              int not null auto_increment comment '�ؼ�������id',
   city_name            varchar(32) not null,
   province_id          int not null comment 'ʡ������id�����',
   primary key (city_id)
)
charset = UTF8;

alter table city comment '�ؼ���������';

/*==============================================================*/
/* Table: county                                                */
/*==============================================================*/
create table county
(
   county_id            int not null auto_increment comment '�ؼ�������id',
   county_name          varchar(32) not null comment '�ؼ�����������',
   city_id              int not null comment '�ؼ�������id',
   primary key (county_id)
)
charset = UTF8;

alter table county comment '�ؼ���������';

/*==============================================================*/
/* Table: feedback                                              */
/*==============================================================*/
create table feedback
(
   feedback_id          int not null auto_increment comment '������id',
   apply_id             int not null comment '�����id',
   level                tinyint not null default 3 comment '���۵ȼ���0��ʾ���ϸ�,1��ʾ�ϸ�2��ʾ���㣬3��ʾδ����',
   feedback_file_path   varchar(256) not null comment '�����ļ�·��',
   primary key (feedback_id),
   unique key AK_Key_2 (apply_id)
)
charset = UTF8;

alter table feedback comment '������';

/*==============================================================*/
/* Table: province                                              */
/*==============================================================*/
create table province
(
   province_id          int not null auto_increment comment 'ʡ������id',
   province_name        varchar(32) not null comment 'ʡ����������',
   primary key (province_id)
)
charset = UTF8;

alter table province comment 'ʡ��������';

/*==============================================================*/
/* Table: student                                               */
/*==============================================================*/
create table student
(
   name                 varchar(32) not null comment 'ѧ������',
   gender               varchar(2) not null comment 'ѧ���Ա�,0��Ů��1����',
   college              varchar(32) not null comment 'ѧԺ',
   class                varchar(32) not null comment '�༶',
   idCard               varchar(32) comment '���֤',
   qq                   varchar(32),
   bank_card            varchar(32) comment '���п���',
   phone                varchar(11),
   email                varchar(320),
   origin               varchar(64) comment 'ѧ����Դ��',
   high_school          varchar(32) comment '��ҵ����',
   student_id           varchar(32) not null comment 'ѧ����ѧ��',
   primary key (student_id)
)
charset = UTF8;

alter table student comment 'ѧ����';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              varchar(32) not null comment '�û�id',
   password             varchar(16) not null comment '����',
   role                 tinyint not null default 2 comment '�û���ɫ',
   group_loc            varchar(32) character set utf8 comment '�������',
   primary key (user_id)
)
charset = UTF8;

alter table user comment '�û��˺���Ϣ��';

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

