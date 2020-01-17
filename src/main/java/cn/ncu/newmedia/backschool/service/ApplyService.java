package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}
