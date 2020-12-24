package cn.cuilan.service;

import cn.cuilan.entity.Examination;
import cn.cuilan.mapper.ExaminationMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 考试信息service
 *
 * @author zhang.yan
 * @date 2020/12/24
 */
@Service
public class ExaminationService extends BaseService<ExaminationMapper, Examination> {

    @Resource
    private ExaminationMapper examinationMapper;

    /**
     * 查询考试信息
     *
     * @param name        考试名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param subjectId   科目id
     * @param classRankId 班级id
     * @param pageNum     页码
     * @param pageSize    分页大小
     * @return 返回考试信息
     */
    public Page<Examination> queryExams(String name, Date startTime, Date endTime,
                                        Long subjectId, Long classRankId, int pageNum, int pageSize) {
        return examinationMapper.queryExams(name, startTime == null ? null : startTime.getTime(),
                endTime == null ? null : endTime.getTime(),
                subjectId, classRankId, pageNum, pageSize);
    }
}
