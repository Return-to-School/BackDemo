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


    public boolean hasUser (String username){
        return userDao.userExist(username)>0;
    }

    public User getUsersByAccount(String account){
        return userDao.getUserByAccount(account);
    }

//    @Transactional
//    public int addGroupManager(User user) {
//        if (user.getActivities()==null)
//            return 0;
//
//        int cnt = 0;
//        for (Activity activity:user.getActivities()) {
//            cnt +=activityManagerDao.add(user.getId(),activity.getId());
//        }
//        return cnt;
//    }

    public Page getAll(int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,userDao,
                e->e.listAll((currPage-1)*pageSize,pageSize),
                e->e.getAllCnt());
    }

    @Transactional
    public boolean deleteUser(int userId) {

        try {

            Student student = studentDao.getStudentByColumn("user_id", userId);

            List<Apply> applyList = applyDao.getAppliesByColumn("student_id", student.getId());
            /*级联删除申请*/
            if (applyList != null) {
                applyList.forEach(e->applyDao.delete(e.getId()));
            }

            /*然后才能删除student，有外键约束*/
            if (student != null) {
                studentDao.delete(student.getId());
            }

            List<Feedback> feedbackList  = new ArrayList<>();
            applyList.forEach(e->feedbackList.add(feedBackDao.getFeedbackByColumn("apply_id",e.getId())));

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

        int cnt = userDao.insert(user);//保存该用户

        cnt += studentDao.updateUserId(user.getId(),user.getAccount());//根据学号（用户账号)更新基础数据库中的外键userid
        return cnt==2;

    }



    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }



    public User getGroupByLoc(String loc) {
        return userDao.getGroupManagerByLoc(loc);
    }

    @Transactional
    public boolean changePassword(User user) {
        return userDao.update(user)>0;
    }
}
