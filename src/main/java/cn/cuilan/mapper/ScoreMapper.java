package cn.cuilan.mapper;

import cn.cuilan.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 成绩mapper
 *
 * @author zhang.yan
 * @date 2020/12/26
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    /**
     * 查询成绩
     *
     * @param studentId   学生id
     * @param studentName 学生姓名
     * @param subjectId   科目id
     * @param classRankId 班级id
     * @param score       分数
     * @param pageNum     页码
     * @param pageSize    分页大小
     * @return 返回符合条件的成绩
     */
    @Select({"<script>",
            " select sc.*, su.name as subjectName, cr.name as classRankName, e.name as examinationName from t_subject as su  ",
            " join t_examination as e on e.subject_id = su.id ",
            " join t_class_rank as cr on cr.id = e.class_rank_id ",
            " join t_score as sc on sc.examination_id = e.id ",
            " join t_student as st on st.id = sc.student_id ",
            " where 1 = 1 ",
            "<if test='studentId!=null'>",
            " and st.id = #{studentId} ",
            "</if>",
            "<if test='studentName!=null'>",
            " and st.name like concat('%', #{studentName}, '%') ",
            "</if>",
            "<if test='subjectId!=null'>",
            " and su.id = #{subjectId} ",
            "</if>",
            "<if test='classRankId!=null'>",
            " and cr.id = #{classRankId} ",
            "</if>",
            "<if test='score!=null'>",
            " and sc.score = #{score} ",
            "</if>",
            "</script>"})
    Page<Score> queryScores(@Param("studentId") Long studentId,
                            @Param("studentName") String studentName,
                            @Param("subjectId") Long subjectId,
                            @Param("classRankId") Long classRankId,
                            @Param("score") Integer score,
                            @Param("pageNum") int pageNum,
                            @Param("pageSize") int pageSize);
}
