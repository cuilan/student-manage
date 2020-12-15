package cn.cuilan.controller;

import cn.cuilan.annotation.Logined;
import cn.cuilan.entity.SysRole;
import cn.cuilan.entity.SysUser;
import cn.cuilan.service.SysMenuService;
import cn.cuilan.service.SysRoleService;
import cn.cuilan.service.SysUserService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色
 *
 * @author zhang.yan
 * @date 2020/12/14
 */
@Slf4j
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询系统角色
     */
    @GetMapping("/api/sysRole/all")
    public Result<?> getAllRoles(@Logined Long sysUserId) {
        return Result.map().data("roles", sysRoleService.getAllSysRole());
    }

    /**
     * 根据id查询系统角色
     */
    @GetMapping("/api/sysRole/id")
    public Result<?> getSysRoleById(@Logined Long currentSysUserId, Long id) {
        SysRole sysRole = sysRoleService.getNotNull(id);
        // 将该角色可见的系统菜单放入
        sysRole.setSysMenus(sysMenuService.getSysMenusByRoleId(sysRole.getId()));
        return Result.map().data("sysRole", sysRole);
    }

    /**
     * 添加系统角色
     */
    @PostMapping("/api/sysRole/add")
    public Result<?> addSysRole(@Logined Long currentSysUserId, @RequestBody SysRole sysRole) {
        log.info("add SysRole, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        sysRole.setVisible(true);
        sysRoleService.save(sysRole);
        return Result.success();
    }

    /**
     * 修改角色
     */
    @PostMapping("/api/sysRole/update")
    public Result<?> updateSysRole(@Logined Long currentSysUserId, @RequestBody SysRole sysRole) {
        log.info("update SysRole, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        SysRole dbSysRole = sysRoleService.getNotNull(sysRole.getId());
        // 修改角色名称
        if (StrUtil.isNotBlank(sysRole.getName())) {
            dbSysRole.setName(sysRole.getName());
        }
        // 描述
        if (StrUtil.isNotBlank(sysRole.getDescription())) {
            dbSysRole.setDescription(sysRole.getDescription());
        }
        // 是否可用
        dbSysRole.setVisible(sysRole.getVisible());
        sysRoleService.updateById(dbSysRole);
        return Result.success();
    }

    /**
     * 根据id删除角色
     */
    @PostMapping("/api/sysRole/delete")
    public Result<?> deleteSysRole(@Logined Long currentSysUserId, Long sysRoleId) {
        log.info("delete SysRole, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        boolean deleted = sysRoleService.removeById(sysRoleId);
        return deleted ? Result.success("删除成功") : Result.fail("删除失败");
    }

    /**
     * 根据角色id和菜单id删除关联关系
     */
    @PostMapping("/api/sysRole/deleteSysMenu")
    public Result<?> deleteByRoleIdAndMenuId(@Logined Long currentSysUserId, @Param("sysRoleId") Long sysRoleId, @Param("sysMenuId") Long sysMenuId) {
        sysUserService.checkIsAdmin(currentSysUserId);
        return Result.map().data("newMenus", sysRoleService.deleteByRoleIdAndMenuId(sysRoleId, sysMenuId));
    }

    /**
     * 添加角色id与菜单数组的关联关系
     */
    @PostMapping("/api/sysRole/addSysMenu")
    public Result<?> addByRoleIdAndMenuIds(@Logined Long currentSysUserId,
                                           @RequestParam("roleId") Long roleId,
                                           @RequestParam("menuIds") String sysMenuIds) {
        sysUserService.checkIsAdmin(currentSysUserId);

        String[] menuIds;
        if (StrUtil.isBlank(sysMenuIds)) {
            menuIds = null;
        } else {
            menuIds = sysMenuIds.trim().split(",");
        }
        sysRoleService.addByRoleIdAndMenuIds(roleId, menuIds);
        return Result.success();
    }


}
