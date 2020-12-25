package cn.cuilan.mapper;

import cn.cuilan.entity.Examination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 考试信息mapper
 *
 * @author zhang.yan
 * @date 2020/12/24
 */
@Mapper
public interface ExaminationMapper extends BaseMapper<Examination> {

    /**
     * 查询考试信息
     *
     * @param name          考试名称
     * @param finished      是否已结束
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param subjectId     科目id
     * @param classRankName 班级名称
     * @param pageNum       页码
     * @param pageSize      分页大小
     * @return 返回考试信息
     */
    @Select({"<script>",
            " select e.*, s.name as subjectName, cr.name as classRankName from t_subject as s ",
            " left join t_examination as e on e.subject_id = s.id ",
            " left join t_class_rank as cr on cr.id = e.class_rank_id ",
            " where 1 = 1 ",
            "<if test='name!=null'>",
            " and e.name like concat('%', #{name}, '%') ",
            "</if>",
            "<if test='finished!=null'>",
            " and e.finished = #{finished} ",
            "</if>",
            "<if test='startTime!=null'>",
            " and e.start_time &gt;= #{startTime} ",
            "</if>",
            "<if test='endTime!=null'>",
            " and e.end_time &lt; #{endTime} ",
            "</if>",
            "<if test='subjectId!=null'>",
            " and s.id = #{subjectId} ",
            "</if>",
            "<if test='classRankName!=null'>",
            " and cr.name like concat('%', #{classRankName}, '%') ",
            "</if>",
            "</script>"})
    Page<Examination> queryExams(@Param("name") String name,
                                 @Param("finished") Boolean finished,
                                 @Param("startTime") Long startTime,
                                 @Param("endTime") Long endTime,
                                 @Param("subjectId") Long subjectId,
                                 @Param("classRankName") String classRankName,
                                 @Param("pageNum") int pageNum,
                                 @Param("pageSize") int pageSize);
}
