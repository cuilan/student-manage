package cn.cuilan.service;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.mapper.ClassRankMapper;
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
    private StudentService studentService;

    /**
     * 查询所有班级
     *
     * @param name    班级名称
     * @param gradeId 年级id
     */
    public Page<ClassRank> queryClassRanks(String name, Long gradeId, int pageNum, int pageSize) {
        Page<ClassRank> classRanks = classRankMapper.queryClassRanks(name, gradeId, pageNum, pageSize);
        // 统计每个班级下的学生数量
        classRanks.forEach(classRank -> classRank.setStudentNum(studentService.countByClassRankId(classRank.getId())));
        return classRanks;
    }

    /**
     * 统计年级下的班级数量
     *
     * @param gradeId 年级id
     * @return 返回该年级下的班级数量
     */
    public Integer countClassRankById(Long gradeId) {
        return classRankMapper.countClassRankById(gradeId);
    }
}
