package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.Utils.EnumUtils;
import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Feedback;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import cn.ncu.newmedia.backschool.pojo.vo.DataModel;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.ExcelExportService;
import cn.ncu.newmedia.backschool.service.StudentService;
import cn.ncu.newmedia.backschool.view.ExcelView;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

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


    /*
    * 宣传组管理员能够搜索的字段
    * */
    private final Map<String,String> groupManagerMap = Map.of("name","name",
            "college","college","high-school","highSchool","status","apply_status");

    /**
     * 超级管理员能够搜索的字段
     */
    private final Map<String,String> superManagerMap = Map.of("province","loc","city","loc","county","loc",
            "name","name", "college","college","high-school","highSchool","status","apply_status");


    /**
     * 到处的excel列表的表头
     */
    private final String[] listHeader =  {"姓名","学号","学院","专业班级","地区","回访中学","报名状态","评价结果"};



    /**
     * 学生申请报名参加活动，报名必须在规定的时间范围内，
     * 对于不需要审核的活动直接设置报名的状态为已通过
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
    public Page getAllApply(@RequestParam("currPage")int currPage,
                            @RequestParam("pageSize")int pageSize){
        return applyService.listAllApplies(currPage,pageSize);
    }




    /**
     * 获取某个活动的所有申请
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/all/activity-id/{activityId}",method = RequestMethod.GET)
    @ResponseBody
    public Page getAllByActivityId(@PathVariable("activityId") int activityId,
                                   @RequestParam("currPage")int currPage,
                                   @RequestParam("pageSize")int pageSize){
        return applyService.listAllByActivityId(activityId,currPage,pageSize);
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
                                                    @RequestParam("key")String key,
                                                    @RequestParam("currPage")int currPage,
                                                    @RequestParam("pageSize")int pageSize){

        String message = "查询成功";
        boolean success = true;
        Page page = new Page();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(column.equals("")){
            message = "搜索的列不能为空";
            success = false;
        }else if(key.equals("")){
            message = "搜索的关键字不能为空";
            success = false;
        }else if(superManagerMap.containsKey(column)){
            String columnInDb = superManagerMap.get(column);
            page = applyService.search(columnInDb,key,currPage,pageSize);
        }else{
            message = "搜索字段错误";
            success = false;
        }

        return MessageObject.dealMap(List.of("success","message","page"),List.of(success,message,page));

    }

    /**
     * 超级管理员导出信息，管理员可以通过省、市、姓名、学院、回访中学、状态等信息查询导出
     * @param column
     * @param key
     * @return
     */
    @RequestMapping(value = "/export-for-super/{column}",method = RequestMethod.GET)
    public ModelAndView exportForSuperManager(@PathVariable("column")String column,
                                              @RequestParam("key")String key){

        List<ApplyVo> applyVoList = new ArrayList<>();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(superManagerMap.containsKey(column)){
            String columnInDb = superManagerMap.get(column);
            applyVoList.addAll(applyService.search(columnInDb,key));
        }

        return export(applyVoList);
    }







    /**
     * 宣传组管理员查询报名申请，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。
     * @param column
     * @param key
     * @return
     */
    @RequestMapping(value = "/search-for-group/{userId}/{column}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchForGroupManager(@PathVariable("column")String column,
                                                    @PathVariable("userId")String userId,
                                                    @RequestParam("key")String key,
                                                    @RequestParam("currPage")int currPage,
                                                    @RequestParam("pageSize")int pageSize){

        String message = "查询成功";
        boolean success = true;
        Page page = new Page();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(column.equals("")){
            message = "搜索的列不能为空";
            success = false;
        }else if(key.equals("")){
            message = "搜索的关键字不能为空";
            success = false;
        }else if(groupManagerMap.containsKey(column)){
            String columnInDb = groupManagerMap.get(column);
            page = applyService.searchForGroup(userId,columnInDb,key,currPage,pageSize);
        }else{
            message = "搜索字段错误";
            success = false;
        }

        return MessageObject.dealMap(List.of("success","message","page"),List.of(success,message,page));

    }



    /**
     * excel导出宣传组管理员的查询数据，宣传组管理员只能用姓名、学院、回访中学、状态查询导出
     * @param column
     * @param userId
     * @param key
     * @return
     */
    @RequestMapping(value = "/export-for-group/{userId}/{column}",method = RequestMethod.GET)
    public ModelAndView exportForGroupManager(@PathVariable("column")String column,
                                               @PathVariable("userId")String userId,
                                               @RequestParam("key")String key){


        List<ApplyVo> applyVoList = new ArrayList<>();

        column = column.trim().replaceAll(" ","");
        key = key.trim();

        if(groupManagerMap.containsKey(column)){
            String columnInDb = groupManagerMap.get(column);
            applyVoList.addAll(applyService.searchForGroup(userId,columnInDb,key));
        }
        return export(applyVoList);
    }


    /**
     * 用excel的形式导出信息
     * @param applyVoList
     * @return
     */
    public ModelAndView export(List applyVoList){

        ModelAndView mv = new ModelAndView();

        View view = new ExcelView(exportApplyExcelService());
        mv.setView(view);
        mv.addObject("applyVoList",applyVoList);
        return mv;
    }




    @SuppressWarnings("unchecked")
    private ExcelExportService exportApplyExcelService(){
        return ((map, workbook, request, response) -> {
            try{

                response.setHeader("Content-Disposition","attachment; filename="+
                        new String("列表.xlsx".getBytes(),"iso8859-1"));
                response.setContentType("application/vnd.ms-excel");

                HSSFSheet sheet = (HSSFSheet)workbook.createSheet("applyVo");
                int rowNum = 0;
                HSSFRow header = sheet.createRow(rowNum++);

                List<ApplyVo> applyVoList = (List < ApplyVo >) map.get("applyVoList");

                /*创建表头*/
                for(int i = 0; i<listHeader.length; i++) {
                    header.createCell(i).setCellValue(listHeader[i]);
                }

                for(int i=0;i<applyVoList.size();i++) {

                    HSSFRow row = sheet.createRow(rowNum++);
                    ApplyVo applyVo = applyVoList.get(i);

                    /*写入数据*/
                    row.createCell(0).setCellValue(applyVo.getStudent().getName());
                    row.createCell(1).setCellValue(applyVo.getStudent().getStudentCard());
                    row.createCell(2).setCellValue(applyVo.getStudent().getCollege());
                    row.createCell(3).setCellValue(applyVo.getStudent().getClassname());
                    row.createCell(4).setCellValue(applyVo.getOrigin());
                    row.createCell(5).setCellValue(applyVo.getHighSchool());
                    row.createCell(6).setCellValue(applyVo.getStatus().getDesc());

                    Feedback feedback = applyVo.getFeedback();
                    row.createCell(7).setCellValue(feedback==null?"未提交":feedback.getLevel().getDesc());

                }

            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

}
