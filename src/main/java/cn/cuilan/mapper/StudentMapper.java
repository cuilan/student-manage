package cn.cuilan.mapper;

import cn.cuilan.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 学生mapper
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 统计每个班级下的学生数量
     *
     * @param classRankId 班级id
     * @return 返回该班级下的学生数量
     */
    @Select("select count(*) from t_student where class_rank_id = #{classRankId}")
    Integer countByClassRankId(@Param("classRankId") Long classRankId);
}
