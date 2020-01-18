package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.FeedBackDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author maoalong
 * @date 2020/1/17 20:55
 * @description
 */
@Service
public class FeedBackService {

    @Autowired
    private FeedBackDao feedBackDao;

    @Transactional
    public boolean saveFeedback(Activity activity, FeedBack feedBack) {

        Date now  = new Date();

        if(now.after(activity.getFeedbackStartTime())
                &&now.before(activity.getApplyEndTime()))
            return feedBackDao.save(feedBack)>0;

        return false;
    }
}
