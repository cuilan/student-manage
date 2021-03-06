package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 年级实体
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_grade")
public class Grade extends BaseIdTimeEntity<Long> {

    /**
     * 年级名称
     */
    private String name;

}
