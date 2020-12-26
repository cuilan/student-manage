package cn.cuilan;

import cn.cuilan.entity.Examination;
import cn.cuilan.mapper.ExaminationMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class Application {

    @Resource
    private ExaminationMapper examinationMapper;

    /**
     * 启动时更新已完成的考试
     */
    @PostConstruct
    public void updateExaminationFinished() {
        // 启动时，更新结束时间小于当前时间的所有考试
        List<Examination> exams = examinationMapper.queryExamsByTime(System.currentTimeMillis());
        exams.forEach(examination -> {
            examination.setFinished(true);
            examinationMapper.updateById(examination);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
