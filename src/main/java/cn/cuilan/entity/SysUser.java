package cn.cuilan.entity;

import lombok.Data;

/**
 * 系统用户
 *
 * @author zhang.yan
 * @date 2020/12/9
 */
@Data
public class SysUser {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 该用户可访问的菜单，多菜单用英文逗号隔开
     */
    private String menus;

    /**
     * 该用户拥有的权限，多权限用英文逗号隔开
     */
    private String permissions;
}
