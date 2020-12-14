-- 创建数据库
CREATE database student;

-- ====================================================

-- 系统用户表
CREATE TABLE `t_sys_user`
(
    `id`              INT(11) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `username`        VARCHAR(32)         NOT NULL DEFAULT '' COMMENT '用户名',
    `password`        VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '用户密码',
    `phone`           VARCHAR(32)         NOT NULL DEFAULT '' COMMENT '电话',
    `status`          TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '0-停用 1-启用',
    `portrait`        VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '头像',
    `last_login_ip`   VARCHAR(100)        NOT NULL DEFAULT 0 COMMENT '最后登录IP',
    `last_login_time` BIGINT(13)          NOT NULL DEFAULT 0 COMMENT '最后登录时间',
    `create_time`     BIGINT(13)          NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`     BIGINT(13)          NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

-- 系统菜单表
CREATE TABLE `t_sys_menu`
(
    `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(200)     NOT NULL DEFAULT '' COMMENT '菜单名称',
    `url`         VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '跳转地址',
    `icon`        VARCHAR(32)      NOT NULL DEFAULT '' COMMENT '菜单图标',
    `parent_id`   INT(11)          NOT NULL DEFAULT 0 COMMENT '父菜单 ID',
    `priority`    SMALLINT(6)      NOT NULL DEFAULT 0 COMMENT '优先级，越小，同级显示的时候越靠前',
    `visible`     TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '是否可见，0-不可见，1-可见',
    `create_time` BIGINT(13)       NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)       NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='系统菜单表';

-- 系统角色表
CREATE TABLE `t_sys_role`
(
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '角色Id',
    `name`        VARCHAR(255) NOT NULL DEFAULT '' COMMENT '角色名称',
    `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述',
    `create_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `visible`     TINYINT(11)  NOT NULL DEFAULT 0 COMMENT '是否可见，0-不可见，1-可见',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='系统角色表';
