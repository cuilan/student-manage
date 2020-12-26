package cn.cuilan.service;

import cn.cuilan.entity.Examination;
import cn.cuilan.entity.Score;
import cn.cuilan.entity.Student;
import cn.cuilan.mapper.ScoreMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 成绩service
 *
 * @author zhang.yan
 * @date 2020/12/26
 */
@Service
public class ScoreService extends BaseService<ScoreMapper, Score> {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private StudentService studentService;

    @Resource
    private ExaminationService examinationService;

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
    public Page<Score> queryScores(Long studentId, String studentName, Long subjectId, Long classRankId,
                                   Integer score, int pageNum, int pageSize) {
        return scoreMapper.queryScores(studentId, studentName, subjectId,
                classRankId, score, pageNum, pageSize);
    }

    /**
     * 添加考试成绩
     *
     * @param studentId  学生id
     * @param examId     考试id
     * @param scoreValue 分数
     */
    public void addScore(Long studentId, Long examId, Integer scoreValue) {
        Student student = studentService.getNotNull(studentId);
        Examination examination = examinationService.getNotNull(examId);

        Score score = new Score();
        score.setStudentId(studentId);
        score.setStudentName(student.getName());
        score.setExaminationId(examId);
        score.setSubjectId(examination.getSubjectId());
        score.setClassRankId(examination.getClassRankId());
        score.setScore(scoreValue);
        scoreMapper.insert(score);
    }
}
