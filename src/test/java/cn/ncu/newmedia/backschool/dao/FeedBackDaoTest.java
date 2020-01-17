package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.FeedBack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedBackDaoTest {

    @Autowired
    private FeedBackDao feedBackDao;

    @Test
    void testInsert(){
        FeedBack feedBack = new FeedBack();

        feedBack.setLevel(5);
        feedBack.setFilePath("/测试");
        feedBack.setApply(1);
        System.out.println(feedBackDao.save(feedBack));
    }

}