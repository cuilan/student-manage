package cn.cuilan.controller;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.entity.Examination;
import cn.cuilan.service.ExaminationService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 考试信息
 *
 * @author zhang.yan
 * @date 2020/12/24
 */
@RestController
public class ExaminationController {

    @Resource
    private ExaminationService examinationService;

    /**
     * 查询考试信息列表
     */
    @GetMapping("/api/exam/query")
    public Result<?> queryExams(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "finished", required = false) Boolean finished,
                                @RequestParam(value = "startTime", required = false) Long startTime,
                                @RequestParam(value = "endTime", required = false) Long endTime,
                                @RequestParam(value = "subjectId", required = false) Long subjectId,
                                @RequestParam(value = "classRankName", required = false) String classRankName,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return Result.map().data("exams", examinationService.queryExams(name, finished, startTime, endTime,
                subjectId, classRankName, pageNum, pageSize));
    }

    /**
     * 根据id查询考试
     *
     * @param id 考试id
     */
    @GetMapping("/api/exam/queryById")
    public Result<?> queryExamById(Long id) {
        return Result.map().data("exam", examinationService.getNotNull(id));
    }

    /**
     * 添加考试
     */
    @PostMapping("/api/exam/add")
    public Result<?> addExam(@RequestBody Examination examination) {
        if (StrUtil.isBlank(examination.getName())) {
            return Result.fail("请输入考试名称");
        }
        if (examination.getSubjectId() == null || examination.getSubjectId() <= 0) {
            return Result.fail("请选择科目");
        }
        if (examination.getClassRankId() == null || examination.getClassRankId() <= 0) {
            return Result.fail("请选择班级");
        }
        if (examination.getStartTime() == null || examination.getStartTime() <= 0) {
            return Result.fail("请选择考试开始时间");
        }
        if (examination.getEndTime() == null || examination.getEndTime() <= 0) {
            return Result.fail("请选择考试结束时间");
        }
        if (examination.getStartTime() >= examination.getEndTime()) {
            return Result.fail("考试开始时间不能小于结束时间");
        }
        if (examination.getTotalScore() == null || examination.getTotalScore() <= 0) {
            return Result.fail("请输入考试总分数");
        }
        if (StrUtil.isBlank(examination.getAddress())) {
            return Result.fail("请输入考试地点");
        }
        examinationService.save(examination);
        return Result.success();
    }

    /**
     * 修改考试信息
     */
    @PostMapping("/api/exam/update")
    public Result<?> updateExam(@RequestBody Examination examination) {
        Examination dbExamination = examinationService.getNotNull(examination.getId());
        if (StrUtil.isNotBlank(examination.getName())
                && !examination.getName().equals(dbExamination.getName())) {
            dbExamination.setName(examination.getName());
        }
        if (!examination.getSubjectId().equals(dbExamination.getSubjectId())) {
            dbExamination.setSubjectId(examination.getSubjectId());
        }
        if (!examination.getClassRankId().equals(dbExamination.getClassRankId())) {
            dbExamination.setClassRankId(examination.getClassRankId());
        }
        if (!examination.getStartTime().equals(dbExamination.getStartTime())) {
            dbExamination.setStartTime(examination.getStartTime());
        }
        if (!examination.getEndTime().equals(dbExamination.getEndTime())) {
            dbExamination.setEndTime(examination.getEndTime());
        }
        if (dbExamination.getStartTime() >= dbExamination.getEndTime()) {
            return Result.fail("考试开始时间不能小于结束时间");
        }
        if (!examination.getTotalScore().equals(dbExamination.getTotalScore())) {
            dbExamination.setTotalScore(examination.getTotalScore());
        }
        if (StrUtil.isNotBlank(examination.getAddress())
                && !examination.getAddress().equals(dbExamination.getAddress())) {
            dbExamination.setAddress(examination.getAddress());
        }
        if (StrUtil.isNotBlank(examination.getRemark())
                && !examination.getRemark().equals(dbExamination.getRemark())) {
            dbExamination.setRemark(examination.getRemark());
        }
        examinationService.updateById(dbExamination);
        return Result.success();
    }

    /**
     * 删除考试
     */
    @PostMapping("/api/exam/delete")
    public Result<?> deleteExam(Long id) {
        // 根据id查询一下，如果没有，则会抛出异常
        examinationService.getNotNull(id);
        examinationService.removeById(id);
        return Result.success();
    }

}
