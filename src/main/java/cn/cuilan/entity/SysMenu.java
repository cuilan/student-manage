package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统菜单
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_menu")
public class SysMenu extends BaseIdTimeEntity<Long> implements Comparable<SysMenu> {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 优先级，越小，同级显示的时候越靠前
     */
    private Integer priority;

    /**
     * 是否可见，0-不可见，1-可见
     */
    private boolean visible;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> sysMenus;

    @Override
    public int compareTo(SysMenu sysMenu) {
        return this.priority.compareTo(sysMenu.priority);
    }
}
