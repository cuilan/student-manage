-- 插入系统用户
INSERT INTO `t_sys_user`(`id`, `username`, `password`, `phone`, `status`, `portrait`,
`last_login_ip`, `last_login_time`, `create_time`, `update_time`) VALUES
(1, 'admin', '123456', '17700001111', 1, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '', 1607765013556, 1607765013556, 1607765013556),
(2, 'user1', '12345678', '18900001111', 1, '', '', 1607765013556, 1607765013556, 1607765013556),
(3, 'user2', '12345678', '18900002222', 1, '', '', 1607765013556, 1607765013556, 1607765013556);

-- 插入系统菜单
INSERT INTO `t_sys_menu`(`id`, `name`, `url`, `icon`, `parent_id`, `priority`,
`visible`, `create_time`, `update_time`) VALUES
(1, '主页', '/home', 'el-icon-s-home', 0, 0, 1, 1607765013556, 1607765013556),
(2, '学生信息管理', '/students', 'el-icon-s-custom', 0, 2, 1, 1607765013556, 1607765013556),
(3, '学生成绩管理', '/scores', 'el-icon-s-claim', 0, 3, 1, 1607765013556, 1607765013556),
(4, '考试信息管理', '/exams', 'el-icon-s-check', 0, 4, 1, 1607765013556, 1607765013556),
(5, '系统管理', '/settings', 'el-icon-s-tools', 0, 5, 1, 1607765013556, 1607765013556),
(6, '系统用户管理', '/users', 'el-icon-user', 5, 6, 1, 1607765013556, 1607765013556),
(7, '系统菜单管理', '/menus', 'el-icon-document', 5, 7, 1, 1607765013556, 1607765013556),
(8, '系统角色管理', '/roles', 'el-icon-trophy', 5, 8, 1, 1607765013556, 1607765013556),

(8, '录入信息', '/student-add', 'el-icon-document-add', 2, 8, 1, 1607765013556, 1607765013556),
(9, '查询信息', '/student-query', 'el-icon-search', 2, 9, 1, 1607765013556, 1607765013556),
(10, '修改信息', '/student-edit', 'el-icon-edit', 2, 10, 1, 1607765013556, 1607765013556),
(11, '打印信息', '/student-print', 'el-icon-printer', 2, 11, 1, 1607765013556, 1607765013556),
(12, '录入成绩', '/scores-add', 'el-icon-document-add', 3, 12, 1, 1607765013556, 1607765013556),
(13, '查询成绩', '/scores-query', 'el-icon-search', 3, 12, 1, 1607765013556, 1607765013556),
(14, '修改成绩', '/scores-edit', 'el-icon-edit', 3, 14, 1, 1607765013556, 1607765013556),
(15, '打印成绩', '/scores-print', 'el-icon-printer', 3, 15, 1, 1607765013556, 1607765013556),
(16, '添加考试', '/exam-add', 'el-icon-plus', 3, 16, 1, 1607765013556, 1607765013556),
(17, '修改考试', '/exam-query', 'el-icon-check', 3, 17, 1, 1607765013556, 1607765013556),
(18, '删除考试', '/exam-delete', 'el-icon-close', 3, 18, 1, 1607765013556, 1607765013556),
(19, '科目管理', '/exam-manage', 'el-icon-monitor', 3, 19, 1, 1607765013556, 1607765013556),
;

-- 插入系统角色
INSERT INTO `t_sys_role`(`id`, `name`, `description`, `visible`, `create_time`, `update_time`) VALUES
(1, '超级管理员', '拥有所有权限', 1, 1607765013556, 1607765013556);