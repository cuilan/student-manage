package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成绩
 *
 * @author zhang.yan
 * @date 2020/12/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_score")
public class Score extends BaseIdTimeEntity<Long> {

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 考试科目id
     */
    private Long subjectId;

    /**
     * 科目名称
     */
    @TableField(exist = false)
    private String subjectName;

    /**
     * 考试班级id
     */
    private Long classRankId;

    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String classRankName;

    /**
     * 考试信息id
     */
    private Long examinationId;

    /**
     * 考试名称
     */
    @TableField(exist = false)
    private String examinationName;

    /**
     * 分数
     */
    private Integer score;

}
