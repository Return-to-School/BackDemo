package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ActivityManagerDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.pc.ApplyVoPC;
import cn.ncu.newmedia.backschool.pojo.vo.pc.Keys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/17 16:16
 * @description
 */
@Service
public class ApplyService {

    @Autowired
    private ApplyDao applyDao;


    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityManagerDao activityManagerDao;



    /**
     * 添加一个申请
     * @param apply
     * @param activity
     * @return
     */
    @Transactional
    public boolean apply(Apply apply, Activity activity) {

        Date start = activity.getApplyStartTime();
        Date end = activity.getApplyEndTime();
        Date now = new Date();

        if(!(now.after(start)&&now.before(end))){
            return false;
        }

        /*将当前时间设置创建时间*/
        apply.setCreateTime(now);

        return  applyDao.insert(apply)>0;
    }


    /**
     * 获取所有的申请
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllApplies(int currPage, int pageSize) {
        return PageService.getPage(currPage,pageSize,applyDao,
                e->e.listAll());
    }






    /**
     * 获取某个学生的所有的申请
     * @param studentId
     * @return
     */
    public List<ApplyVoPC>  listAllByStudentId(String studentId){
        return applyDao.getAppVoListBySid(studentId);
    }



    public Apply getApplyById(int applyId){return applyDao.getApplyById(applyId);}




    /**
     * 管理员审核报名申请
     * @param applyList
     * @param status
     * @return
     */
    @Transactional
    public boolean examine(List<Apply> applyList, ApplyStatus status) {

        try {
            applyList.forEach(e->{
                applyDao.changeApplyStatus(e.getApplyId(),status);
            });
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    /**
     * 按页返回搜索结果
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page search(Keys keys,int currPage, int pageSize) {
        return PageService.getPage(currPage,pageSize,applyDao,
                e->e.getAppVoListByKeys(keys));
    }


    public List<ApplyVoPC> search(Keys keys){
        return applyDao.getAppVoListByKeys(keys);
    }


    /**
     * 分页获取宣传组管理员下的报名申请
     * @param userId
     * @param keys
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page searchForGroup(String userId,Keys keys,int currPage,int pageSize) {
        List<ApplyVoPC> applyVoPCList = null;

        /*宣传子管理员不能进行地区查询*/
        keys.setLocation(null);


        applyVoPCList = applyDao.getAppVoListByKeys(keys);
        applyVoPCList.removeIf(e->!activityService.isManagedByGroup(e.getActivity().getActivityId(),userId));


        return PageService.getPage(currPage,pageSize,applyVoPCList);
    }


    /**
     * 不分页版本
     * @param userId
     * @param keys
     * @return
     */
    public List searchForGroup(String userId, Keys keys){
        List<ApplyVoPC> applyVoPCList = applyDao.getAppVoListByKeys(keys);

        /*宣传子管理员不能进行地区查询*/
        keys.setLocation(null);

        applyVoPCList.removeIf(e->activityManagerDao.isManagedByGroup(e.getActivity().getActivityId(),userId)==0);
        return applyVoPCList;
    }



    /*获取某个活动下通过审核的学生申请列表*/
    public List<ApplyVoPC> getPassStudentApply(int activityId) {
        return applyDao.getPassStudentApply(activityId);
    }



    /**
     * 通过学生id和活动id获取相应的申请信息
     * @param activityId
     * @param studentId
     * @return
     */
    public Apply getApplyByActIdAndSdtId(int activityId, String studentId) {
        return applyDao.getApplyByAidAndSid(activityId,studentId);
    }


    /**
     * 获取id为activityId的活动的所有申请学生的信息
     * @param currPage
     * @param pageSize
     * @param activityId
     * @return
     */
    public Page listAllApplyVoInAct(int currPage, int pageSize, Integer activityId) {
        List<ApplyVoPC> dataList = applyDao.getApplyVoPcsByActId(activityId);
        List<JSONObject> finalDataList = new ArrayList<>();

        dataList.forEach(e->{
            JSONObject obj = JSON.parseObject(JSON.toJSONString(e, SerializerFeature.WriteMapNullValue));
            obj.remove("activity");
            obj.remove("feedback");
            finalDataList.add(obj);
        });
        return PageService.getPage(currPage,pageSize,finalDataList);
    }


    /**
     * 获取活动下的所有申请
     * @param activityId
     * @return
     */
    public List<Apply> getAllByActId(Integer activityId) {
        return applyDao.getAppliesByColumn("activity_id",activityId);
    }
}
