package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ReturnCode;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.service.ActivityService;
import cn.ncu.newmedia.backschool.service.ApplyService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ActivityController {

    static final String FILEPATH = "c:/形象大使回母校";


    @Autowired
    private ActivityService activityService;

    @Autowired
    private ApplyService applyService;



    /**
     * PC端创建一个活动
     * @param activity
     * @return
     */
    @RequestMapping(value = "/{userId}",method = RequestMethod.POST)
    @ResponseBody
    public Map addActivity(@PathVariable("userId")String userId,
                                          @RequestBody Activity activity){

        activity.setCreateTime(new Date());

        Activity tmp = activityService.getActivityByName(activity.getName());
        if(tmp!=null){
            log.error("账户："+userId+"创建活动失败，活动同名");
            return Map.of("success",false, "code",ReturnCode.ACTIVITY_EXISTS.getCode(),
                    "msg",ReturnCode.ACTIVITY_EXISTS.getDesc(),"activityId",-1);
        }




        /*去掉目录非法字符*/
        activity.setFilePath("/"+activity.getName().replaceAll("[<>\"|/:?*\\\\ ]",""));

        /*创建两个文件夹存放活动文件和返回文件*/
        try{
            File activityFileDirector = new File(FILEPATH+activity.getFilePath()+"/活动资料");
            File feedBackFileDirector = new File(FILEPATH+activity.getFilePath()+"/反馈文件");
            activityFileDirector.mkdirs();
            feedBackFileDirector.mkdirs();
        }catch (Exception e){
            log.error("活动相关的目录创建失败");
        }


        boolean success = activityService.saveActivity(userId,activity);

        ReturnCode code;
        int id;
        if(success){
            code = ReturnCode.SUCCESS;
            id = activity.getActivityId();
        }else{
            code = ReturnCode.PARAMS_ERROR;
            id = -1;
        }

        return Map.of("success",success, "code",code.getCode(),
                "msg",code.getDesc(),"activityId",id);

    }




    /**
     * pc端活动资料上传
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

                log.error("文件上传错误");
                return Map.of("success",false,"code",ReturnCode.FILE_UPLOAD_ERROR.getCode(),
                        "msg",ReturnCode.FILE_UPLOAD_ERROR.getDesc());
            }
        }

        return Map.of("success",true,"code",ReturnCode.SUCCESS.getCode(),
                "msg",ReturnCode.SUCCESS.getDesc());
    }






    /**
     * 获取活动下的文件名
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/{activityId}/filenames",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFilenames(@PathVariable("activityId")Integer activityId){

        List<String> filenames = new ArrayList<>();

        Activity activity = activityService.getActivityById(activityId);

        boolean success = false;

        ReturnCode code;

        if(activity==null){
            code = ReturnCode.NODATA;
        }else{

            String filePath = activity.getFilePath()+"/活动资料";

            File director = new File(FILEPATH+filePath);
            filenames.addAll(Arrays.asList(director.list()));
            code = ReturnCode.SUCCESS;
            success = true;
        }

        return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc(),"filenames",filenames);
    }




    /**
     * pc端更新活动信息
     * @param activity
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateActivity(@PathVariable("id") Integer id,@RequestBody Activity activity){

        /*保留原来的旧的活动信息*/
        Activity activityOld = activityService.getActivityById(id);

        if(activityOld==null){
            /*活动不存在*/
            log.warn("更新的活动不存在=>id:"+id);
            return Map.of("success",false,"code",ReturnCode.NODATA.getCode(),"msg",ReturnCode.NODATA.getDesc());
        }else if(activityOld.getActivityId()!=activity.getActivityId()){
            /*id不一致*/
            return Map.of("success",false,"code",ReturnCode.PARAMS_ERROR,"msg",ReturnCode.PARAMS_ERROR);
        }

        activity.setFilePath("/"+activity.getName());
        activity.setCreateTime(activityOld.getCreateTime());
        boolean success = activityService.updateActivity(activity);

        ReturnCode code = ReturnCode.FAILED;
        if(success){
            /*修改成新的资料文件路径*/
            File activityFileDirector = new File(FILEPATH+activityOld.getFilePath());

            if(activityFileDirector.exists()){
                activityFileDirector.renameTo(new File(FILEPATH+activity.getFilePath()));
            }

            code = ReturnCode.SUCCESS;
        }

        return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc());
    }






    /**
     * pc端接口，删除一个活动
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/{activityId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> deleteActivity(@PathVariable("activityId")Integer activityId){


        Activity activity = activityService.getActivityById(activityId);
        if(activity==null){
            log.warn("删除的活动不存在=>id:"+activityId);
            return Map.of("success",false,"code",
                    ReturnCode.NODATA.getCode(),"msg",ReturnCode.NODATA.getDesc());
        }

        activityService.deleteActivity(activityId);

        return Map.of("success",true,"code",ReturnCode.SUCCESS.getCode(),"msg",ReturnCode.SUCCESS.getDesc());
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
     * 移动端接口，显示所有正在进行的活动
     * @return
     */
    @RequestMapping(value = "/all/underway-act",method = RequestMethod.GET)
    @ResponseBody
    public Page listAllUnderwayAct(@RequestParam("currPage")Integer currPage,
                                   @RequestParam("pageSize")Integer pageSize){
        return activityService.listAllUnderwayAct(currPage,pageSize);
    }





    /**
     * 移动端显示所有的历史活动
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
     * pc端获取活动下所有通过审核的学生申请
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
     * PC端获取宣传组管理员下的所有正在进行的活动
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
    @RequestMapping(value = "/group/{managerId}/history",method = RequestMethod.GET)
    @ResponseBody
    public Page listGroupHistoryAct(@PathVariable("managerId")String managerId,
                                    @RequestParam("currPage")Integer currPage,
                                    @RequestParam("pageSize")Integer pageSize){
        return activityService.listGroupHistoryAct(currPage,pageSize,managerId);
    }


    /**
     * 全局搜索模糊查询活动的相关字段
     * @param key
     * @param currPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search-by-key",method = RequestMethod.GET)
    @ResponseBody
    public Page search(@RequestParam("key")String key,
                       @RequestParam("currPage")int currPage,
                       @RequestParam("pageSize")int pageSize){
        return activityService.search(key,currPage,pageSize);
    }
}
