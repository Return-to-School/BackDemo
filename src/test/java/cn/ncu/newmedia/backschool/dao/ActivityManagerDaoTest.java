package cn.ncu.newmedia.backschool.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityManagerDaoTest {

    @Autowired
    private ActivityManagerDao activityManagerDao;

    @Test
    void testAdd(){
        System.out.println(activityManagerDao.add(1,4));
    }
}