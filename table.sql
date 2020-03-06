#创建用户表
DROP TABLE if exists `user`;

create table `user`(
    `user_id` int(11) unsigned not null AUTO_INCREMENT comment '用户ID',
    `user_name` varchar(50) default null comment '用户名',
    `password` varchar(50) default null comment '密码',
    `register_time` datetime DEFAULT null comment '注册时间',
    `image_path` varchar(255) default '' comment '头像地址',
    primary key (`user_id`)
)ENGINE =InnoDB charset = utf8;

#创建 文件仓库表
DROP table if exists `file_store`;
create table `file_store`(
    `file_store_id` int(11) unsigned not null AUTO_INCREMENT comment '文件仓库ID',
    `max_size` int(11) default '2097152' comment '最大容量(单位KB)',
    `current_size` int(11) default '0' comment '当前容量(单位KB)',
    `user_id` int(11) unsigned not null  comment '主人ID',
    primary key (`file_store_id`),
    foreign key (`user_id`) references user(`user_id`) on delete cascade
)ENGINE = InnoDB charset = utf8;

#创建文件夹表
drop table if exists `file_folder`;
create table `file_folder`(
    `file_folder_id` int(11) unsigned not null AUTO_INCREMENT comment '文件夹ID',
    `file_folder_name` varchar(255) default null comment '文件夹名称',
    `time` datetime default null comment '创建时间',
    `parent_folder_id` int(11) default '0' comment '父文件夹ID',
    `file_store_id` int(11) unsigned not null comment '所属仓库ID',
    primary key (`file_folder_id`),
    foreign key (`file_store_id`) references file_store(`file_store_id`) on delete cascade
)ENGINE = InnoDB charset = utf8;

#创建文件表
drop table if exists `my_file`;
create table `my_file`(
    `my_file_id` int(11) unsigned not null AUTO_INCREMENT COMMENT '文件ID',
    `my_file_name` varchar(255) default  null comment '文件名',
    `download_time` int(11) default '0' comment '下载次数',
    `upload_time` datetime DEFAULT  null comment '上传时间',
    `my_file_path` varchar (255) default '/' commit '文件存放路径',
    `size` int(11) default null comment '文件大小',
    `type` int(11) default null comment '文件类型',
    `postfix` varchar(255) default null comment '文件后缀',
    `file_folder_id` int(11) unsigned not null comment '所属文件夹',
    primary key (`my_file_id`),
    foreign key (`file_folder_id`) references file_folder(`file_folder_id`) on delete cascade
)ENGINE = InnoDB charset = utf8;
