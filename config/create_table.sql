DROP DATABASE sketio;
CREATE DATABASE sketio;
USE sketio;

CREATE TABLE user (
  id       VARCHAR(30) NOT NULL PRIMARY KEY,
  nick     VARCHAR(20) NOT NULL UNIQUE,
  level    INT(3)      NOT NULL DEFAULT 1,
  limitexp INT(5)      NOT NULL DEFAULT 300,
  totalexp INT(5)      NOT NULL DEFAULT 0,
  curexp   INT(5)      NOT NULL DEFAULT 0
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

CREATE TABLE quiz (
  category VARCHAR(10) NOT NULL,
  name     VARCHAR(10) NOT NULL PRIMARY KEY
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;
-- https://krdict.korean.go.kr/download/downloadList