package cn.ncu.newmedia.backschool;

import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackSchoolApplicationTests {


    @Autowired
    private ActivityService activityService;


    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        System.out.println(activityService.listAllHistoryAct(1, 3));
    }

}
