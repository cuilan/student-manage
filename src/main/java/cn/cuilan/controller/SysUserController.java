package cn.cuilan.controller;

import cn.cuilan.beans.SysUserLoginRequest;
import cn.cuilan.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户管理
 *
 * @author zhang.yan
 * @date 2020/12/10
 */
@Slf4j
@RestController
public class SysUserController {

    /**
     * 系统用户登录
     */
    @PostMapping("/api/sysUser/login")
    public Result<?> login(@RequestBody SysUserLoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        log.info("system user login, username: {}, password: {}", username, password);

        String token = "";
        return Result.map().data("token", token);
    }

}
