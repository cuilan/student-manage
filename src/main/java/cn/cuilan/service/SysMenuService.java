package cn.cuilan.service;

import cn.cuilan.entity.SysMenu;
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

    public List<SysMenu> getAllSysMenu() {
        List<SysMenu> sysMenus = sysMenuMapper.getAllSysMenu();
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
        Collections.sort(menus);
        // 放入子菜单
        menus.forEach(menu -> {
            List<SysMenu> subSysMenus = parentSysMenuMap.get(menu.getId());
            if (CollUtil.isNotEmpty(subSysMenus)) {
                Collections.sort(subSysMenus);
                menu.setSubMenus(subSysMenus);
            }
        });
        return menus;
    }

}
