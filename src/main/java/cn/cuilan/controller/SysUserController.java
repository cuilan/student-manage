package cn.cuilan.controller;

import cn.cuilan.annotation.Logined;
import cn.cuilan.beans.SysUserLoginRequest;
import cn.cuilan.entity.SysUser;
import cn.cuilan.service.SysUserService;
import cn.cuilan.utils.NetworkUtils;
import cn.cuilan.utils.TokenManagerUtils;
import cn.cuilan.utils.TokenUtils;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统用户管理
 *
 * @author zhang.yan
 * @date 2020/12/10
 */
@Slf4j
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 系统用户登录
     */
    @PostMapping("/api/sysUser/login")
    public Result<?> login(@RequestBody SysUserLoginRequest loginRequest,
                           HttpServletRequest request) {
        String ip = NetworkUtils.getIpAddress(request);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        log.info("system user login, username: {}, password: {}, ip: {}", username, password, ip);

        SysUser sysUser = sysUserService.getByUsername(username);

        if (sysUser == null) {
            return Result.fail("未找到该用户");
        }

        if (!sysUser.getPassword().equals(password)) {
            return Result.fail("密码错误");
        }

        // 非超级管理员，需要判断是否可用
        if (!sysUser.getUsername().equals("admin") && !sysUser.isStatus()) {
            return Result.fail("您当前的账号不可用，请联系管理员!");
        }

        sysUser.setLastLoginIp(ip);
        sysUser.setLastLoginTime(System.currentTimeMillis());
        sysUserService.updateById(sysUser);

        // 生成token
        String token = TokenUtils.generaterToken();
        // 保存token，重启SpringBoot服务后失效
        TokenManagerUtils.setTokenAndUserId(token, sysUser.getId());

        return Result.map().data("token", token).data("sysUser", sysUser);
    }

    /**
     * 查询用户列表
     *
     * @param username 用户名称
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @return 返回用户列表
     */
    @GetMapping("/api/sysUser/query")
    public Result<?> querySysUser(@Logined Long currentSysUserId,
                                  @RequestParam(value = "id", required = false) Long sysUserId,
                                  @RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        log.info("query SysUser, currentSysUserId: {}", currentSysUserId);
        SysUser currentSysUser = sysUserService.getNotNull(currentSysUserId);
        boolean isAdmin = false;
        if (currentSysUser.getUsername().equals("admin")) {
            isAdmin = true;
        }

        if (sysUserId != null && sysUserId != 0) {
            SysUser sysUser = sysUserService.getSysUserAndRoleInfo(sysUserId);
            // 如果不是管理员，需要忽略用户敏感信息
            if (!isAdmin) {
                sysUserService.ignoreSysUserInfo(sysUser);
            }
            return Result.map().data("sysUser", sysUser);
        }

        return Result.map()
                .data("isAdmin", isAdmin)
                .data("sysUsers", sysUserService.getListByUsername(isAdmin, username, pageNum, pageSize));
    }

    /**
     * 添加系统用户
     */
    @PostMapping("/api/sysUser/add")
    public Result<?> addSysUser(@Logined Long currentSysUserId, @RequestBody SysUser sysUser) {
        log.info("add SysUser, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        sysUser.setStatus(true);
        sysUserService.save(sysUser);
        return Result.success();
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/api/sysUser/update")
    public Result<?> updateSysUser(@Logined Long currentSysUserId, @RequestBody SysUser sysUser) {
        log.info("update SysUser, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        SysUser dbSysUser = sysUserService.getNotNull(sysUser.getId());
        // 修改用户名
        if (StrUtil.isNotBlank(sysUser.getUsername())) {
            dbSysUser.setUsername(sysUser.getUsername());
        }
        // 密码
        if (StrUtil.isNotBlank(sysUser.getPassword())) {
            dbSysUser.setPassword(sysUser.getPassword());
        }
        // 电话
        if (StrUtil.isNotBlank(sysUser.getPhone())) {
            dbSysUser.setPhone(sysUser.getPhone());
        }
        // 头像
        if (StrUtil.isNotBlank(sysUser.getPortrait())) {
            dbSysUser.setPortrait(sysUser.getPortrait());
        }
        // 状态
        dbSysUser.setStatus(sysUser.isStatus());
        sysUserService.updateById(dbSysUser);
        return Result.success();
    }

    /**
     * 根据id删除用户
     */
    @PostMapping("/api/sysUser/delete")
    public Result<?> deleteSysUser(@Logined Long currentSysUserId, Long sysUserId) {
        log.info("delete SysUser, currentSysUserId: {}", currentSysUserId);
        sysUserService.checkIsAdmin(currentSysUserId);

        boolean deleted = sysUserService.removeById(sysUserId);
        return deleted ? Result.success("删除成功") : Result.fail("删除失败");
    }

}
