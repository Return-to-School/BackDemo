package cn.ncu.newmedia.backschool;

import cn.ncu.newmedia.backschool.Enumeration.SexEnum;
import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.dao.ActivityDao;
import cn.ncu.newmedia.backschool.dao.ApplyDao;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.vo.pc.Keys;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.FeedBackService;
import cn.ncu.newmedia.backschool.service.PageService;
import cn.ncu.newmedia.backschool.service.StudentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackSchoolApplicationTests {


    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private ApplyDao applyDao;

    @Autowired
    private ActivityDao activityDao;

    @Test
    void contextLoads() {
    }


    @Test
    void test(){
        applyDao.getAppVoListBySid("6109117189").forEach(System.out::println);
    }


}
