package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.StudentDao;
import cn.ncu.newmedia.backschool.dao.UserDao;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/12 21:10
 * @description
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean hasUser (String username){
        return userDao.userExist(username)>0;
    }

}
