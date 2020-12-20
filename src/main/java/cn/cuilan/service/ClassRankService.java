package cn.cuilan.service;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.mapper.ClassRankMapper;
import cn.cuilan.mapper.StudentMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhang.yan
 * @date 2020/12/20
 */
@Service
public class ClassRankService extends BaseService<ClassRankMapper, ClassRank> {

    @Resource
    private ClassRankMapper classRankMapper;

    @Resource
    private StudentMapper studentMapper;

    /**
     * 查询所有班级
     *
     * @param name    班级名称
     * @param gradeId 年级id
     */
    public Page<ClassRank> queryClassRanks(String name, Long gradeId, int pageNum, int pageSize) {
        Page<ClassRank> classRanks = classRankMapper.queryClassRanks(name, gradeId, pageNum, pageSize);
        // 统计每个班级下的学生数量
        classRanks.forEach(classRank -> classRank.setStudentNum(studentMapper.countByClassRankId(classRank.getId())));
        return classRanks;
    }
}
