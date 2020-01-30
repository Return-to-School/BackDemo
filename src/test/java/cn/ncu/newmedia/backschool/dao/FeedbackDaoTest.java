package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackDaoTest {

    @Autowired
    private FeedBackDao feedBackDao;

//    @Test
//    void testInsert(){
//        Feedback feedBack = new Feedback();
//
//        feedBack.setLevel(5);
//        feedBack.setFilePath("/测试");
//        feedBack.setApply(1);
//        System.out.println(feedBackDao.save(feedBack));
//    }

    @Test
    void testGet(){

    }

}