package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
     * 学生申请报名参加活动
     * @param apply
     * @return
     */
    @RequestMapping("/signUp")
    @ResponseBody
    public Map<String,Object> apply(@RequestBody Apply apply){

        Activity activity = activityService.getActivityById(apply.getActivity());

        apply.setStatus(false);

        boolean success = applyService.apply(apply,activity);

        return MessageObject.dealMap(List.of("success"),List.of(success));
    }

    /**
     * 获取所有的申请
     * @return
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public List<Apply> getAllApply(){
        return applyService.listAllApplies();
    }

    /**
     * 获取某个活动的所有申请
     * @param activityId
     * @return
     */
    @RequestMapping("/getAllByActivityId/{activityId}")
    @ResponseBody
    public List<Apply> getAllByActivityId(@PathVariable("activityId") int activityId){
        return applyService.listAllByActivityId(activityId);
    }

    /**
     * 获取某个学生的所有的活动申请
     * @param studentId
     * @return
     */
    @RequestMapping("/getAllByStudentId/{studentId}")
    @ResponseBody
    public List<Apply> getAllByStudentId(@PathVariable("studentId") int studentId){
        return applyService.listAllByStudentId(studentId);
    }
}
