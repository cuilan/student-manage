package cn.cuilan.controller;

import cn.cuilan.service.ClassRankService;
import cn.cuilan.utils.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
