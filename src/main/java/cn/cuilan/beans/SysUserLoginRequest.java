package cn.cuilan.beans;

import lombok.Data;

/**
 * 系统用户登录请求
 *
 * @author zhang.yan
 * @date 2020/12/10
 */
@Data
public class SysUserLoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
