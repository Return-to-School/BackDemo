package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/13 20:48
 * @description
 */
@Service
public class ActivityService {

    @Autowired
    private ActivityDao activityDao;

    public Activity getActivityById(int id) {
        return activityDao.getActivityByValue("activity_id",id+"");
    }

    @Transactional
    public boolean saveActivity(Activity activity) {
        return activityDao.insert(activity)>0;
    }

    @Transactional
    public boolean updateActivity(Activity activity) {
        return activityDao.update(activity)>0;
    }

    public List<Activity> listAllActivities() {
        return activityDao.listAll();
    }

    @Transactional
    public boolean deleteActivity(int activityId) {
        return activityDao.delete(activityId)>0;
    }

    public List<Activity> filterActivityByLocation(String key) {
        return activityDao.filterActivityByColumn("location",key);
    }

    public List<Activity> filterActivityByName(String key) {
        return activityDao.filterActivityByColumn("activity_name",key);
    }

    public List<Activity> getGroupActivityList(int userId) {
        return activityDao.getActivityByGroupManagerId(userId);
    }

    public List<Activity> listAllUnderwayAct() {
        return activityDao.listAllUnderwayAct();
    }

    public List<Activity> listGroupUnderwayAct(int managerId) {
        return activityDao.listGroupUnderwayAct(managerId);
    }
}
