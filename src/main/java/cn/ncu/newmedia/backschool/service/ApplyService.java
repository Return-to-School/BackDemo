package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ApplyDao;
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

    @Transactional
    public boolean examine(List<Apply> applyList,int status) {

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

    public List<ApplyVo> search(String name, String key) {
        return applyDao.getApplyVoListByColumn(name,key);
    }
}
