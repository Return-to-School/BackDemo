package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.FeedBack;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.FeedBackService;
import cn.ncu.newmedia.backschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

//    private final String STATICFEEDBACKPATH = "/feedbackFile";

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    @RequestMapping("/sendFeedback/{applyId}/{level}")
    @ResponseBody
    public Map<String,Object> sendFeedback(MultipartFile feedbackFile,
                                           @PathVariable("applyId") int applyId,
                                           @PathVariable("level") int level){

        String filename = System.currentTimeMillis()+feedbackFile.getOriginalFilename();

        Apply apply = applyService.getApplyById(applyId);

        Activity activity = activityService.getActivityById(apply.getActivity());

        String filePath = "/"+activity.getName();

        FeedBack feedBack = new FeedBack();
        feedBack.setApply(applyId);
        feedBack.setLevel(level);
        feedBack.setFilePath(filePath);

        File director = new File (FILEPATH +filePath+"/反馈文件");

        if(!director.exists()){
            director.mkdirs();
        }

        File file = new File (director+"/"+filename);

        try{
            feedbackFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
            return MessageObject.dealMap(List.of("success"),List.of(false));
        }

        boolean success = feedBackService.saveFeedback(feedBack);

        return MessageObject.dealMap(List.of("success"),List.of(success));

    }

}
