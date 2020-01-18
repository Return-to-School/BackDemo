package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.FeedBack;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.FeedBackService;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private final String FILEPATH = "e:/所有文件";

    @Autowired
    private FeedBackService feedBackService;


    @Autowired
    private ApplyService applyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    @RequestMapping("/sendFeedback/{applyId}/{level}")
    @ResponseBody
    public Map<String,Object> sendFeedback(@RequestParam("feedbackFiles") List<MultipartFile> feedbackFiles,
                                           @PathVariable("applyId") int applyId,
                                           @PathVariable("level") int level){

        if(feedbackFiles.size()==0){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"请选择需要上传反馈文件"));
        }

        /*获取活动的文件路径*/
        Apply apply = applyService.getApplyById(applyId);
        Student student = studentService.getStudentByColumn("student_id",apply.getStudent());
        Activity activity = activityService.getActivityById(apply.getActivity());
        String filePath = "/"+activity.getName();
        File director = new File (FILEPATH +filePath+"/反馈文件");

        if(!director.exists()){
            director.mkdirs();
        }

        List<File> fileList = new ArrayList<>();
        int flag = 0;

        /*使用时间戳标记，保存文件*/
        Date timestrap = new Date();
        String time = timestrap.toLocaleString().replace(":","-");

        for(MultipartFile e:feedbackFiles){

            File file = new File(director+"/"
                    +time+"_"+student.getName()+"_"+e.getOriginalFilename());
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

        FeedBack feedBack = new FeedBack();
        feedBack.setApply(applyId);
        feedBack.setLevel(level);
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
