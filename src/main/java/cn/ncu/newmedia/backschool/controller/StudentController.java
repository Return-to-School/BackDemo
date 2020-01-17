package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/12 22:31
 * @description
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * 添加一个学生信息
     * @param student
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> addProfile(@RequestBody Student student){

        boolean success = studentService.saveStudent(student);

        return MessageObject.dealMap(List.of("success"),List.of(success));
    }

    /**
     * 更新学生的个人信息
     * @param student
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> updateProfile(@RequestBody Student student) {

        boolean success = false;
        if(!(boolean)identify(student.getIdCard(),student.getName()).get("success")==false){
            success = studentService.updateStudent(student);
        }

        return MessageObject.dealMap(List.of("success"), List.of(success));
    }


    /**
     * 验证身份证与本人姓名是否一致
     * @param idCard
     * @param name
     * @return
     */
    @RequestMapping("/identify/{idCard}/{name}")
    @ResponseBody
    public Map<String,Object> identify(@PathVariable("idCard")String idCard,
                                       @PathVariable("name")String name){
        boolean success =  studentService.idCardHasMatch(idCard,name);
        return MessageObject.dealMap(List.of("success"),List.of(success));
    }

    @RequestMapping("/get/{userId}")
    @ResponseBody
    public Student getStudent(@PathVariable("userId")int userId){
        return studentService.getStudentByColumn("user_id",userId);
    }


}
