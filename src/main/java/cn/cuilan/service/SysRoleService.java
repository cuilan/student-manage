package cn.cuilan.service;

import cn.cuilan.entity.SysMenu;
import cn.cuilan.entity.SysRole;
import cn.cuilan.exception.BaseException;
import cn.cuilan.mapper.SysRoleMapper;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhang.yan
 * @date 2020/12/14
 */
@Slf4j
@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRole> {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询所有系统角色
     */
    public List<SysRole> getAllSysRole() {
        List<SysRole> sysRoles = sysRoleMapper.getAllSysRole();
        if (CollUtil.isEmpty(sysRoles)) {
            throw new BaseException("未找到系统角色，系统错误");
        }

        sysRoles.forEach(sysRole -> {
            // 将该角色可见的系统菜单放入
            sysRole.setSysMenus(sysMenuService.getSysMenusByRoleId(sysRole.getId()));
        });
        return sysRoles;
    }

    /**
     * 根据角色id和菜单id删除关联关系
     *
     * @param sysRoleId 角色id
     * @param sysMenuId 菜单id
     */
    public List<SysMenu> deleteByRoleIdAndMenuId(Long sysRoleId, Long sysMenuId) {
        sysRoleMapper.deleteByRoleIdAndMenuId(sysRoleId, sysMenuId);
        return sysMenuService.getSysMenusByRoleId(sysRoleId);
    }

    /**
     * 添加角色id与菜单数组的关联关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id的字符串数组
     */
    public void addByRoleIdAndMenuIds(Long roleId, String[] menuIds) {
        // 先删除所有
        sysRoleMapper.deleteMenuIdsByRoleId(roleId);
        // 如果不为空，则逐条添加
        if (menuIds != null) {
            for (String menuId : menuIds) {
                sysRoleMapper.insertSysMenuRoles(roleId, Long.parseLong(menuId));
            }
        }
    }

    /**
     * 添加角色id与用户id的关联关系
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    public void addByRoleIdAndUserId(Long roleId, Long userId) {
        // 先删除所有
        sysRoleMapper.deleteRoleIdByUserId(userId);
        // 添加角色与用户的关联关系
        sysRoleMapper.insertSysUserRoles(roleId, userId);
    }
}
