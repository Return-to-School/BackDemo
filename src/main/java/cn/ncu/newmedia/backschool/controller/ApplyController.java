package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/17 16:04
 * @description
 */
@Controller
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private StudentService studentService;


    /**
     * 学生申请报名参加活动
     * @param apply
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> apply(@RequestBody Apply apply){

        /*判断是否存在该学生*/
        Student student = studentService.getStudentByColumn("student_id",apply.getStudent());

        if(student==null){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"请完善个人资料"));
        }

        Activity activity = activityService.getActivityById(apply.getActivity());

        /*需要审核*/
        if(activity.getNeedExamine())
            apply.setStatus(0);
        else
            apply.setStatus(1);

        boolean success = applyService.apply(apply,activity);

        return MessageObject.dealMap(List.of("success","message"),List.of(success,"提交申请成功"));
    }

    /**
     * 获取所有的申请
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Apply> getAllApply(){
        return applyService.listAllApplies();
    }


    /**
     * 获取某个活动的所有申请
     * @param activityId
     * @return
     */
    @RequestMapping("/all/activity-id/{activityId}")
    @ResponseBody
    public List<Apply> getAllByActivityId(@PathVariable("activityId") int activityId){
        return applyService.listAllByActivityId(activityId);
    }


    /**
     * 获取某个学生的所有的活动申请
     * @param studentId
     * @return
     */
    @RequestMapping("/all/student-id/{studentId}")
    @ResponseBody
    public List<Apply> getAllByStudentId(@PathVariable("studentId") int studentId){
        return applyService.listAllByStudentId(studentId);
    }


    /**
     * 管理员审核报名申请
     * @param applyId
     * @param status
     * @return
     */
    @RequestMapping("/examination/{applyId}/{status}")
    @ResponseBody
    public Map<String,Object> examine(@PathVariable("applyId") int applyId,@PathVariable("status") int status){

        boolean success = applyService.examine(applyId,status);
        return MessageObject.dealMap(List.of("success"),List.of(success));

    }

}
