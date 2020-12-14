package cn.cuilan.entity;

import cn.cuilan.common.BaseNoIdEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统用户与系统角色关联实体
 *
 * @author zhang.yan
 * @date 2020-12-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user_roles")
public class SysUserRoles extends BaseNoIdEntity {

    /**
     * 角色id
     */
    private long rolesId;

    /**
     * 系统用户id
     */
    private long sysUserId;
}
