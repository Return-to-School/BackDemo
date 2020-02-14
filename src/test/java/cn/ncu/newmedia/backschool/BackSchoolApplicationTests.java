package cn.ncu.newmedia.backschool;

import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BackSchoolApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDelFolder(){
        File file = new File("e:/所有文件/南昌大学开学典礼");
        FolderDelUtils.deleteFolder(file);
    }

}
