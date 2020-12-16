package cn.cuilan.mapper;

import cn.cuilan.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单mapper
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询所有系统菜单
     *
     * @return 菜单list
     */
    @Select("select * from t_sys_menu")
    List<SysMenu> getAllSysMenu();

    /**
     * 查询当前用户可见的系统菜单
     *
     * @param sysUserId 系统用户id
     * @return 菜单list
     */
    @Select({"select m.* from t_sys_menu as m ",
            " left join t_sys_menu_roles as mr on m.id = mr.sys_menu_id ",
            " left join t_sys_user_roles as ur on mr.roles_id = ur.roles_id ",
            " where m.visible = true ",
            " and ur.sys_user_id = #{sysUserId}"})
    List<SysMenu> getSysMenusBySysUserId(@Param("sysUserId") Long sysUserId);

    /**
     * 查询当前角色可见的系统菜单
     *
     * @param sysRoleId 系统角色id
     * @return 菜单list
     */
    @Select({"select m.* from t_sys_menu as m ",
            " left join t_sys_menu_roles as mr on m.id = mr.sys_menu_id ",
            " where m.visible = true ",
            " and mr.roles_id = #{sysRoleId} "})
    List<SysMenu> getSysMenusBySysRoleId(@Param("sysRoleId") Long sysRoleId);
}
