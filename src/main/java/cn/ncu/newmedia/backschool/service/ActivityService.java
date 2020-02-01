package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.controller.ActivityController;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.dao.FeedBackDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/13 20:48
 * @description
 */
@Service
public class ActivityService {

    static final String FILEPATH = "e:/所有文件";

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ApplyDao  applyDao;

    @Autowired
    private FeedBackDao feedBackDao;

    public Activity getActivityById(int id) {
        return activityDao.getActivityByColumn("activity_id",id+"");
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

        /*删除活动的管理者*/
        activityDao.deleteManagerByActId(activityId);

        List<Apply> applyList = applyDao.getAppliesByColumn("activity_id",activityId);

        /* 删除所有相关的反馈*/
        applyList.forEach(e->feedBackDao.deleteByApplyId(e.getId()));

        /*删除所有申请*/
        applyDao.deleteByActId(activityId);

        /*删除文件*/
        File director = new File(FILEPATH+activityDao.getActivityById(activityId).getFilePath());
        FolderDelUtils.deleteFolder(director);

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

    public boolean isManagedByGroup(int id, String userId) {
        return activityDao.isManagedByGroup(id,userId)>0;
    }
}
