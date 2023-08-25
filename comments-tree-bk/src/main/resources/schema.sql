CREATE TABLE `comment` (
  `id` INTEGER PRIMARY KEY,
  `msg` varchar(500) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `path` varchar(1024) DEFAULT NULL,
  `createtime` DATETIME
);

CREATE TABLE `user` (
  `id` INTEGER PRIMARY KEY,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `pswd` varchar(120) NOT NULL
);

CREATE TABLE `role` (
  `id` INTEGER PRIMARY KEY,
  `rolename` varchar(50) DEFAULT NULL
);

CREATE TABLE `user_role` (
  `id` INTEGER PRIMARY KEY,
  `roleid` int not null,
  `userid` int not null
);