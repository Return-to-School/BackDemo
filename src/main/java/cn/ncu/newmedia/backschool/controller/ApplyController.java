package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.ExcelExportService;
import cn.ncu.newmedia.backschool.service.StudentService;
import cn.ncu.newmedia.backschool.view.ExcelView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Student student = studentService.getStudentByColumn("student_id",apply.getStudentId());

        if(student==null){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"请完善个人资料"));
        }

        Activity activity = activityService.getActivityById(apply.getActivityId());

        /*判断申请是否需要审核*/
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
     * 管理员批量审核报名申请
     * @param applyList
     * @param status
     * @return
     */
    @RequestMapping(value = "/examination/{status}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> examine(@RequestBody List<Apply> applyList,@PathVariable("status") int status){

        boolean success = applyService.examine(applyList,status);
        return MessageObject.dealMap(List.of("success"),List.of(success));

    }


    /**
     * 超级管理员查询报名申请
     * @param column
     * @param key
     * @return
     */
    @RequestMapping(value = "/search-for-super/{column}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchForSuperManager(@PathVariable("column")String column,
                                     @RequestParam("key")String key){

        String message = "查询成功";
        boolean success = false;
        List<ApplyVo> applyVoList = new ArrayList<>();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(column.equals("")){
            message = "搜索的列不能为空";
        }else if(key.equals("")){
            message = "搜索的关键字不能为空";
        }else if(column.equals("province")||column.equals("city")||column.equals("county")){
            applyVoList = applyService.search("apply.origin",key);
        }else if(column.equals("name")){
            applyVoList = applyService.search("name",key);
        }else if(column.equals("college")){
            applyVoList = applyService.search("college",key);
        }else if(column.equals("high-school")){
            applyVoList = applyService.search("apply.high_school",key);
        }else if(column.equals("status")){
            applyVoList = applyService.search("apply_status",key);
        }else{
            message = "搜索字段错误";
        }

        if(applyVoList.size()!=0) success = true;

        return MessageObject.dealMap(List.of("success","message","applyVoList"),List.of(success,message,applyVoList));

    }


    /**
     * 宣传组管理员查询报名申请
     * @param column
     * @param key
     * @return
     */
    @RequestMapping("/search-for-group/{column}/{key}")
    @ResponseBody
    public Map<String,Object> searchForGroupManager(@PathVariable("column")String column,
                                                    @PathVariable("key")String key){

        column = column.trim().replaceAll(" ","");
        if(column.equals("province")||column.equals("city")){
            return MessageObject.dealMap(List.of("success","message","applyVoList"),List.of(false,"宣传组管理员不能进行按区域搜索",new ArrayList<>()));
        }else{
            return searchForSuperManager(column,key);
        }
    }

}
