package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import java.util.ArrayList;
import java.util.HashMap;
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
    @RequestMapping(value = "",method = RequestMethod.POST)
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
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateProfile(@PathVariable("id")Integer id,@RequestBody Student student) {

        boolean success = false;
        Student studentOld = studentService.getStudentByColumn("student_id",id);
        if(studentOld==null)
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"学生信息不存在"));

        String message = "更新成功";
        student.setId(id);
        if((boolean)identify(student.getIdCard(),student.getName()).get("success")){
            success = studentService.updateStudent(student);
            if(!success) message = "更新失败";
        }else{
            message = "学生姓名与身份证不匹配";
        }

        return MessageObject.dealMap(List.of("success","message"), List.of(success,message));
    }


    /**
     * 验证身份证与本人姓名是否一致
     * @param idCard
     * @param name
     * @return
     */
    @RequestMapping(value = "/verification",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> identify(@RequestParam("idCard") String idCard,
                                       @RequestParam("name")String name){
        boolean success =  studentService.idCardHasMatch(idCard,name);
        return MessageObject.dealMap(List.of("success"),List.of(success));
    }


    /**
     * 获取指定学生信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Student getStudent(@PathVariable("id")int userId){
        return studentService.getStudentByColumn("user_id",userId);
    }


    /**
     * 获取所有学生的信息
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Student> listAll(){
        return studentService.listAll();
    }


    /**
     * 获取参与活动的所有学生信息
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/student-in-act/{activityId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudentInAct(@PathVariable("activityId")Integer activityId){
        return studentService.getStudentListInAct(activityId);
    }
}
