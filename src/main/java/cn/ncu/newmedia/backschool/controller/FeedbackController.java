package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.Enumeration.Level;
import cn.ncu.newmedia.backschool.Utils.EnumUtils;
import cn.ncu.newmedia.backschool.Utils.FolderDelUtils;
import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Feedback;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.FeedBackService;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/17 19:42
 * @description
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    static final String FILEPATH = "c:/形象大使回母校";

    @Autowired
    private FeedBackService feedBackService;


    @Autowired
    private ApplyService applyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    /**
     * 添加反馈信息
     * @param feedbackFiles
     * @param applyId
     * @param level
     * @return
     */
    @RequestMapping(value = "/{applyId}/level/{level}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendFeedback(@RequestParam("feedbackFiles") List<MultipartFile> feedbackFiles,
                                           @PathVariable("applyId") int applyId,
                                           @PathVariable("level") int level){

        Apply apply = applyService.getApplyById(applyId);

        /*先对申请状态进行判断，若审核未通过、或者还未被申请则不允许进行反馈*/
        if(apply.getStatus()== ApplyStatus.NOTEXAMINE){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"未审核"));
        }else if(apply.getStatus()==ApplyStatus.DISAGREE){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"审核不通过"));
        }

        if(feedbackFiles.size()==0){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"请选择需要上传反馈文件"));
        }

        /*活动的文件路径+学生姓名=学生反馈文件路径*/
        Student student = studentService.getStudentByColumn("student_id",apply.getStudentId());
        Activity activity = activityService.getActivityById(apply.getActivityId());
        String filePath = activity.getFilePath()+"/反馈文件/"+student.getName();
        File director = new File (FILEPATH +filePath);

        /*若之前存在反馈文件，需要先删除它*/
        Feedback feedback = feedBackService.getFeedBackByApplyId(applyId);
        if(feedback!=null) {
            feedBackService.delete(feedback.getId());
        }
        if(director.exists())
            FolderDelUtils.deleteFileInFolder(director);
        else{
            director.mkdirs();
        }


        List<File> fileList = new ArrayList<>();
        int flag = 0;

        /*使用时间戳标记，保存文件*/
        Date timestrap = new Date();
        String time = timestrap.toLocaleString().replace(":","-");

        for(MultipartFile e:feedbackFiles){

            File file = new File(director+"/"
                    +time+"_"+e.getOriginalFilename());
            try {
                e.transferTo(file);
            } catch (IOException e1) {
                e1.printStackTrace();
                flag++;
            }
            fileList.add(file);
        }

        if(flag>0){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"文件上传错误"));
        }

        Feedback feedBack = new Feedback();
        feedBack.setApply(applyId);
        feedBack.setLevel(EnumUtils.getEnumByCode(Level.class,level));
        feedBack.setFilePath(filePath);
        boolean success = feedBackService.saveFeedback(activity,feedBack);

        String message = "文件上传成功";

        /*添加反馈失败，回滚*/
        if(!success){
            fileList.forEach(e->e.delete());
            message = "请在反馈起始时间范围内上传文件";
        }

        return MessageObject.dealMap(List.of("success","message"),List.of(success,message));
    }

}
