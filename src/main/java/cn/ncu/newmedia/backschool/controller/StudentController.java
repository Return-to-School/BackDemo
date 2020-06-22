package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ReturnCode;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.StudentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ApplyService applyService;



    /**
     * 移动端，完善学生的个人信息
     * @param student
     * @return
     */
    @RequiresRoles(value = {"normalUser"})
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateProfile(@PathVariable("id")String id,@RequestBody Student student) {

        boolean success = false;
        Student studentOld = studentService.getStudentByColumn("student_id",id);

        /*判断下基础数据库是否有该学生的记录*/
        if(studentOld==null){
            return Map.of("success",false,"msg", ReturnCode.NODATA.getDesc(),
                    "code",ReturnCode.NODATA.getCode());
        }else if(!studentOld.getName().equals(student.getName())){
            /*学号姓名不匹配*/
            return Map.of("success",false,"msg",ReturnCode.ID_NOT_MATCH_NAME.getDesc(),
                    "code",ReturnCode.ID_NOT_MATCH_NAME.getCode());
        }


        ReturnCode code = ReturnCode.SUCCESS;

        /*判断参数是否完整*/
        if(student.getQq()==null||student.getBankCard()==null||
                student.getPhone()==null||student.getEmail()==null||
                student.getOrigin()==null||student.getHighSchool()==null||
        student.getIdCard()==null){

            code = ReturnCode.PARAMS_ERROR;
        }else{
            /*验证身份证*/
            if(!studentService.checkIdCard(student.getIdCard())){
                code = ReturnCode.IDCARD_ERROR;
            }else{
                studentOld.setQq(student.getQq());
                studentOld.setEmail(student.getEmail());
                studentOld.setHighSchool(student.getHighSchool());
                studentOld.setOrigin(student.getOrigin());
                studentOld.setBankCard(student.getBankCard());
                studentOld.setPhone(student.getPhone());
                studentOld.setIdCard(student.getIdCard());
                success = studentService.updateStudent(studentOld);

                if(!success) code = ReturnCode.FAILED;
            }

        }


        return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc());
    }




    /**
     * 移动端，获取指定学生信息
     * @param studentId
     * @return
     */
    @RequiresRoles(value = {"normalUser"})
    @RequestMapping(value = "/{studentId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getStudent(@PathVariable("studentId")String studentId){

        Student student = studentService.getStudentByColumn("student_id",studentId);

        ReturnCode code = ReturnCode.SUCCESS;
        boolean success = true;
        if(student==null){
            code = ReturnCode.NODATA;
            success = false;
        }
        return Map.of("success",success,"msg",code.getDesc(),"code",code.getCode(),"data",student);
    }



    /**
     * 获取所有学生的信息
     * @return
     */
    @RequiresRoles(value = {"groupManager","superManager"},logical = Logical.OR)
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public Map listAll(@RequestParam("currPage")int currPage,
                        @RequestParam("pageSize")int pageSize){
        Page page = studentService.listAll(currPage,pageSize);
        ReturnCode code = ReturnCode.SUCCESS;
        if(page.getTotalCount()==0){
            code = ReturnCode.NODATA;
            page = null;
        }
        return Map.of("success",true,"code",code.getCode(),"msg",code.getDesc(),"data",page);
    }



    /**
     * pc端获取参与某个活动的所有学生信息
     * @param activityId
     * @return
     */
    @RequiresRoles(value = {"groupManager","superManager"},logical = Logical.OR)
    @RequestMapping(value = "/student-in-act/{activityId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getStudentInAct(@PathVariable("activityId")Integer activityId,
                                  @RequestParam("currPage")int currPage,
                                  @RequestParam("pageSize")int pageSize){

        Activity activity = activityService.getActivityById(activityId);

        ReturnCode code = ReturnCode.SUCCESS;

        /*活动不存在*/
        if(activity==null){
            code = ReturnCode.NODATA;
            return Map.of("success",false,"code",code.getCode(),"msg",code.getDesc(),"data","");
        }

        return Map.of("success",true,"code",code.getCode(),"msg",code.getDesc(),"data",applyService.listAllApplyVoInAct(currPage,pageSize,activityId));
//        activity.setApplyList(applyService.listAllByActivityId(activityId));
//
//        JSONArray applyArray = new JSONArray();
//        for (Apply o : activity.getApplyList()) {
//            JSONObject applyObj = JSONObject.parseObject(JSON.toJSONString(o,
//                    SerializerFeature.WriteMapNullValue));
//
//            Student student = studentService.getStudentByColumn("student_id",
//                    applyObj.get("studentId"));
//            applyObj.put("student",student);
//            applyObj.remove("studentId");
//            applyArray.add(applyObj);
//        }
//
//        JSONObject activityObj = JSONObject.parseObject(JSON.toJSONString(activity));
//        activityObj.replace("applyList",applyArray);

    }

}
