package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_rank")
public class ClassRank extends BaseIdTimeEntity<Long> {

    /**
     * 年级名称
     */
    private String name;

    /**
     * 所属年级id
     */
    private Long gradeId;

    /**
     * 所属年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 该班级下学生人数
     */
    @TableField(exist = false)
    private Integer studentNum;

}
