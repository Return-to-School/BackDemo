package cn.ncu.newmedia.backschool.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoleDaoTest {
    @Autowired
    private UserRoleDao userRoleDao;

    @Test
    void testGetRolesByAccount(){
        System.out.println(userRoleDao.getRolsByAccount("along"));
    }
}