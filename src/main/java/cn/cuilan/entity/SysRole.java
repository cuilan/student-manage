package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统角色
 *
 * @author zhang.yan
 * @date 2020/12/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_role")
public class SysRole extends BaseIdTimeEntity<Long> {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否可用
     */
    private Boolean visible;

    /**
     * 该角色拥有的菜单
     */
    @TableField(exist = false)
    private List<SysMenu> sysMenus;
}
