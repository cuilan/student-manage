package cn.cuilan.controller;

import cn.cuilan.entity.ClassRank;
import cn.cuilan.entity.Grade;
import cn.cuilan.entity.Student;
import cn.cuilan.enums.BloodType;
import cn.cuilan.enums.Sex;
import cn.cuilan.service.ClassRankService;
import cn.cuilan.service.GradeService;
import cn.cuilan.service.StudentService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
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

    @Resource
    private GradeService gradeService;

    @Resource
    private ClassRankService classRankService;

    /**
     * 查询学生列表
     */
    @GetMapping("/api/student/query")
    public Result<?> queryStudents(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "sex", required = false) Sex sex,
                                   @RequestParam(value = "bloodType", required = false) BloodType bloodType,
                                   @RequestParam(value = "nation", required = false) String nation,
                                   @RequestParam(value = "gradeId", required = false) Long gradeId,
                                   @RequestParam(value = "classRankId", required = false) Long classRankId,
                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if (StrUtil.isBlank(name)) {
            name = null;
        }
        if (StrUtil.isBlank(nation)) {
            nation = null;
        }
        return Result.map().data("students", studentService.queryStudents(name, sex, bloodType,
                nation, gradeId, classRankId, pageNum, pageSize));
    }

    /**
     * 根据id查询学生
     *
     * @param id 学生id
     */
    @GetMapping("/api/student/queryById")
    public Result<?> querystudentById(Long id) {
        Student student = studentService.getNotNull(id);
        Grade grade = gradeService.getNotNull(student.getGradeId());
        student.setGradeName(grade.getName());
        ClassRank classRank = classRankService.getNotNull(student.getClassRankId());
        student.setClassRankName(classRank.getName());
        return Result.map().data("student", student);
    }

    /**
     * 添加学生
     */
    @PostMapping("/api/student/add")
    public Result<?> addStudent(@RequestBody Student student) {
        if (StrUtil.isBlank(student.getName())) {
            return Result.fail("姓名不能为空");
        }
        if (student.getSex() == null) {
            return Result.fail("性别不能为空");
        }
        if (student.getAge() == null || student.getAge() <= 0) {
            return Result.fail("年龄不合法");
        }
        if (student.getBloodType() == null) {
            return Result.fail("血型不能为空");
        }
        if (StrUtil.isBlank(student.getNation())) {
            return Result.fail("民族不能为空");
        }
        if (StrUtil.isBlank(student.getAddress())) {
            return Result.fail("家庭住址不能为空");
        }
        if (student.getGradeId() == null) {
            return Result.fail("年级不能为空");
        }
        if (student.getClassRankId() == null) {
            return Result.fail("班级不能为空");
        }
        studentService.save(student);
        return Result.success();
    }

    /**
     * 修改学生
     */
    @PostMapping("/api/student/update")
    public Result<?> updateStudent(@RequestBody Student student) {
        Student dbStudent = studentService.getNotNull(student.getId());
        if (StrUtil.isNotBlank(student.getName())) {
            dbStudent.setName(student.getName());
        }
        if (student.getSex() != null) {
            dbStudent.setSex(student.getSex());
        }
        if (student.getAge() != null && student.getAge() > 0) {
            dbStudent.setAge(student.getAge());
        }
        if (student.getBloodType() != null) {
            dbStudent.setBloodType(student.getBloodType());
        }
        if (StrUtil.isNotBlank(student.getNation())) {
            dbStudent.setNation(student.getNation());
        }
        if (StrUtil.isNotBlank(student.getAddress())) {
            dbStudent.setAddress(student.getAddress());
        }
        studentService.updateById(dbStudent);
        return Result.success();
    }

    /**
     * 删除学生
     */
    @PostMapping("/api/student/delete")
    public Result<?> deleteStudent(Long id) {
        // 根据id查询一下，如果没有，则会抛出异常
        studentService.getNotNull(id);
        studentService.removeById(id);
        return Result.success();
    }

}
