package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Enumeration.RoleEnum;
import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.dao.*;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.User;
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
    private ActivityManagerDao activityManagerDao;

    @Autowired
    private ApplyDao  applyDao;

    @Autowired
    private FeedBackDao feedBackDao;

    @Autowired
    private UserService userService;

    /**
     * 通过id获取活动
     * @param id
     * @return
     */
    public Activity getActivityById(int id) {
        return activityDao.getActivityByColumn("activity_id",id+"");
    }





    /*
     * 添加活动并直接保存其管理员
     * @param activity
     * @return
     */
    @Transactional
    public boolean saveActivity(String userId,Activity activity) {

        /*验证管理员是不是在自己的地区创建活动*/
        String loc = activity.getLocation();
        String province = loc.substring(0,loc.indexOf("-"));


        User manager =  userService.getUserById(userId);

        /*超级管理员可以在所有地区创建活动*/
        if(manager.getRole() == RoleEnum.SUPERMANAGER||
                (manager.getRole() == RoleEnum.GROUPMANAGER&&manager.getLoc().equals(province))){

            int cnt = activityDao.insert(activity);
            cnt += activityManagerDao.add(manager.getUserId(),activity.getActivityId());
            return cnt==2;
        }

        return false;


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
                e -> e.listAll());
    }



    /**
     * 删除一个活动
     * @param activityId
     * @return
     */
    @Transactional
    public boolean deleteActivity(int activityId) {

        /*删除活动的管理者*/
        activityManagerDao.deleteManagerByActId(activityId);

        List<Apply> applyList = applyDao.getAppliesByColumn("activity_id",activityId);

        /* 删除所有相关的反馈*/
        applyList.forEach(e->feedBackDao.deleteByApplyId(e.getApplyId()));

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
    public Page filterActivityByLocation(String key,int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.filterActivityByColumn("location",key));
    }

    /**
     * 搜索某个名称的活动
     * @param key
     * @return
     */
    public Page filterActivityByName(String key,int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.filterActivityByColumn("activity_name",key));
    }


    /**
     * 分页获取管理员下的活动
     * @param userId
     * @return
     */
    public Page getGroupActivityList(int currPage,int pageSize,int userId) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.getActivitiesByGroupManagerId(userId));
    }


    /**
     * 获取所有正在进行的活动
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllUnderwayAct(Integer currPage,Integer pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,e->e.listAllUnderwayAct());
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
                e->e.listGroupUnderwayAct((managerId)));
    }

    /**
     * 通过活动的id验证该活动是不是被userid的用户所管理
     * @param activityId
     * @param userId
     * @return
     */
    public boolean isManagedByGroup(int activityId, String userId) {
        return activityManagerDao.isManagedByGroup(activityId,userId)>0;
    }


    /**
     * 获取所有的历史活动
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllHistoryAct(Integer currPage, Integer pageSize) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.listAllHistoryAct());
    }


    /**
     * 获取管理员的所有的历史活动
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listGroupHistoryAct(Integer currPage, Integer pageSize,String managerId) {
        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.listGroupHistoryAct(managerId));
    }

    /**
     * 通过活动的名称进行查询
     * @param name
     * @return
     */
    public Activity getActivityByName(String name) {
        return activityDao.getActivityByColumn("activity_name",name);
    }


    /**
     * 全局搜索模糊查询
     * @param key
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page search(String key, int currPage, int pageSize) {

        return PageService.getPage(currPage,pageSize,activityDao,
                e->e.search(key));

    }
}
