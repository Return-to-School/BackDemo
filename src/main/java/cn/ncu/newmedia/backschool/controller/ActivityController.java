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
import java.util.ArrayList;
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


    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    /**
     * 活动资料上传,只支持单文件
     * @param activityId
     * @param activityFiles
     * @return
     */
    @RequestMapping("/upload/{activityId}")
    @ResponseBody
    public Map<String, Object> upload(@PathVariable("activityId")int activityId,@RequestParam("activityFiles") List<MultipartFile> activityFiles){


        Activity activity = activityService.getActivityById(activityId);

        String filePath = activity.getFilePath();
        /*获取活动资料的目录*/
        File director = new File(FILEPATH+filePath+"/活动资料");

        if(!director.exists())
            director.mkdirs();

        List<File> fileList = new ArrayList<>();
        for (MultipartFile e:activityFiles) {

            File file = new File(director+"/"+System.currentTimeMillis()+"_"+e.getOriginalFilename());

            try{
                e.transferTo(file);
                fileList.add(file);
            }catch (IOException e1){
                e1.printStackTrace();
                fileList.forEach(e2->e2.delete());
                return MessageObject.dealMap(List.of("success","message"),List.of(false,"文件上传错误"));
            }
        }

        return MessageObject.dealMap(List.of("success","message"),List.of(true,"文件上传成功"));
    }


    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> addActivity(@RequestBody Activity activity){

        activity.setCreateTime(new Date());
        /*去掉目录非法字符*/
        activity.setFilePath("/"+activity.getName().replaceAll("[<>\"|/:?*\\\\ ]",""));

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

    /**
     * 删除一个活动
     * @param activityId
     * @return
     */
    @RequestMapping("/delete/{activityId}")
    @ResponseBody
    public Map<String,Object> deleteActivity(@PathVariable("activityId")int activityId){

        boolean success = activityService.deleteActivity(activityId);

        return MessageObject.dealMap(List.of("success"),List.of(success));
    }

    /**
     * 获取所有的活动
     * @return
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public List<Activity> listAllActivities(){
        return activityService.listAllActivities();
    }


    /**
     * 显示所有正在进行的活动
     * @return
     */
    @RequestMapping("/allUnderwayAct")
    @ResponseBody
    public List<Activity> listAllUnderwayAct(){
        return activityService.listAllUnderwayAct();
    }

    /**
     * 获取宣传组管理员下的所有正在进行的活动
     * @param managerId
     * @return
     */
    @RequestMapping("/group/allUnderwayAct/{managerId}")
    @ResponseBody
    public List<Activity> listGroupUnderwayAct(@PathVariable("managerId")int managerId){
        return activityService.listGroupUnderwayAct(managerId);
    }

    /**
     * 获取宣传组管理员管理的活动
     * @param userId
     * @return
     */
    @RequestMapping("/group/{userId}")
    @ResponseBody
    public List<Activity> groupActivity(@PathVariable("userId") int userId){
        return activityService.getGroupActivityList(userId);
    }

    /**
     * 搜索指定区域的活动
     * @param key
     * @return
     */
    @RequestMapping("/search/location/{key}")
    @ResponseBody
    public List<Activity> searchByLoc(@PathVariable("key")String key){
        return activityService.filterActivityByLocation(key);
    }

    /**
     * 搜索指定名称的活动
     * @param key
     * @return
     */
    @RequestMapping("/search/name/{key}")
    @ResponseBody
    public List<Activity> searchByName(@PathVariable("key")String key){
        return activityService.filterActivityByName(key);
    }

}
