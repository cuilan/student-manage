package cn.cuilan.mapper;

import cn.cuilan.entity.Grade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 年级mapper
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {

    /**
     * 查询所有年级，支持根据年级名称模糊查询
     *
     * @param name 年级名称
     */
    @Select({"<script>",
            "select * from t_grade where 1 = 1 ",
            "<if test='name!=null'>",
            " and name like concat('%', #{name}, '%') ",
            "</if>",
            " order by id asc ",
            "</script>"})
    List<Grade> queryGrades(@Param("name") String name);
}
