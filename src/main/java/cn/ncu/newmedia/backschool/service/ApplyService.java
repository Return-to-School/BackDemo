package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
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

    public List<Apply> listAllApplies() {
        return applyDao.listAll();
    }

    public List<Apply> listAllByActivityId(int activityId) {
        return applyDao.getAppliesByColumn("activity_id",activityId);
    }

    public List<Apply> listAllByStudentId(int studentId){
        return applyDao.getAppliesByColumn("student_id",studentId);
    }

    public Apply getApplyById(int applyId){return applyDao.getApplyById(applyId);}

    public boolean examine(int applyId,int status) {
        return applyDao.changeApplyStatus(applyId,status)>0;
    }
}
