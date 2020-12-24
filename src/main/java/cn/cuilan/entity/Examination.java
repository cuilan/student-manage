package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 考试信息
 *
 * @author zhang.yan
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_examination")
public class Examination extends BaseIdTimeEntity<Long> {

    /**
     * 考试名称
     */
    private String name;

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
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 总分数
     */
    private Integer totalScore;

    /**
     * 是否已完成，0-未完成，1-完成
     */
    private boolean finished;

    /**
     * 考试地点
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 考试开始时间日期类型，Long -> LocalDateTime
     */
    public LocalDateTime getStartDateTime() {
        return toLocalDateTime(startTime);
    }

    /**
     * 考试结束时间日期类型，Long -> LocalDateTime
     */
    public LocalDateTime getEndDateTime() {
        return toLocalDateTime(endTime);
    }
}
