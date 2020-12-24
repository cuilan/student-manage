package cn.cuilan.mapper;

import cn.cuilan.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 科目mapper
 *
 * @author zhang.yan
 * @date 2020/12/23
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

    /**
     * 查询所有科目
     *
     * @param name 科目名称
     * @return 返回所有科目
     */
    @Select({"<script>",
            "select * from t_subject where 1 = 1 ",
            "<if test='name!=null'>",
            " and `name` like concat('%', #{name}, '%') ",
            "</if>",
            "</script>"})
    List<Subject> querySubjects(@Param("name") String name);

}
