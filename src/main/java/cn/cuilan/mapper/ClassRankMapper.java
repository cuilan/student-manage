package cn.cuilan.mapper;

import cn.cuilan.entity.ClassRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 班级mapper
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Mapper
public interface ClassRankMapper extends BaseMapper<ClassRank> {

    @Select({"<script>",
            "select cr.*, g.name as gradeName from t_grade as g ",
            " join t_class_rank as cr on cr.grade_id = g.id ",
            " where 1 = 1 ",
            "<if test='gradeId!=null'>",
            " and g.id = #{gradeId} ",
            "</if>",
            "<if test='name!=null'>",
            " and cr.name like concat('%', #{name}, '%') ",
            "</if>",
            "</script>"})
    Page<ClassRank> queryClassRanks(@Param("name") String name,
                                    @Param("gradeId") Long gradeId,
                                    @Param("pageNum") int pageNum,
                                    @Param("pageSize") int pageSize);

    /**
     * 统计年级下的班级数量
     *
     * @param gradeId 年级id
     * @return 返回该年级下的班级数量
     */
    @Select("select count(*) from t_class_rank where grade_id = #{gradeId}")
    Integer countClassRankById(@Param("gradeId") Long gradeId);
}
