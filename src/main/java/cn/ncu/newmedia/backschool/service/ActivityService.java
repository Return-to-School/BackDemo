package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maoalong
 * @date 2020/1/13 20:48
 * @description
 */
@Service
public class ActivityService {


    @Autowired
    private ActivityDao activityDao;

    public String findCreatorById(int id) {
        return activityDao.getCreatorById(id);
    }
}
