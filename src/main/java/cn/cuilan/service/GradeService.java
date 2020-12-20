package cn.cuilan.service;

import cn.cuilan.entity.Grade;
import cn.cuilan.mapper.GradeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 年级service
 *
 * @author zhang.yan
 * @date 2020/12/20
 */
@Slf4j
@Service
public class GradeService extends BaseService<GradeMapper, Grade> {

    @Resource
    private GradeMapper gradeMapper;

    /**
     * 查询所有年级，支持根据年级名称模糊查询
     *
     * @param name 年级名称
     */
    public List<Grade> queryGrades(String name) {
        return gradeMapper.queryGrades(name);
    }
}
