create database sket;
use sket;

create table user(
id varchar(20) not null primary key,
pw varchar(20) not null,
nick varchar(20) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=utf8;