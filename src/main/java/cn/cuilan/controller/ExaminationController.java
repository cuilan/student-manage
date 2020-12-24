package cn.cuilan.controller;

import cn.cuilan.service.ExaminationService;
import cn.cuilan.utils.result.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startTime", required = false) Date startTime,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endTime", required = false) Date endTime,
                                @RequestParam(value = "subjectId", required = false) Long subjectId,
                                @RequestParam(value = "classRankId", required = false) Long classRankId,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return Result.map().data("exams", examinationService.queryExams(name, startTime, endTime,
                subjectId, classRankId, pageNum, pageSize));
    }

}
