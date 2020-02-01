package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.Utils.EnumUtils;
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
        Student student = studentService.getStudentByColumn("student_id",apply.getStudentId());

        if(student==null){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"请完善个人资料"));
        }

        Activity activity = activityService.getActivityById(apply.getActivityId());
        if(activity==null)
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"活动不存在"));

        /*判断申请是否需要审核*/
        if(activity.getNeedExamine())
            apply.setStatus(ApplyStatus.NOTEXAMINE);
        else
            apply.setStatus(ApplyStatus.AGREE);

        boolean success = false;
        try{
            success = applyService.apply(apply,activity);
        } catch (Exception e){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"该活动已经申请"));
        }

        return MessageObject.dealMap(List.of("success","message"),List.of(success,success?"提交申请成功":"提交申请失败"));
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
    @RequestMapping(value = "/all/activity-id/{activityId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Apply> getAllByActivityId(@PathVariable("activityId") int activityId){
        return applyService.listAllByActivityId(activityId);
    }


    /**
     * 获取某个学生的所有的活动申请
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/all/student-id/{studentId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Apply> getAllByStudentId(@PathVariable("studentId") int studentId){
        return applyService.listAllByStudentId(studentId);
    }


    /**
     * 管理员批量审核报名申请
     * @param applyList
     * @param statusCode
     * @return
     */
    @RequestMapping(value = "/examination/{status}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> examine(@RequestBody List<Apply> applyList,@PathVariable("status") int statusCode){

        ApplyStatus status = EnumUtils.getEnumByCode(ApplyStatus.class,statusCode);
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
        boolean success = true;
        List<ApplyVo> applyVoList = new ArrayList<>();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(column.equals("")){
            message = "搜索的列不能为空";
            success = false;
        }else if(key.equals("")){
            message = "搜索的关键字不能为空";
            success = false;
        }else if(column.equals("province")||column.equals("city")||column.equals("county")){
            applyVoList = applyService.search("loc",key);
        }else if(column.equals("name")){
            applyVoList = applyService.search("name",key);
        }else if(column.equals("college")){
            applyVoList = applyService.search("college",key);
        }else if(column.equals("high-school")){
            applyVoList = applyService.search("highSchool",key);
        }else if(column.equals("status")){
            applyVoList = applyService.search("apply_status",key);
        }else{
            message = "搜索字段错误";
            success = false;
        }


        return MessageObject.dealMap(List.of("success","message","applyVoList"),List.of(success,message,applyVoList));

    }


    /**
     * 宣传组管理员查询报名申请
     * @param column
     * @param key
     * @return
     */
    @RequestMapping(value = "/search-for-group/{userId}/{column}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchForGroupManager(@PathVariable("column")String column,
                                                    @PathVariable("userId")String userId,
                                                    @RequestParam("key")String key){

        column = column.trim().replaceAll(" ","");
        if(column.equals("province")||column.equals("city")){
            return MessageObject.dealMap(List.of("success","message","applyVoList"),List.of(false,"宣传组管理员不能进行按区域搜索",new ArrayList<>()));
        }else{
            Map<String,Object> tmp = searchForSuperManager(column,key);
            List<ApplyVo> applyVoList = (List<ApplyVo>)tmp.get("applyVoList");
            /*筛选出管理员所管理的活动*/
            tmp.put("applyVoList",applyVoList.stream().filter(e->activityService.isManagedByGroup(e.getActivity().getId(),userId)));
            return tmp;
        }
    }

}
