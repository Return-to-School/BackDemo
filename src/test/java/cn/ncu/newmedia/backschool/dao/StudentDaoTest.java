package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.Enumeration.SexEnum;
import cn.ncu.newmedia.backschool.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;


@SpringBootTest
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    Student student = new Student();

    @PostConstruct
    void init(){
        student.setName("张三");
        student.setGender(SexEnum.MALE);
        student.setStudentCard("6109117198");
        student.setCollege("信工");
        student.setClassname("计算机");
        student.setIdCard("43112119990729879X");
        student.setQq("4417128769");
        student.setBankCard("6173002020062111570");
        student.setPhone("15678905436");
        student.setEmail("5413138@qq.com");
        student.setOrigin("江西省-高安市-南城县");
        student.setHighSchool("南城县第一中学");
        student.setUser(1);
    }

    @Test
    void testinsertStudent(){
        System.out.println(studentDao.insert(student));
    }

    @Test
    void testUpdateStudent(){
        student.setId(1);
        student.setOrigin("湖南省-永州市-祁阳县-大中桥");

        System.out.println(studentDao.update(student));
    }


    @Test
    void testIdCardHashMatch(){
        System.out.println(studentDao.idCardHashMatch("431121199907298790","along"));
    }


    @Test
    void testGetStudentById(){
        System.out.println(studentDao.getStudentByColumn("student_id",2));
    }
}