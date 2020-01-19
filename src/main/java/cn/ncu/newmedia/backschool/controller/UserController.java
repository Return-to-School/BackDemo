package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import cn.ncu.newmedia.backschool.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author maoalong
 * @date 2020/1/12 16:22
 * @description
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String showLoginPage(){return "login";}


    /**
     * 利用shiro进行登录验证
     * @param user
     * @return
     */
    @RequestMapping("/validate")
    @ResponseBody
    public String validate(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword());

        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }

        if(subject.hasRole("user")){
            return "登录成功";
        }else{
            if(userService.hasUser(user.getAccount())){
                return "密码错误";
            }else{
                return "用户名不存在";
            }
        }
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.getAll();
    }


    /**
     * 将用户指定为宣传组管理员
     * @param userId
     * @param activityList
     * @return
     */
    @RequestMapping("/addGroupManager/{userId}")
    @ResponseBody
    public Map<String, Object> addGroupManager(@PathVariable("userId") int userId,
                                               @RequestBody List<Activity> activityList){

        User user = userService.getUserByColumn("user_id",userId).get(0);

        if(user==null){
            return MessageObject.dealMap(List.of("success","message"),List.of(false,"用户不存在"));
        }

        user.setActivities(activityList);
        boolean success = userService.addGroupManager(user)==user.getActivities().size();

        return MessageObject.dealMap(List.of("success"),List.of(success));
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/delete/{userId}")
    @ResponseBody
    public Map<String,Object> deleteUser(@PathVariable("userId")int userId){

        boolean success = userService.deleteUser(userId);
        return MessageObject.dealMap(List.of("success"),List.of(success));
    }


}

