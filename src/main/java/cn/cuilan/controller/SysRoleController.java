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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

        SysUser currentSysUser = sysUserService.getNotNull(currentSysUserId);
        if (!currentSysUser.getUsername().equals("admin")) {
            return Result.fail("只有管理员才有权限添加新角色!");
        }

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

        SysUser currentSysUser = sysUserService.getNotNull(currentSysUserId);
        if (!currentSysUser.getUsername().equals("admin")) {
            return Result.fail("只有管理员才有权限修改权限!");
        }

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

        SysUser currentSysUser = sysUserService.getNotNull(currentSysUserId);
        if (!currentSysUser.getUsername().equals("admin")) {
            return Result.fail("只有管理员才有权限删除角色!");
        }

        boolean deleted = sysRoleService.removeById(sysRoleId);
        return deleted ? Result.success("删除成功") : Result.fail("删除失败");
    }

    /**
     * 根据角色id和菜单id删除关联关系
     */
    @PostMapping("/api/sysRole/deleteSysMenu")
    public Result<?> deleteByRoleIdAndMenuId(@Param("sysRoleId") Long sysRoleId, @Param("sysMenuId") Long sysMenuId) {
        return Result.map().data("newMenus", sysRoleService.deleteByRoleIdAndMenuId(sysRoleId, sysMenuId));
    }

}
