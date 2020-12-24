package cn.cuilan.controller;

import cn.cuilan.entity.Subject;
import cn.cuilan.service.SubjectService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhang.yan
 * @date 2020/12/24
 */
@RestController
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    /**
     * 查询所有科目
     */
    @GetMapping("/api/subject/query")
    public Result<?> querySubjects(@RequestParam(value = "name", required = false) String name) {
        return Result.map().data("subjects", subjectService.querySubjects(name));
    }

    /**
     * 根据id查询科目
     *
     * @param id 科目id
     */
    @GetMapping("/api/subject/queryById")
    public Result<?> querySubjectById(Long id) {
        return Result.map().data("subject", subjectService.getNotNull(id));
    }

    /**
     * 添加科目
     */
    @PostMapping("/api/subject/add")
    public Result<?> addSubject(@RequestBody Subject subject) {
        if (StrUtil.isBlank(subject.getName())) {
            return Result.fail("科目名称不能为空");
        }
        subjectService.save(subject);
        return Result.success();
    }

    /**
     * 更新科目
     */
    @PostMapping("/api/subject/update")
    public Result<?> updateSubject(@RequestBody Subject subject) {
        Subject dbSubject = subjectService.getNotNull(subject.getId());
        if (StrUtil.isNotBlank(subject.getName())) {
            dbSubject.setName(subject.getName());
        }
        if (subject.getExam() != null) {
            dbSubject.setExam(subject.getExam());
        }
        if (subject.getOpen() != null) {
            dbSubject.setOpen(subject.getOpen());
        }
        if (StrUtil.isNotBlank(subject.getRemark())) {
            dbSubject.setRemark(subject.getRemark());
        }
        subjectService.updateById(dbSubject);
        return Result.success();
    }

    /**
     * 删除科目
     */
    @PostMapping("/api/subject/delete")
    public Result<?> deleteStudent(Long id) {
        // 根据id查询一下，如果没有，则会抛出异常
        subjectService.getNotNull(id);
        subjectService.removeById(id);
        return Result.success();
    }

}
