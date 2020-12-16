-- 插入系统用户
INSERT INTO `t_sys_user`(`id`, `username`, `password`, `phone`, `status`, `portrait`,
`last_login_ip`, `last_login_time`, `create_time`, `update_time`) VALUES
(1, 'admin', '123456', '17700001111', 1, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '', 1607765013556, 1607765013556, 1607765013556),
(2, 'user1', '12345678', '18900001111', 1, 'https://lh3.googleusercontent.com/ogw/ADGmqu_ohmdl-VKxHm2qTPn1Cd7w6yE2EHVuKhWKgLQl=s64-c-mo', '', 1607765013556, 1607765013556, 1607765013556),
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
(9, '录入信息', '/student-add', 'el-icon-document-add', 2, 9, 1, 1607765013556, 1607765013556),
(10, '查询信息', '/student-query', 'el-icon-search', 2, 10, 1, 1607765013556, 1607765013556),
(11, '修改信息', '/student-edit', 'el-icon-edit', 2, 11, 1, 1607765013556, 1607765013556),
(12, '打印信息', '/student-print', 'el-icon-printer', 2, 12, 1, 1607765013556, 1607765013556),
(13, '录入成绩', '/scores-add', 'el-icon-document-add', 3, 13, 1, 1607765013556, 1607765013556),
(14, '查询成绩', '/scores-query', 'el-icon-search', 3, 14, 1, 1607765013556, 1607765013556),
(15, '修改成绩', '/scores-edit', 'el-icon-edit', 3, 15, 1, 1607765013556, 1607765013556),
(16, '打印成绩', '/scores-print', 'el-icon-printer', 3, 16, 1, 1607765013556, 1607765013556),
(17, '添加考试', '/exam-add', 'el-icon-plus', 3, 17, 1, 1607765013556, 1607765013556),
(18, '修改考试', '/exam-query', 'el-icon-check', 3, 18, 1, 1607765013556, 1607765013556),
(19, '删除考试', '/exam-delete', 'el-icon-close', 3, 19, 1, 1607765013556, 1607765013556),
(20, '科目管理', '/exam-manage', 'el-icon-monitor', 3, 20, 1, 1607765013556, 1607765013556);

-- 插入系统角色
INSERT INTO `t_sys_role`(`id`, `name`, `description`, `visible`, `create_time`, `update_time`) VALUES
(1, 'ROLE_ROOT', '超级管理员', 1, 1607765013556, 1607765013556),
(2, 'ROLE_ADMIN', '系统管理员', 1, 1607765013556, 1607765013556),
(3, 'ROLE_USER', '普通用户', 1, 1607765013556, 1607765013556);

-- 插入关联表初始数据
INSERT INTO `t_sys_user_roles`(`roles_id`, `sys_user_id`) VALUES (1, 1), (2, 2), (3, 3);
INSERT INTO `t_sys_menu_roles`(`roles_id`, `sys_menu_id`) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8);
