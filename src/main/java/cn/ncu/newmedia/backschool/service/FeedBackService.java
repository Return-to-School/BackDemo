package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.FeedBackDao;
import cn.ncu.newmedia.backschool.pojo.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean saveFeedback(FeedBack feedBack) {
        return feedBackDao.save(feedBack)>0;
    }
}
