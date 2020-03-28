package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Enumeration.ReturnCode;
import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import cn.ncu.newmedia.backschool.service.StudentService;
import cn.ncu.newmedia.backschool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author maoalong
 * @date 2020/1/12 16:22
 * @description
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

//    @RequestMapping(value = "/login",method = RequestMethod.GET)
//    public String showLoginPage(){return "login";}


    /**
     * 利用shiro进行登录验证
     * @param user
     * @return
     */
    @RequestMapping(value = "/verification",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> validate(@RequestBody User user){
//        Subject subject = SecurityUtils.getSubject();

        ReturnCode code = ReturnCode.SUCCESS;
        String userId = "-1";
        boolean success = false;
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getStudentId()+"",user.getPassword());

        try{
//            subject.login(token);
            user = userService.getUserById(user.getUserId());
            userId = user.getUserId()+"";
            success = true;
        }catch (UnknownAccountException e1){
            code = ReturnCode.USER_NOT_EXISTS;
        }catch (IncorrectCredentialsException e2){
            code = ReturnCode.PASSWORD_WRONG;
        } catch (AuthenticationException e3){
            code = ReturnCode.FAILED;
        }finally {
            return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc(),"UserId",userId);
        }

    }


    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public Page getAllUsers(@RequestParam("currPage")int currPage,
                            @RequestParam("pageSize")int pageSize){

        return userService.getAll(currPage,pageSize);
    }





    /**
     * 将用户指定为宣传组管理员
     * @param userId
     * @param activityList
     * @return
     */
//    @RequestMapping(value = "/group-manager/{userId}",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGroupManager(@PathVariable("userId") int userId,
//                                               @RequestBody List<Activity> activityList){
//
//        User user = userService.getUserById(userId);
//
//        if(user==null){
//            return MessageObject.dealMap(List.of("success","message"),List.of(false,"用户不存在"));
//        }
//
//        user.setActivities(activityList);
//        boolean success = userService.addGroupManager(user)==user.getActivities().size();
//
//        return MessageObject.dealMap(List.of("success"),List.of(success));
//    }




    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> deleteUser(@PathVariable("userId")String userId){

        User user = userService.getUserById(userId);

        ReturnCode code = ReturnCode.SUCCESS;
        boolean success = true;
        if(user==null){
            success = false;
            code = ReturnCode.NODATA;
        }else{
            userService.deleteUser(userId);
        }
        return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc());

    }

    

    /**
     * 获取一个用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}" ,method = RequestMethod.GET)
    @ResponseBody
    public Map getUserById(@PathVariable("userId")String userId){
        User user = userService.getUserById(userId);


        ReturnCode code = ReturnCode.SUCCESS;
        boolean success = true;
        if(user==null){
            success = false;
            code = ReturnCode.NODATA;
        }else{
            user.setPassword(null);
        }
        return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc(),"data",user);
    }





    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody User user,@RequestParam("name")String name){

        ReturnCode code = ReturnCode.SUCCESS;
        boolean success = false;
        String userId = "-1";
        String studentId = user.getUserId()+"";//用学生的学号作为账户

        User userInDb = userService.getUserById(studentId);

        if(userInDb!=null){
            code = ReturnCode.REPEAT_OPERATION;
            return Map.of("success",success,"code",code.getCode(),"msg",code.getDesc(),"userId",userId);
        }

        Student student = studentService.getStudentByColumn("student_id",studentId);

        //比对基础数据库，验证是否存在该学号
        if(student==null){
            log.error("注册的该学号不存在=>studentId:"+studentId);
            code = ReturnCode.STUDENT_NOT_EXISTS;
            return Map.of("success",false,"code",code.getCode(),"msg",code.getDesc(),"userId",userId);
        }

        //验证学号和本人姓名是否一致
        if(!student.getName().equals(name)){
            log.error("注册账号时遇到致命错误，学号与姓名不匹配=>id:"+studentId+"   姓名:"+name);
            code = ReturnCode.ID_NOT_MATCH_NAME;
            return Map.of("success",success,"msg",code.getDesc(),"code",code.getCode(),"userId",userId);
        }


        success = userService.addUser(user);

        if(success){
            userId = user.getUserId();
        }

        return Map.of("success",success,"msg",code.getDesc(),"code",code.getCode(),"userId",userId);
    }


    /**
     * 用户修改密码
     * @param userId
     * @param user
     * @return
     */
    @RequestMapping(value = "/{id}/revision/pwd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateUser(@PathVariable("id")String userId,
                                         @RequestParam("pwdNew")String pwdNew,
                                         @RequestBody User user){


        ReturnCode code = ReturnCode.SUCCESS;

        /*两个参数不一致*/
        if(!user.getUserId().equals(userId)){
            code = ReturnCode.PARAMS_ERROR;
            return Map.of("success",false,"msg",code.getDesc(),"code",code.getCode());
        }


        User userTmp = userService.getUserById(userId);

        /*用户不存在*/
        if(userTmp==null){
            code = ReturnCode.USER_NOT_EXISTS;
            return Map.of("success",false,"msg",code.getDesc(),"code",code.getCode());
        }

        if(!userTmp.getPassword().equals(user.getPassword())){
            return Map.of("success",false,"code",ReturnCode.PASSWORD_WRONG.getCode(),
                    "msg",ReturnCode.PASSWORD_WRONG.getDesc());
        }

        try{
            user.setPassword(pwdNew);
            userService.changePassword(user);
        }catch (Exception e){
            return Map.of("success",false,"message",e.getMessage());
        }

        return Map.of("success",true,"msg",code.getDesc(),"code",code.getCode());
    }

}

