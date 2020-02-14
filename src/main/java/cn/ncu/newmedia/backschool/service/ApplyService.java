package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ActivityManagerDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import org.apache.ibatis.annotations.Param;
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

    @Transactional
    public boolean apply(Apply apply, Activity activity) {

        Date start = activity.getApplyStartTime();
        Date end = activity.getApplyEndTime();
        Date now = new Date();

        if(!(now.after(start)&&now.before(end))){
            return false;
        }

        apply.setCreateTime(now);

        return  applyDao.insert(apply)>0;
    }

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

    public List<Apply> listAllByStudentId(int studentId){
        return applyDao.getAppliesByColumn("student_id",studentId);
    }

    public Apply getApplyById(int applyId){return applyDao.getApplyById(applyId);}

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
     * @param column
     * @param value
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page search(String column, String value,int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,applyDao,
                e->e.getAppVoForSuper(column,value,(currPage-1)*pageSize,pageSize),
                e->e.getAppVoForSuperCnt(column,value));
    }

    public Page searchForGroup(String userId, String column, String key,int currPage,int pageSize) {
        List<ApplyVo> applyVoList = applyDao.getAppVoForGroup(column,key);
        applyVoList.removeIf(e->activityManagerDao.isManagedByGroup(e.getActivity().getId(),userId)==0);

        int totalCount = applyVoList.size();
        int st = (currPage-1)*pageSize;
        int ed = st+pageSize-1>totalCount?totalCount:st+pageSize-1;
        applyVoList = applyVoList.subList(st,ed);
        return new Page(currPage,pageSize,totalCount,applyVoList);
    }
}
