package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.Utils.EnumUtils;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Feedback;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import cn.ncu.newmedia.backschool.pojo.vo.Keys;
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

import java.io.IOException;
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

        /*判断是否存在该学生的账号*/
        Student student = studentService.getStudentByColumn("student_id",apply.getStudentId());

        if(student.getIdCard()==null||
        student.getQq()==null||student.getBankCard()==null||
        student.getPhone()==null||student.getEmail()==null||
        student.getOrigin()==null||student.getHighSchool()==null){
            return Map.of("success",false,"message","请完善个人资料");
        }


        Activity activity = activityService.getActivityById(apply.getActivityId());
        if(activity==null)
            return Map.of("success",false,"message","活动不存在");

        /*判断申请是否需要审核*/
        if(activity.getNeedExamine())
            apply.setStatus(ApplyStatus.NOTEXAMINE);
        else
            apply.setStatus(ApplyStatus.AGREE);

        boolean success = false;

        try{
            success = applyService.apply(apply,activity);
        } catch (Exception e){
            return Map.of("success",false,"message","该活动已经申请");
        }

        return Map.of("success",success,"message",success?"提交申请成功":"请在规定时间范围内提交申请");
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
        return Map.of("success",success);

    }


    /**
     * 超级管理员查询申请，搜索字段封装在keys中
     * @param keys
     * @param currPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search-for-super",method = RequestMethod.POST)
    @ResponseBody
    public Page searchForSuperManager(@RequestBody Keys keys,
                                    @RequestParam("currPage")int currPage,
                                    @RequestParam("pageSize")int pageSize){

        return  applyService.search(keys,currPage,pageSize);
    }




    /**
     * 超级管理员导出信息，管理员可以通过省、市、姓名、学院、回访中学、状态等信息查询导出
     * @param key
     * @return
     */
    @RequestMapping(value = "/export-for-super",method = RequestMethod.POST)
    public ModelAndView exportForSuperManager(@RequestBody Keys key){

        List<ApplyVo> applyVoList = new ArrayList<>();

        applyVoList.addAll(applyService.search(key));

        return export(applyVoList);
    }




    /**
     * 宣传组管理员查询报名申请，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。
     */
    @RequestMapping(value = "/search-for-group/{userId}",method = RequestMethod.POST)
    @ResponseBody
    public Page searchForGroupManager(@PathVariable("userId")String userId,
                                                    @RequestBody Keys keys,
                                                    @RequestParam("currPage")int currPage,
                                                    @RequestParam("pageSize")int pageSize){

        return applyService.searchForGroup(userId,keys,currPage,pageSize);
    }



    /**
     * excel导出宣传组管理员的查询数据，宣传组管理员只能用姓名、学院、回访中学、状态查询导出
     */
    @RequestMapping(value = "/export-for-group/{userId}",method = RequestMethod.POST)
    public ModelAndView exportForGroupManager(@PathVariable("userId")String userId,
                                               @RequestBody Keys keys){


        List<ApplyVo> applyVoList = new ArrayList<>();

        applyVoList.addAll(applyService.searchForGroup(userId,keys));
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


    /*
    * 通过活动的id和学生的id获取学生的申请信息
    * */
    @RequestMapping(value = "/activity/{activityId}/student/{studentId}",method = RequestMethod.GET)
    @ResponseBody
    public Apply getApplyByActIdAndSdtId(@PathVariable("activityId")int activityId,
                                         @PathVariable("studentId")int studentId){
        return applyService.getApplyByActIdAndSdtId(activityId,studentId);
    }
}
