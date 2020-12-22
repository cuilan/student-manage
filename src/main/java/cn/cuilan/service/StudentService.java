package cn.cuilan.service;

import cn.cuilan.entity.Student;
import cn.cuilan.enums.BloodType;
import cn.cuilan.enums.Sex;
import cn.cuilan.mapper.StudentMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 学生service
 *
 * @author zhang.yan
 * @date 2020/12/21
 */
@Service
public class StudentService extends BaseService<StudentMapper, Student> {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 统计每个班级下的学生数量
     *
     * @param classRankId 班级id
     * @return 返回该班级下的学生数量
     */
    public Integer countByClassRankId(Long classRankId) {
        return studentMapper.countByClassRankId(classRankId);
    }

    /**
     * 查询学生列表
     *
     * @param name        学生姓名
     * @param sex         性别
     * @param bloodType   血型
     * @param nation      民族
     * @param gradeId     年级id
     * @param classRankId 班级id
     * @return 返回学生列表
     */
    public Page<Student> queryStudents(String name, Sex sex, BloodType bloodType, String nation,
                                       Long gradeId, Long classRankId, int pageNum, int pageSize) {
        return studentMapper.queryStudents(name, sex, bloodType, nation, gradeId, classRankId, pageNum, pageSize);
    }
}
