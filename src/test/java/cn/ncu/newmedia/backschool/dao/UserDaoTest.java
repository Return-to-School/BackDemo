package cn.ncu.newmedia.backschool.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    void testGetPasswordByUsername(){
        System.out.println(userDao.getPasswordByAccount("along"));
    }


    @Test
    void testGetGroupManager(){
        System.out.println(userDao.getGroupManagerById(2).toString());
    }
}