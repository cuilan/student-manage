package cn.cuilan.service;

import cn.cuilan.entity.SysUser;
import cn.cuilan.exception.BaseException;
import cn.cuilan.mapper.SysUserMapper;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户service
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Slf4j
@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 验证是否为管理员，非管理员直接抛异常
     *
     * @param sysUserId 系统用户id
     */
    public void checkIsAdmin(Long sysUserId) {
        SysUser currentSysUser = getNotNull(sysUserId);
        if (!currentSysUser.getUsername().equals("admin")) {
            throw new BaseException("只有超级管理员才有权限进行该操作!");
        }
    }

    /**
     * 获取系统用户及角色信息
     *
     * @param sysUserId 系统用户id
     * @return 返回系统用户
     */
    public SysUser getSysUserAndRoleInfo(Long sysUserId) {
        return sysUserMapper.getById(sysUserId);
    }

    /**
     * 根据用户名查询系统用户
     *
     * @param username 用户名称
     * @return 返回系统用户
     */
    public SysUser getByUsername(String username) {
        return sysUserMapper.getByUsername(username);
    }

    /**
     * 根据用户名称搜索用户
     *
     * @param username 用户名称
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @return 返回用户列表
     */
    public Page<SysUser> getListByUsername(boolean isAdmin, String username, int pageNum, int pageSize) {
        if (StrUtil.isBlank(username)) {
            username = null;
        }
        Page<SysUser> sysUsers = sysUserMapper.getListByUsername(username, pageNum, pageSize);
        if (!isAdmin) {
            sysUsers.forEach(this::ignoreSysUserInfo);
        }
        return sysUsers;
    }

    /**
     * 忽略用户敏感信息
     *
     * @param sysUser sysUser
     */
    public void ignoreSysUserInfo(SysUser sysUser) {
        sysUser.setPassword(null);
    }

}
