package cn.ncu.newmedia.backschool;

import cn.ncu.newmedia.backschool.Enumeration.SexEnum;
import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.StudentService;
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

    @Test
    void contextLoads() {
    }


    @Test
    void testAddStudent(){
        Student student = new Student();
        student.setName("lisi");
        student.setGender(SexEnum.MALE);
        student.setCollege("材料");
        student.setClassname("123");
        student.setStudentCard("151675131623");
        System.out.println(studentService.saveStudent(student));
    }


    @Test
    void updateStudent(){
        Student student = new Student();
        student.setId(1);
        student.setIdCard("36178368123812");
        student.setBankCard("27513278");
        student.setOrigin("是否公司的");
        student.setHighSchool("祁阳一中");
        student.setPhone("2317353");
        student.setEmail("23135173@qq.com");
        student.setQq("327345723");

        System.out.println(studentService.updateStudent(student));
    }

}
