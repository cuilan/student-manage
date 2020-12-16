package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统用户
 *
 * @author zhang.yan
 * @date 2020/12/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user")
public class SysUser extends BaseIdTimeEntity<Long> {

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 是否可用
     */
    private boolean status;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;

    /**
     * 该用户的角色id
     */
    @TableField(exist = false)
    private Long roleId;

    /**
     * 该用户的角色名称
     */
    @TableField(exist = false)
    private String roleName;

    /**
     * 该用户的角色描述
     */
    @TableField(exist = false)
    private String description;

    /**
     * 将 Long 类型时间转为 LocalDateTime 类型
     */
    public LocalDateTime getLastLoginDateTime() {
        return toLocalDateTime(lastLoginTime);
    }
}
