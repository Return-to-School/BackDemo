package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ActivityManagerDao;
import cn.ncu.newmedia.backschool.dao.StudentDao;
import cn.ncu.newmedia.backschool.dao.UserDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ActivityManagerDao activityManagerDao;

    public boolean hasUser (String username){
        return userDao.userExist(username)>0;
    }

    public List<User> getUserByColumn(String column,Object value){
        return userDao.getUserByColumn(column,value);
    }

    @Transactional
    public int addGroupManager(User user) {
        if (user.getActivities()==null)
            return 0;

        int cnt = 0;
        for (Activity activity:user.getActivities()) {
            cnt +=activityManagerDao.add(user.getId(),activity.getId());
        }
        return cnt;
    }

    public List<User> getAll() {
        return userDao.listAll();
    }

    @Transactional
    public boolean deleteUser(int userId) {

        Student student = studentDao.getStudentByColumn("user_id",userId);

        if(student!=null){
            studentDao.delete(student.getId());
        }

        return userDao.delete(userId)>0;
    }
}
