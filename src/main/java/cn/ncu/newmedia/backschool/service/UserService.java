package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.*;
import cn.ncu.newmedia.backschool.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private ApplyDao applyDao;

    @Autowired
    private FeedBackDao feedBackDao;

    @Autowired
    private ActivityManagerDao activityManagerDao;

    public boolean hasUser (String username){
        return userDao.userExist(username)>0;
    }

    public User getUsersByColumn(String account){
        return userDao.getUserByAccount(account);
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


        try {

            Student student = studentDao.getStudentByColumn("user_id", userId);

            List<Apply> applyList = applyDao.getAppliesByColumn("student_id", student.getId());
            if (applyList != null) {
                applyList.forEach(e->applyDao.delete(e.getId()));
            }

            if (student != null) {
                studentDao.delete(student.getId());
            }


            List<Feedback> feedbackList  = new ArrayList<>();
            applyList.forEach(e->feedbackList.addAll(feedBackDao.getFeedbackByColumn("apply_id",e.getId())));

            if (feedbackList != null) {
                feedbackList.forEach(e->feedBackDao.delete(e.getId()));
            }

            userDao.deleteManager(userId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return userDao.delete(userId)>0;
    }

    @Transactional
    public boolean addUser(User user) {
        return userDao.insert(user)>0;
    }

    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
