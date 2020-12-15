package cn.cuilan.service;

import cn.cuilan.entity.SysMenu;
import cn.cuilan.entity.SysUser;
import cn.cuilan.mapper.SysMenuMapper;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统菜单service
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Slf4j
@Service
public class SysMenuService extends BaseService<SysMenuMapper, SysMenu> {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询当前登录用户的可见菜单
     *
     * @param sysUserId 当前登录用户id
     * @return 返回可见菜单
     */
    public List<SysMenu> getSysMenusBySysUserId(Long sysUserId) {
        List<SysMenu> sysMenus;
        SysUser sysUser = sysUserService.getNotNull(sysUserId);
        // 管理员可以查看所有
        if (sysUser.getUsername().equals("admin")) {
            sysMenus = sysMenuMapper.getAllSysMenu();
        } else {
            sysMenus = sysMenuMapper.getSysMenusBySysUserId(sysUserId);
        }
        return sortSysMenus(sysMenus);
    }

    /**
     * 查询当前角色可见的系统菜单
     *
     * @param sysRoleId 系统角色id
     * @return 菜单list
     */
    public List<SysMenu> getSysMenusByRoleId(Long sysRoleId) {
        List<SysMenu> sysMenus = sysMenuMapper.getSysMenusBySysRoleId(sysRoleId);
        return sortSysMenus(sysMenus);
    }

    /**
     * 整理菜单父子级关系
     *
     * @param sysMenus 菜单集合
     * @return 整理并排序后的菜单集合
     */
    public List<SysMenu> sortSysMenus(List<SysMenu> sysMenus) {
        // 根目录的父id为0
        Long rootParentId = 0L;
        Map<Long, List<SysMenu>> parentSysMenuMap = new HashMap<>();

        // 整理所有菜单的父子级关系
        sysMenus.forEach(sysMenu -> {
            List<SysMenu> menus = parentSysMenuMap.get(sysMenu.getParentId());
            if (CollUtil.isEmpty(menus)) {
                menus = new ArrayList<>();
                parentSysMenuMap.put(sysMenu.getParentId(), menus);
            }
            menus.add(sysMenu);
        });

        List<SysMenu> menus = parentSysMenuMap.get(rootParentId);
        if (CollUtil.isEmpty(menus)) {
            return null;
        }
        Collections.sort(menus);
        // 放入子菜单
        menus.forEach(menu -> {
            List<SysMenu> subSysMenus = parentSysMenuMap.get(menu.getId());
            if (CollUtil.isNotEmpty(subSysMenus)) {
                Collections.sort(subSysMenus);
                menu.setSysMenus(subSysMenus);
            }
        });
        return menus;
    }

}
