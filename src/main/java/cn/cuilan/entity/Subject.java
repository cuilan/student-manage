package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 科目
 *
 * @author zhang.yan
 * @date 2020/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_subject")
public class Subject extends BaseIdTimeEntity<Long> {

    /**
     * 科目名称
     */
    private String name;

    /**
     * 是否需要考试，0-不需要，1-需要
     */
    private Boolean exam;

    /**
     * 是否开卷，0-不开卷，1-开卷
     */
    private Boolean open;

    /**
     * 备注
     */
    private String remark;
}
