package cn.cuilan.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成MD5 TOKEN工具类
 *
 * @author zhang.yan
 * @date 2020/11/19
 */
@Slf4j
public class TokenUtils {

    /**
     * 生成token
     *
     * @return 返回token
     */
    public static String generaterToken() {
        String salt = RandomUtil.randomString(16);
        String randomString = RandomUtil.randomString(32);
        log.info("salt: {}", salt);
        log.info("randomString: {}", randomString);

        MD5 md5 = new MD5(salt.getBytes());
        String md5String = md5.digestHex(randomString);
        log.info("md5: {}", md5String);
        return md5String;
    }

    public static void main(String[] args) {
        System.out.println(generaterToken());
    }
}
