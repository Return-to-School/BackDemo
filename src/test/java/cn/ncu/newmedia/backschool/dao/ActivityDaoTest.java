package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

import java.util.Date;

@SpringBootTest
class ActivityDaoTest {

    @Autowired
    private ActivityDao activityDao;

    private Activity activity = new Activity();

    @PostConstruct
    void init(){
        activity.setName("回母校");
        activity.setApplyStartTime(new Date());
        activity.setApplyEndTime(new Date());
        activity.setFeedbackStartTime(new Date());
        activity.setFeedbackEndTime(new Date());
        activity.setCreator("along");
        activity.setCreateTime(new Date());
        activity.setContent("形象大使");
        activity.setFilePath("/home/along/Documents/");
    }

    @Test
    void testInsert(){
        activityDao.insert(activity);
    }

    @Test
    void testUpdate(){
        activity.setId(2);
        activity.setApplyEndTime(new Date());
        activity.setContent("测试。。。");
        System.out.println(activityDao.update(activity));
    }

    @Test
    void testFind(){
        System.out.println(activityDao.getActivityByValue("activity_id","1"));
    }

}