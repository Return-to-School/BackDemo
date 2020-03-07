package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ActivityManagerDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import cn.ncu.newmedia.backschool.pojo.vo.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ActivityDao activityDao;

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
                e->e.listAll((currPage-1)*pageSize,pageSize),
                e->e.getApplyCnt());
    }

    /**
     * 分页获取某个活动下的所有申请
     * @param activityId
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAllByActivityId(int activityId,int currPage,int pageSize) {
        return PageService.getPage(currPage, pageSize, applyDao,
                e -> e.getAppliesByActId(activityId, (currPage - 1) * pageSize, pageSize),
                e -> e.getApplyCntInAct(activityId));
    }




    /**
     * 获取某个学生的所有的申请
     * @param studentId
     * @return
     */
    public List<Apply> listAllByStudentId(int studentId){
        return applyDao.getAppliesByColumn("student_id",studentId);
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
                applyDao.changeApplyStatus(e.getId(),status);
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
                e->e.getAppVoForSuper(keys,(currPage-1)*pageSize,pageSize),
                e->e.getAppVoForSuperCnt(keys));
    }

    public List<ApplyVo> search(Keys keys){
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
        List<ApplyVo> applyVoList = applyDao.getAppVoListByKeys(keys);
        applyVoList.removeIf(e->activityManagerDao.isManagedByGroup(e.getActivity().getId(),userId)==0);

        int totalCount = applyVoList.size();
        int st = (currPage-1)*pageSize;
        int ed = st+pageSize-1>totalCount?totalCount:st+pageSize-1;
        applyVoList = applyVoList.subList(st,ed);
        return new Page(currPage,pageSize,totalCount,applyVoList);
    }


    /**
     * 不分页版本
     * @param userId
     * @param keys
     * @return
     */
    public List searchForGroup(String userId, Keys keys){
        List<ApplyVo> applyVoList = applyDao.getAppVoListByKeys(keys);
        applyVoList.removeIf(e->activityManagerDao.isManagedByGroup(e.getActivity().getId(),userId)==0);
        return applyVoList;
    }

    /*获取某个活动下通过审核的学生申请列表*/
    public Page getPassStudentApply(int activityId, int currPage, int pageSize) {
        return PageService.getPage(currPage,pageSize,applyDao,
                e->e.getPassStudentApply(activityId,(currPage-1)*pageSize,pageSize),
                e->e.getPassStudentCnt(activityId));
    }

    public Apply getApplyByActIdAndSdtId(int activityId, int studentId) {
        return applyDao.getApply(activityId,studentId);
    }
}
