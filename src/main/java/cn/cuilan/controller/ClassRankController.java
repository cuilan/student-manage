package cn.cuilan.controller;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.service.ClassRankService;
import cn.cuilan.service.StudentService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhang.yan
 * @date 2020/12/20
 */
@Slf4j
@RestController
public class ClassRankController {

    @Resource
    private ClassRankService classRankService;

    @Resource
    private StudentService studentService;

    /**
     * 查询所有班级
     *
     * @param name    班级名称
     * @param gradeId 年级id
     */
    @GetMapping("/api/classRank/query")
    public Result<?> queryClassRanks(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "gradeId", required = false) Long gradeId,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if (StrUtil.isBlank(name)) {
            name = null;
        }
        return Result.map().data("classRanks", classRankService.queryClassRanks(name, gradeId, pageNum, pageSize));
    }

    /**
     * 根据id查询班级
     *
     * @param id 班级id
     */
    @GetMapping("/api/classRank/queryById")
    public Result<?> queryClassRankById(Long id) {
        return Result.map().data("classRank", classRankService.getNotNull(id));
    }

    /**
     * 添加班级
     */
    @PostMapping("/api/classRank/add")
    public Result<?> addClassRank(@RequestBody ClassRank classRank) {
        if (StrUtil.isBlank(classRank.getName())) {
            return Result.fail("请输入班级名称");
        }
        if (classRank.getGradeId() == null || classRank.getGradeId() <= 0) {
            return Result.fail("请选择年级");
        }
        classRankService.save(classRank);
        return Result.success();
    }

    /**
     * 修改班级
     */
    @PostMapping("/api/classRank/update")
    public Result<?> updateClassRank(@RequestBody ClassRank classRank) {
        ClassRank dbClassRank = classRankService.getNotNull(classRank.getId());
        if (StrUtil.isNotBlank(classRank.getName())) {
            dbClassRank.setName(classRank.getName());
        }
        if (classRank.getGradeId() != null && classRank.getGradeId() > 0) {
            dbClassRank.setGradeId(classRank.getGradeId());
        }
        classRankService.updateById(dbClassRank);
        return Result.success();
    }

    /**
     * 删除班级
     */
    @PostMapping("/api/classRank/delete")
    public Result<?> deleteClassRank(Long id) {
        // 根据id查询一下，如果没有，则会抛出异常
        classRankService.getNotNull(id);
        // 校验该班级下是否有学生
        Integer studentNum = studentService.countByClassRankId(id);
        if (studentNum > 0) {
            return Result.fail("该班级下有学生，不能删除");
        }
        classRankService.removeById(id);
        return Result.success();
    }
}
