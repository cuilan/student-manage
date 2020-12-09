package cn.cuilan.common;

import cn.cuilan.exception.BaseException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽象无ID实体
 *
 * @author zhang.yan
 * @date 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseNoIdEntity extends BaseObservableEntity<Long> implements Cloneable {

    @Override
    public Long getId() {
        throw new BaseException("不支持id操作");
    }

    @Override
    protected BaseNoIdEntity clone() throws CloneNotSupportedException {
        return (BaseNoIdEntity) super.clone();
    }
}
