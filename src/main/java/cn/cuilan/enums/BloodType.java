package cn.cuilan.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 血型
 *
 * @author zhang.yan
 * @date 2020/12/21
 */
public enum BloodType implements IEnum<Integer> {

    // 0-A
    A(0, "A型"),

    // 1-B
    B(1, "B型"),

    // 2-AB
    AB(2, "AB型"),

    // 3-O
    O(3, "O型");

    private Integer value;

    private String desc;

    BloodType(Integer value, String desc) {
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

    /**
     * 获取所有血型
     */
    public static List<BloodType> getBloodTypes() {
        return Arrays.asList(values());
    }
}
