package cn.cuilan.service;

import cn.cuilan.entity.SysRole;
import cn.cuilan.mapper.SysRoleMapper;
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

    /**
     * 查询所有系统角色
     */
    public List<SysRole> getAllSysRole() {
        return sysRoleMapper.getAllSysRole();
    }

}
