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


//    @Transactional
//    public int addGroupManager(User user) {
//        if (user.getActivities()==null)
//            return 0;
//
//        int cnt = 0;
//        for (Activity activity:user.getActivities()) {
//            cnt +=activityManagerDao.add(user.getStudentId(),activity.getStudentId());
//        }
//        return cnt;
//    }

    public Page getAll(int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,userDao,
                e->e.listAll());
    }

    @Transactional
    public boolean deleteUser(String userId) {

        try {

            Student student = studentDao.getStudentByColumn("student_id", userId);

            List<Apply> applyList = applyDao.getAppliesByColumn("student_id", student.getStudentId());

            /*级联删除申请*/
            if (applyList != null) {
                applyList.forEach(e->applyDao.delete(e.getApplyId()));
            }


            List<Feedback> feedbackList  = new ArrayList<>();
            applyList.forEach(e->feedbackList.add(feedBackDao.getFeedbackByColumn("apply_id",e.getApplyId())));

            if (feedbackList != null) {
                feedbackList.forEach(e->feedBackDao.delete(e.getFeedbackId()));
            }

            /*管理员的映射关系也需要删除*/
            userDao.deleteManager(userId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return userDao.delete(userId)>0;
    }

    @Transactional
    public boolean addUser(User user) {

        return  userDao.insert(user)>0;//保存该用户

    }



    public User getUserById(String userId) {
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
