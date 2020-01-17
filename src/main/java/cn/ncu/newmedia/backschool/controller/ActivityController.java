package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/13 21:24
 * @description
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    private final String FILEPATH = "e:/所有文件";

//    private final String STATICFILEPATH = "/activityFile";
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    /**
     * 活动资料上传,只支持单文件
     * @param activityId
     * @param activityFile
     * @return
     */
    @RequestMapping("/upload/{activityId}")
    @ResponseBody
    public Map<String, Object> upload(@PathVariable("activityId")int activityId, MultipartFile activityFile){

        /*避免文件重名使用时间戳*/
        String filename = System.currentTimeMillis()+activityFile.getOriginalFilename();

        String filePath = activityService.getActivityById(activityId).getFilePath();

        /*获取活动资料的目录*/
        File director = new File(FILEPATH+filePath+"/活动资料");

        if(!director.exists())
            director.mkdirs();

        File file = new File(director+"/"+filename);

        try{
            activityFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
            return MessageObject.dealMap(List.of("success"),List.of(false));
        }

        return MessageObject.dealMap(List.of("success"),List.of(true));
    }


    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> addActivity(@RequestBody Activity activity){

        activity.setCreateTime(new Date());
        activity.setFilePath("/"+activity.getName());

        /*创建两个文件夹存放活动文件和返回文件*/

        File activityFileDirector = new File(FILEPATH+activity.getFilePath()+"/活动资料");
        File feedBackFileDirector = new File(FILEPATH+activity.getFilePath()+"/反馈文件");

        activityFileDirector.mkdirs();
        feedBackFileDirector.mkdirs();

        boolean success = activityService.saveActivity(activity);

        return MessageObject.dealMap(List.of("success"),List.of(success));

    }


    /**
     * 更新活动信息
     * @param activity
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> updateActivity(@RequestBody Activity activity){

        /*保留原来的旧的活动信息*/
        Activity activityOld = activityService.getActivityById(activity.getId());

        activity.setFilePath("/"+activity.getName());

        boolean success = activityService.updateActivity(activity);

        if(success){
            /*修改成新的资料文件路径*/
            File activityFileDirector = new File(FILEPATH+activityOld.getFilePath());

            if(activityFileDirector.exists()){
                activityFileDirector.renameTo(new File(FILEPATH+activity.getFilePath()));
            }

            return MessageObject.dealMap(List.of("success"),List.of(success));
        }

        return MessageObject.dealMap(List.of("success"),List.of(false));

    }

    @RequestMapping("/delete/{activityId}")
    @ResponseBody
    public Map<String,Object> deleteActivity(@PathVariable("activityId")int activityId){

        boolean success = activityService.deleteActivity(activityId);

        return MessageObject.dealMap(List.of("success"),List.of(success));
    }


    @RequestMapping("/getAll")
    @ResponseBody
    public List<Activity> listAllActivities(){
        return activityService.listAllActivities();
    }
}
