package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.FeedBackDao;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
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
    public boolean saveFeedback(Activity activity, Feedback feedBack) {

        Date now  = new Date();

        if(now.after(activity.getFeedbackStartTime())
                &&now.before(activity.getApplyEndTime()))
            return feedBackDao.save(feedBack)>0;

        return false;
    }

    public Feedback getFeedBackByApplyId(Integer applyId){return feedBackDao.getFeedbackByColumn("apply_id",applyId);}

    @Transactional
    public int delete(int feedbackId) {
        return feedBackDao.delete(feedbackId);
    }
}
