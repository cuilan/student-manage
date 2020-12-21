package cn.cuilan.controller;

import cn.cuilan.entity.Student;
import cn.cuilan.service.StudentService;
import cn.cuilan.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 学生管理
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Slf4j
@RestController
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 查询学生列表
     */
    @GetMapping("/api/student/query")
    public Result<?> queryStudents(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "classRankId", required = false) Long classRankId,
                                   @RequestParam(value = "gradeId", required = false) Long gradeId,
                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return Result.map().data("students", studentService.queryStudents(name, gradeId, classRankId, pageNum, pageSize));
    }

    /**
     * 添加学生
     */
    @PostMapping("/api/student/add")
    public Result<?> addStudent(@RequestBody Student student) {

        return Result.success();
    }

    /**
     * 修改学生
     */
    @PostMapping("/api/student/update")
    public Result<?> updateStudent() {

        return Result.success();
    }

    /**
     * 删除学生
     */
    @PostMapping("/api/student/delete")
    public Result<?> deleteStudent() {

        return Result.success();
    }

}
