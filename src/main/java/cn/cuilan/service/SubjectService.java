package cn.cuilan.service;

import cn.cuilan.entity.Subject;
import cn.cuilan.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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


}
