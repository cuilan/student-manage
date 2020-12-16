package cn.cuilan.mapper;

import cn.cuilan.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 系统角色mapper
 *
 * @author zhang.yan
 * @date 2020/12/14
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询所有系统角色
     *
     * @return 角色list
     */
    @Select("select * from t_sys_role")
    List<SysRole> getAllSysRole();

    /**
     * 根据角色id和菜单id删除关联关系
     *
     * @param sysRoleId 角色id
     * @param sysMenuId 菜单id
     */
    @Delete("delete from t_sys_menu_roles where roles_id = #{sysRoleId} and sys_menu_id = #{sysMenuId}")
    void deleteByRoleIdAndMenuId(@Param("sysRoleId") Long sysRoleId, @Param("sysMenuId") Long sysMenuId);

    /**
     * 插入菜单与角色的关联关系
     *
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    @Insert("INSERT INTO `t_sys_menu_roles`(`roles_id`, `sys_menu_id`) VALUES (#{roleId}, #{menuId})")
    void insertSysMenuRoles(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 根据角色id删除所有菜单的关联关系
     *
     * @param roleId 角色id
     */
    @Delete("delete from t_sys_menu_roles where roles_id = #{roleId}")
    void deleteMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户id删除所有用户与角色的关联关系
     *
     * @param userId 用户id
     */
    @Delete("delete from t_sys_user_roles where sys_user_id = #{userId}")
    void deleteRoleIdByUserId(@Param("userId") Long userId);

    /**
     * 插入用户与角色的关联关系
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    @Insert("INSERT INTO `t_sys_user_roles`(`roles_id`, `sys_user_id`) VALUES (#{roleId}, #{userId})")
    void insertSysUserRoles(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
