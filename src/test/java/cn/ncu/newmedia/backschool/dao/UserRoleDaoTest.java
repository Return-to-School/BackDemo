package cn.ncu.newmedia.backschool.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRoleDaoTest {
    @Autowired
    private UserRoleDao userRoleDao;

    @Test
    void testGetRolesByAccount(){
        System.out.println(userRoleDao.getRolesByAccount("along"));
    }
}