package cn.cuilan.service;

import cn.cuilan.entity.Subject;
import cn.cuilan.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目service
 *
 * @author zhang.yan
 * @date 2020/12/23
 */
@Service
public class SubjectService extends BaseService<SubjectMapper, Subject> {

    @Resource
    private SubjectMapper subjectMapper;

    /**
     * 查询所有科目
     *
     * @param name 科目名称
     * @return 返回所有科目
     */
    public List<Subject> querySubjects(String name) {
        return subjectMapper.querySubjects(name);
    }
}
