package cn.cuilan.controller;

import cn.cuilan.entity.Grade;
import cn.cuilan.service.GradeService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 年级controller
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Slf4j
@RestController
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 查询所有年级
     */
    @GetMapping("/api/grade/query")
    public Result<?> queryGrades(@RequestParam(value = "name", required = false) String name) {
        if (StrUtil.isBlank(name)) {
            name = null;
        }
        return Result.map().data("grades", gradeService.queryGrades(name));
    }

    /**
     * 根基id查询年级
     *
     * @param id 年级id
     */
    @GetMapping("/api/grade/queryById")
    public Result<?> queryGradeBydId(Long id) {
        return Result.map().data("grade", gradeService.getNotNull(id));
    }

    /**
     * 添加年级
     */
    @PostMapping("/api/grade/add")
    public Result<?> addGrade(@RequestBody Grade grade) {
        if (StrUtil.isBlank(grade.getName())) {
            return Result.fail("请输入年级名称");
        }
        gradeService.save(grade);
        return Result.success();
    }

    /**
     * 修改年级
     */
    @PostMapping("/api/grade/update")
    public Result<?> updateGrade(@RequestBody Grade grade) {
        if (StrUtil.isBlank(grade.getName())) {
            return Result.fail("请输入年级名称");
        }
        Grade dbGrade = gradeService.getNotNull(grade.getId());
        dbGrade.setName(grade.getName());
        gradeService.updateById(dbGrade);
        return Result.success();
    }

    /**
     * 删除年级
     */
    @PostMapping("/api/grade/delete")
    public Result<?> deleteGrade(Long id) {
        // 根据id查询一下，如果没有，则会抛出异常
        gradeService.getNotNull(id);
        gradeService.removeById(id);
        return Result.success();
    }
}
