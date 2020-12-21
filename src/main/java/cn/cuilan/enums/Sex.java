package cn.cuilan.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 性别
 *
 * @author zhang.yan
 * @date 2020/12/21
 */
public enum Sex implements IEnum<Integer> {

    // 0-男
    MALE(0, "男"),

    // 1-女
    FEMALE(1, "女");

    private Integer value;

    private String desc;

    Sex(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }
}
