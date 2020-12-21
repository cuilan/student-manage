package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import cn.cuilan.enums.BloodType;
import cn.cuilan.enums.Sex;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生实体
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_student")
public class Student extends BaseIdTimeEntity<Long> {

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别，0-男，1-女
     */
    private Sex sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 血型，0-A，1-B，2-AB，3-O
     */
    private BloodType bloodType;

    /**
     * 民族
     */
    private String nation;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 年级id
     */
    private Long gradeId;

    /**
     * 班级Id
     */
    private Long classRankId;

    /**
     * 年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String classRankName;
}
