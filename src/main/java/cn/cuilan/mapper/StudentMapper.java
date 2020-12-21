package cn.cuilan.mapper;

import cn.cuilan.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
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

    /**
     * 查询学生列表
     *
     * @param name        学生姓名
     * @param gradeId     年级id
     * @param classRankId 班级id
     * @param pageNum     页码
     * @param pageSize    分页大小
     * @return 返回学生列表
     */
    @Select({"<script>",
            " select s.*, g.name as gradeName, cr.name as classRankName ",
            " from t_grade as g ",
            " left join t_class_rank as cr on cr.grade_id = g.id ",
            " left join t_student as s on s.class_rank_id = cr.id ",
            " where 1 = 1 ",
            "<if test='gradeId!=null'>",
            " and s.grade_id = #{gradeId} ",
            "</if>",
            "<if test='classRankId!=null'>",
            " and s.class_rank_id = #{classRankId} ",
            "</if>",
            "<if test='name!=null'>",
            " and s.name like concat('%', #{name}, '%') ",
            "</if>",
            "</script>"})
    Page<Student> queryStudents(@Param("name") String name,
                                @Param("gradeId") Long gradeId,
                                @Param("classRankId") Long classRankId,
                                @Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize);
}
