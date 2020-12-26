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
  DEFAULT CHARSET = utf8mb4 COMMENT = '系统用户表';

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
  DEFAULT CHARSET = utf8 COMMENT = '系统菜单表';

-- 系统角色表
CREATE TABLE `t_sys_role`
(
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '角色Id',
    `name`        VARCHAR(255) NOT NULL DEFAULT '' COMMENT '角色名称',
    `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述',
    `visible`     TINYINT(11)  NOT NULL DEFAULT 0 COMMENT '是否可见，0-不可见，1-可见',
    `create_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT = '系统角色表';

-- 用户角色关联表
CREATE TABLE `t_sys_user_roles`
(
    `roles_id`    BIGINT(20) NOT NULL COMMENT '角色id',
    `sys_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    UNIQUE KEY `uk_sys_userId` (`sys_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户和角色关联表';

-- 菜单角色关联表
CREATE TABLE `t_sys_menu_roles`
(
    `roles_id`    BIGINT(20) NOT NULL COMMENT '角色id',
    `sys_menu_id` BIGINT(20) NOT NULL COMMENT '菜单id',
    UNIQUE KEY `uk_roleId_menuId` (`roles_id`, `sys_menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色和菜单关联表';

-- 年级表
CREATE TABLE `t_grade`
(
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time` BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '年级名称',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '年级表';

-- 班级表
CREATE TABLE `t_class_rank`
(
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time` BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '班级名称',
    `grade_id`    BIGINT(20)  NOT NULL DEFAULT 0 COMMENT '年级Id',
    PRIMARY KEY (`id`),
    KEY `idx_grade_id` (`grade_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '班级表';

-- 学生表
CREATE TABLE `t_student`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time`   BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`   BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `name`          VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '学生姓名',
    `sex`           TINYINT(2)   NOT NULL DEFAULT 0 COMMENT '性别，0-男，1-女',
    `age`           TINYINT(2)   NOT NULL DEFAULT 0 COMMENT '年龄',
    `blood_type`    TINYINT(2)   NOT NULL DEFAULT 0 COMMENT '血型，0-A，1-B，2-AB，3-O',
    `nation`        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '民族',
    `address`       VARCHAR(255) NOT NULL DEFAULT '' COMMENT '家庭住址',
    `grade_id`      BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '年级Id',
    `class_rank_id` BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '班级Id',
    PRIMARY KEY (`id`),
    KEY `idx_grade_id` (`grade_id`),
    KEY `idx_class_rank_id` (`class_rank_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '学生表';

-- 科目表
CREATE TABLE `t_subject`
(
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `name`        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '科目名',
    `exam`        TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否需要考试，0-不需要，1-需要',
    `open`        TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否开卷，0-不开卷，1-开卷',
    `remark`      VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '科目表';

-- 考试信息表
CREATE TABLE `t_examination`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time`   BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`   BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `name`          VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '考试名称',
    `subject_id`    BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '考试科目id',
    `class_rank_id` BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '考试班级id',
    `start_time`    BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '开始时间',
    `end_time`      BIGINT(13)   NOT NULL DEFAULT 0 COMMENT '结束时间',
    `total_score`   TINYINT(3)   NOT NULL DEFAULT 0 COMMENT '总分数',
    `finished`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否已完成，0-未完成，1-完成',
    `address`       VARCHAR(255) NOT NULL DEFAULT '' COMMENT '考试地点',
    `remark`        VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_subject_id` (`subject_id`),
    KEY `idx_class_rank_id` (`class_rank_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '考试信息表';

-- 学生考试成绩表
CREATE TABLE `t_score`
(
    `id`             BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `create_time`    BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time`    BIGINT(13)  NOT NULL DEFAULT 0 COMMENT '最后修改时间',
    `student_id`     BIGINT(20)  NOT NULL DEFAULT 0 COMMENT '学生id',
    `student_name`   VARCHAR(32) NOT NULL DEFAULT '' COMMENT '学生姓名',
    `subject_id`     BIGINT(20)  NOT NULL DEFAULT 0 COMMENT '考试科目id',
    `class_rank_id`  BIGINT(20)  NOT NULL DEFAULT 0 COMMENT '考试班级id',
    `examination_id` BIGINT(20)  NOT NULL DEFAULT 0 COMMENT '考试信息id',
    `score`          TINYINT(3)  NOT NULL DEFAULT 0 COMMENT '分数',
    PRIMARY KEY (`id`),
    KEY `idx_student_id` (`student_id`),
    KEY `idx_subject_id` (`subject_id`),
    KEY `idx_class_rank_id` (`class_rank_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '学生考试成绩表';

