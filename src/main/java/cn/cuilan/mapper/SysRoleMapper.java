package cn.cuilan.mapper;

import cn.cuilan.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
