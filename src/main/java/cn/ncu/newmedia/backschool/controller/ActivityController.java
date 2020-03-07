package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import cn.ncu.newmedia.backschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author maoalong
 * @date 2020/1/13 21:24
 * @description
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    static final String FILEPATH = "c:/形象大使回母校";


    @Autowired
    private ActivityService activityService;

    @Autowired
    private ApplyService applyService;




    /**
     * 活动资料上传
     * @param activityId
     * @param activityFiles
     * @return
     */
    @RequestMapping(value = "/file/{activityId}",method = RequestMethod.POST)
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
                return Map.of("success",false,"message","文件上传错误");
            }
        }

        return Map.of("success",true,"message","文件上传成功");
    }





    /**
     * 添加一个活动
     * @param activity
     * @return
     */
    @RequestMapping(value = "/{userId}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addActivity(@PathVariable("userId")int userId,
                                          @RequestBody Activity activity){

        activity.setCreateTime(new Date());

        Activity tmp = activityService.getActivityByName(activity.getName());
        if(tmp!=null){
            return Map.of("success",false,"message","活动名已存在");
        }

        /*去掉目录非法字符*/
        activity.setFilePath("/"+activity.getName().replaceAll("[<>\"|/:?*\\\\ ]",""));

        /*创建两个文件夹存放活动文件和返回文件*/
        File activityFileDirector = new File(FILEPATH+activity.getFilePath()+"/活动资料");
        File feedBackFileDirector = new File(FILEPATH+activity.getFilePath()+"/反馈文件");

        activityFileDirector.mkdirs();
        feedBackFileDirector.mkdirs();

        boolean success = activityService.saveActivity(userId,activity);

        return Map.of("success",success);

    }


    /**
     * 获取活动下的文件名
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/{activityId}/filenames",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFilenames(@PathVariable("activityId")Integer activityId){

        String msg = "";
        List<String> filenames = new ArrayList<>();

        Activity activity = activityService.getActivityById(activityId);

        boolean success = false;

        if(activity==null){
            msg = "活动不存在";
        }else{

            String filePath = activity.getFilePath()+"/活动资料";

            File director = new File(FILEPATH+filePath);
            filenames.addAll(Arrays.asList(director.list()));
            msg = "获取成功";
            success = true;
        }

        return Map.of("success",success,"message",msg,"filenames",filenames);
    }




    /**
     * 更新活动信息
     * @param activity
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateActivity(@PathVariable("id") Integer id,@RequestBody Activity activity){

        /*保留原来的旧的活动信息*/
        Activity activityOld = activityService.getActivityById(id);

        if(activityOld==null){
            return Map.of("success",false,"message","活动不存在");
        }else if(activityOld.getId()!=activity.getId()){
            return Map.of("success",false,"message","传入id与对象id不一致");
        }

        activity.setFilePath("/"+activity.getName());
        activity.setCreateTime(activityOld.getCreateTime());
        boolean success = activityService.updateActivity(activity);

        if(success){
            /*修改成新的资料文件路径*/
            File activityFileDirector = new File(FILEPATH+activityOld.getFilePath());

            if(activityFileDirector.exists()){
                activityFileDirector.renameTo(new File(FILEPATH+activity.getFilePath()));
            }

            return Map.of("success",success);
        }

        return Map.of("success",false);

    }






    /**
     * 删除一个活动
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/{activityId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> deleteActivity(@PathVariable("activityId")Integer activityId){


        boolean success = activityService.deleteActivity(activityId);

        return Map.of("success",success);
    }




    /**
     * 获取所有的活动
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public Page listAllActivities(@RequestParam("currPage")Integer currPage,
                                  @RequestParam("pageSize")Integer pageSize){
        return activityService.listAllActivities(currPage,pageSize);
    }


    /**
     * 显示所有正在进行的活动
     * @return
     */
    @RequestMapping(value = "/all/underway-act",method = RequestMethod.GET)
    @ResponseBody
    public Page listAllUnderwayAct(@RequestParam("currPage")Integer currPage,
                                   @RequestParam("pageSize")Integer pageSize){
        return activityService.listAllUnderwayAct(currPage,pageSize);
    }





    /**
     * 显示所有的历史活动
     * @param currPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    @ResponseBody
    public Page listAllHistoryAct(@RequestParam("currPage") Integer currPage,
                                  @RequestParam("pageSize")Integer pageSize){
        return activityService.listAllHistoryAct(currPage,pageSize);
    }


    /**
     * 获取活动下所有通过审核的学生申请
     * @param activityId
     * @param currPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/{id}/apply-pass",method = RequestMethod.GET)
    @ResponseBody
    public Page getApplyPassStudentList(@PathVariable("id")int activityId,
                                        @RequestParam("currPage")int currPage,
                                        @RequestParam("pageSize")int pageSize){
        return applyService.getPassStudentApply(activityId,currPage,pageSize);
    }





    /**
     * 获取宣传组管理员下的所有正在进行的活动
     * @param managerId
     * @return
     */
    @RequestMapping(value = "/group/{managerId}/all/underway-act",method = RequestMethod.GET)
    @ResponseBody
    public Page listGroupUnderwayAct(@PathVariable("managerId")Integer managerId,
                                               @RequestParam("currPage")Integer currPage,
                                               @RequestParam("pageSize")Integer pageSize){
        return activityService.listGroupUnderwayAct(currPage,pageSize,managerId);
    }






    /**
     * 显示宣传组管理员的历史活动
     * @param managerId
     * @param currPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/group/history",method = RequestMethod.GET)
    @ResponseBody
    public Page listGroupHistoryAct(@RequestParam("managerId")Integer managerId,
                                    @RequestParam("currPage")Integer currPage,
                                    @RequestParam("pageSize")Integer pageSize){
        return activityService.listGroupHistoryAct(currPage,pageSize,managerId);
    }




    /**
     * 获取宣传组管理员管理的活动
     * @param userId
     * @return
     */
    @RequestMapping(value = "/group/{managerId}/all",method = RequestMethod.GET)
    @ResponseBody
    public Page groupActivity(@PathVariable("managerId") Integer userId,
                              @RequestParam("currPage")Integer currPage,
                              @RequestParam("pageSize")Integer pageSize){
        return activityService.getGroupActivityList(currPage,pageSize,userId);
    }


    /**
     * 搜索指定区域的活动
     * @param key
     * @return
     */
    @RequestMapping(value = "/search-by-loc",method = RequestMethod.GET)
    @ResponseBody
    public Page searchByLoc(@RequestParam("key")String key,
                              @RequestParam("currPage")Integer currPage,
                              @RequestParam("pageSize")Integer pageSize){
        return activityService.filterActivityByLocation(key,currPage,pageSize);
    }


    /**
     * 搜索指定名称的活动，搜索模式为模糊查询
     * @param key
     * @return
     */
    @RequestMapping(value = "/search-by-name",method = RequestMethod.GET)
    @ResponseBody
    public Page searchByName(@RequestParam("key")String key,
                                       @RequestParam("currPage")Integer currPage,
                                       @RequestParam("pageSize")Integer pageSize){
        return activityService.filterActivityByName(key,currPage,pageSize);
    }


}
