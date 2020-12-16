package cn.cuilan.controller;

import cn.cuilan.annotation.Logined;
import cn.cuilan.entity.SysMenu;
import cn.cuilan.service.SysMenuService;
import cn.cuilan.service.SysUserService;
import cn.cuilan.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统菜单管理
 *
 * @author zhang.yan
 * @date 2020/12/10
 */
@Slf4j
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询系统菜单
     */
    @GetMapping("/api/sysMenu/all")
    public Result<?> getAllMenus(@Logined Long sysUserId) {
        return Result.map().data("menus", sysMenuService.getSysMenusBySysUserId(sysUserId));
    }

    /**
     * 更新系统菜单状态
     */
    @PostMapping("/api/sysMenu/update")
    public Result<?> updateSysMenu(@Logined Long sysUserId, @RequestBody SysMenu sysMenu) {
        sysUserService.checkIsAdmin(sysUserId);

        SysMenu dbSysMenu = sysMenuService.getNotNull(sysMenu.getId());
        dbSysMenu.setVisible(sysMenu.isVisible());
        sysMenuService.updateById(dbSysMenu);
        return Result.success();
    }

}
