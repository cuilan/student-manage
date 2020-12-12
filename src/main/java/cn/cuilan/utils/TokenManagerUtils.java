package cn.cuilan.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于存储已登录用户的token
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Slf4j
public class TokenManagerUtils {

    private static final ConcurrentHashMap<String, Long> tokenUserMap = new ConcurrentHashMap<>();

    /**
     * 存储token与当前已登录用户的id
     *
     * @param token  token
     * @param userId 当前登录用户id
     */
    public static void setTokenAndUserId(String token, Long userId) {
        tokenUserMap.put(token, userId);
    }

    /**
     * 根据token获取当前登录用户id
     *
     * @param token token
     * @return 返回当前登录用户id
     */
    public static Long getUserIdByToken(String token) {
        return tokenUserMap.get(token);
    }

}
