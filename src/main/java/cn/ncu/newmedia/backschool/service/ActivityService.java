package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.controller.ActivityController;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.dao.FeedBackDao;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.function.Function;

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

    /**
     * 通过id获取活动
     * @param id
     * @return
     */
    public Activity getActivityById(int id) {
        return activityDao.getActivityByColumn("activity_id",id+"");
    }

    /**
     * 添加一条数据
     * @param activity
     * @return
     */
    @Transactional
    public boolean saveActivity(Activity activity) {
        return activityDao.insert(activity)>0;
    }

    /**
     * 更新一条数据
     * @param activity
     * @return
     */
    @Transactional
    public boolean updateActivity(Activity activity) {
        return activityDao.update(activity)>0;
    }

    /**
     * 通过分页获取活动
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllActivities(Integer currPage, Integer pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e -> e.listAll((currPage-1)*pageSize,pageSize),e->e.getTotalCnt());
    }

    /**
     * 删除一个活动
     * @param activityId
     * @return
     */
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

    /**
     * 搜索某个地区的活动
     * @param key
     * @return
     */
    public List<Activity> filterActivityByLocation(String key) {
        return activityDao.filterActivityByColumn("location",key);
    }

    /**
     * 搜索某个名称的活动
     * @param key
     * @return
     */
    public List<Activity> filterActivityByName(String key) {
        return activityDao.filterActivityByColumn("activity_name",key);
    }

    /**
     * 分页获取管理员下的活动
     * @param userId
     * @return
     */
    public List<Activity> getGroupActivityList(int userId) {
        return activityDao.getActivityByGroupManagerId(userId);
    }


    /**
     * 获取所有正在进行的活动
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllUnderwayAct(Integer currPage,Integer pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,e->e.listAllUnderwayAct((currPage-1)*pageSize,pageSize),
                e->e.getUnderwayTolAct());
    }

    /**
     * 宣传组管理员获取正在进行的活动
     * @param currPage
     * @param pageSize
     * @param managerId
     * @return
     */
    public Page listGroupUnderwayAct(int currPage,int pageSize,int managerId) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.listGroupUnderwayAct((currPage-1)*pageSize,pageSize,managerId),
                e->e.getGroupUnderwayTolAct());
    }

    /**
     * 通过活动的id验证该活动是不是被userid的用户所管理
     * @param activityId
     * @param userId
     * @return
     */
    public boolean isManagedByGroup(int activityId, String userId) {
        return activityDao.isManagedByGroup(activityId,userId)>0;
    }

}
