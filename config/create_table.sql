create database sketch;
use sketch;

create table user (
id varchar(10) not null primary key,
pw varchar(20) not null,
nick varchar(20) not null unique,
level int(3) not null default 1,
totalexp int(5) not null default 0,
curexp int(5) not null default 0
) engine=innodb default charset=utf8;

create table quiz (
category int(3) not null,
name varchar(10) not null primary key
) engine=innodb default charset=utf8;