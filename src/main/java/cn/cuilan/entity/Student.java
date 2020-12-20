package cn.cuilan.entity;

import cn.cuilan.common.BaseIdTimeEntity;
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
}
