package cn.cuilan.controller;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.entity.Examination;
import cn.cuilan.entity.Score;
import cn.cuilan.entity.Subject;
import cn.cuilan.service.ClassRankService;
import cn.cuilan.service.ExaminationService;
import cn.cuilan.service.ScoreService;
import cn.cuilan.service.SubjectService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 成绩
 *
 * @author zhang.yan
 * @date 2020/12/26
 */
@RestController
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private ClassRankService classRankService;

    @Resource
    private ExaminationService examinationService;

    /**
     * 查询成绩列表
     */
    @GetMapping("/api/score/query")
    public Result<?> queryScores(@RequestParam(value = "studentName", required = false) String studentName,
                                 @RequestParam(value = "subjectId", required = false) Long subjectId,
                                 @RequestParam(value = "classRankId", required = false) Long classRankId,
                                 @RequestParam(value = "score", required = false) Integer score,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long studentId = null;
        // 如果studentName为数字，则将学生名称赋值为空
        if (StrUtil.isNotBlank(studentName) && NumberUtil.isLong(studentName)) {
            studentId = Long.parseLong(studentName);
            studentName = null;
        }
        return Result.map().data("scores", scoreService.queryScores(studentId, studentName, subjectId,
                classRankId, score, pageNum, pageSize));
    }


    /**
     * 根据id查询成绩
     *
     * @param id 成绩id
     */
    @GetMapping("/api/score/queryById")
    public Result<?> queryScoreById(Long id) {
        Score score = scoreService.getNotNull(id);

        Subject subject = subjectService.getNotNull(score.getSubjectId());
        score.setSubjectName(subject.getName());

        ClassRank classRank = classRankService.getNotNull(score.getClassRankId());
        score.setClassRankName(classRank.getName());

        Examination examination = examinationService.getNotNull(score.getExaminationId());
        score.setExaminationName(examination.getName());
        return Result.map().data("score", score);
    }

    /**
     * 添加学生成绩
     */
    @PostMapping("/api/score/add")
    public Result<?> addScore(@RequestParam("studentId") Long studentId,
                              @RequestParam("examId") Long examId,
                              @RequestParam("score") Integer score) {
        if (studentId == null || studentId <= 0) {
            return Result.fail("学生id不正确");
        }
        if (examId == null || examId <= 0) {
            return Result.fail("考试id不正确");
        }
        if (score == null || score <= 0) {
            return Result.fail("分数数值不正确");
        }
        scoreService.addScore(studentId, examId, score);
        return Result.success();
    }

    /**
     * 修改学生成绩
     */
    @PostMapping("/api/score/update")
    public Result<?> updateScore(@RequestParam("id") Long id,
                                 @RequestParam("score") Integer score) {
        Score dbScore = scoreService.getNotNull(id);
        if (score != null && score > 0) {
            dbScore.setScore(score);
            scoreService.updateById(dbScore);
        }
        return Result.success();
    }
}
