package cn.cuilan.service;

import cn.cuilan.entity.SysUser;
import cn.cuilan.mapper.SysUserMapper;
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
    public Page<SysUser> getListByUsername(String username, int pageNum, int pageSize) {
        if (StrUtil.isBlank(username)) {
            username = null;
        }
        return sysUserMapper.getListByUsername(username, pageNum, pageSize);
    }

}
