package cn.cuilan.controller;

import cn.cuilan.entity.Student;
import cn.cuilan.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生管理
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Slf4j
@RestController
public class StudentController {

    /**
     * 查询学生列表
     */
    @GetMapping("/api/student/query")
    public Result<?> queryStudents() {

        return Result.success();
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
