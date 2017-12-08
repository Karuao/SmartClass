create database smartclass;
use smartclass;


create table user
(
user_id int primary key auto_increment,
email varchar(32),
password varchar(32),
avatar varchar(32),
name varchar(32),
gender tinyint,
birthday date,
university smallint,
department smallint,
if_show_closed_classes tinyint
)engine=InnoDB default charset=utf8;

create table class
(
class_id int primary key auto_increment,
invite_code int,
university varchar(32),
department varchar(32),
textbook varchar(32),
detail tinytext,
exam_shedule tinytext,
population smallint,
if_open tinyint,
if_allow_to_join tinyint,
user_id int,
create_date_time datetime,
modify_date_time datetime
)engine=InnoDB default charset=utf8;

create table class_user
(
class_user_id int primary key auto_increment,
class_id int,
user_id int,
exp int,
create_date_time datetime,
modify_date_time datetime
)engine=InnoDB default charset=utf8;

create table `material`
(
`material_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`type` tinyint,
`url` varchar(32),
`if_repository` tinyint,
`if_publish` tinyint,
`publish_date_time` datetime,
`detail` tinytext,
`exp` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `survey`
(
`survey_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`if_open` tinyint,
`if_repository` tinyint,
`exp` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `survey_question`
(
`survey_question_id` int primary key auto_increment,
`detail` tinytext,
`answer_type` tinyint,
`survey_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`survey_id`) references `survey`(`survey_id`)
)engine=InnoDB default charset=utf8;

create table `survey_question_option`
(
`survey_question_option_id` int primary key auto_increment,
`detail` tinytext,
`votes` smallint,
`survey_question_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`survey_question_id`) references `survey_question`(`survey_question_id`)
)engine=InnoDB default charset=utf8;


create table `think`
(
`think_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`detail` tinytext,
`exp`  tinytext,
`if_open` tinyint,
`if_repository` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `think_answer`
(
`think_answer_id` int primary key auto_increment,
`detail` text,
`think_id` int,
`user_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`think_id`) references `think`(`think_id`),
foreign key(`user_id`) references `user`(`user_id`)
)engine=InnoDB default charset=utf8;


create table `homework`
(
`homework_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`detail` tinytext,
`if_repository` tinyint,
`exp` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `homework_answer`
(
`homework_answer_id` int primary key auto_increment,
`detail` text,
`url` tinytext,
`homework_id` int,
`user_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`homework_id`) references `homework`(`homework_id`),
foreign key(`user_id`) references `user`(`user_id`)
)engine=InnoDB default charset=utf8;

create table `question`
(
`question_id` int primary key auto_increment,
`detail` tinytext,
`score` int,
`answer_type` tinyint,
`standard_answer` tinytext,
`create_date_time` datetime,
`modify_date_time` datetime
)engine=InnoDB default charset=utf8;

create table `question_option`
(
`question_option_id` int primary key auto_increment,
`detail` tinytext,
`question_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`question_id`) references `question`(`question_id`)
)engine=InnoDB default charset=utf8;

create table `test`
(
`test_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`if_derangement` tinyint,
`deadline` datetime,
`check_type` tinyint,
`exp` int,
`max_score` int,
`if_open` tinyint,
`if_repository` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `test_question`
(
`test_question_id` int primary key auto_increment,
`test_id` int,
`question_id` int,
`create_date_time` datetime,
`modify_date_time` datetime
)engine=InnoDB default charset=utf8;

create table `user_test`
(
`user_test_id` int primary key auto_increment,
`score` int,
`user_id` int,
`test_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`user_id`) references `user`(`user_id`),
foreign key(`test_id`) references `test`(`test_id`)
)engine=InnoDB default charset=utf8;

create table `user_test_question_answer`
(
`user_test_question_answer_id` int primary key auto_increment,
`answer` text,
`if_correct` tinyint,
`score` int,
`user_test_id` int,
`question_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`user_test_id`) references `user_test`(`user_test_id`),
foreign key(`question_id`) references `question`(`question_id`)
)engine=InnoDB default charset=utf8;

create table `chatroom`
(
`chatroom_id` int primary key auto_increment,
`name` varchar(32),
`group` varchar(32),
`detail` tinytext,
`exp` tinyint,
`if_open` tinyint,
`if_repository` tinyint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `chatroom_message`
(
`chatroom_message_id` int primary key auto_increment,
`detail` text,
`user_id` int,
`chatroom_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`chatroom_id`) references `chatroom`(`chatroom_id`),
foreign key(`user_id`) references `user`(`user_id`)
)engine=InnoDB default charset=utf8;

create table `inform`
(
`inform_id` int primary key auto_increment,
`detail` tinytext,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `attendance`
(
`attendance_id` int primary key auto_increment,
`if_open` tinyint,
`count` smallint,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `attendance_user`
(
`attendance_user_id` int primary key auto_increment,
`user_id` int,
`attendance_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`user_id`) references `user`(`user_id`),
foreign key(`attendance_id`) references `attendance`(`attendance_id`)
)engine=InnoDB default charset=utf8;

create table `ask`
(
`ask_id` int primary key auto_increment,
`question` text,
`answer` text,
`user_id` int,
`class_id` int,
`create_date_time` datetime,
`modify_date_time` datetime,
foreign key(`user_id`) references `user`(`user_id`),
foreign key(`class_id`) references `class`(`class_id`)
)engine=InnoDB default charset=utf8;

create table `directory`
(
`directory_id` int primary key auto_increment,
`name` varchar(32),
`name_detail` varchar(32),
`code` smallint,
`code_detail` varchar(32),
`remark` text,
`create_date_time` datetime,
`modify_date_time` datetime
)