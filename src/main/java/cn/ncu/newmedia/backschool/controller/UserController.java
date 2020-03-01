package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import cn.ncu.newmedia.backschool.service.StudentService;
import cn.ncu.newmedia.backschool.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
        Subject subject = SecurityUtils.getSubject();

        String msg = "";
        String userId = "-1";
        boolean success = false;
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword());

        try{
            subject.login(token);
            msg = "登录成功";
            user = userService.getUsersByAccount(user.getAccount());
            userId = user.getId()+"";
            success = true;
        }catch (UnknownAccountException e1){
            msg = "用户不存在";
        }catch (IncorrectCredentialsException e2){
            msg = "密码错误";
        }
        catch (AuthenticationException e3){
            msg = e3.getMessage();
        }finally {
            return Map.of("success",success,"message",msg,"UserId",userId);
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
    public Map<String,Object> deleteUser(@PathVariable("userId")Integer userId){

        boolean success = userService.deleteUser(userId);
        return Map.of("success",success);

    }

    

    /**
     * 获取一个用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}" ,method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable("userId")Integer userId){
        return userService.getUserById(userId);
    }





    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody User user,@RequestParam("name")String name){

        String message = "注册成功";
        boolean success = false;
        String studentCard = user.getAccount();//用学生的学号作为账户

        User userInDb = userService.getUsersByAccount(studentCard);

        if(userInDb!=null){
            message = "学生已经用该学号注册了";
            return Map.of("success",success,"message",message);
        }

        Student student = studentService.getStudentByColumn("student_card",studentCard);

        //比对基础数据库，验证是否存在该学号
        if(student==null){
            return Map.of("success",false,"message","学号不存在");
        }

        //验证学号和本人姓名是否一致
        if(!student.getName().equals(name)){
            return Map.of("success",success,"message","学号与姓名不匹配");
        }


        success = userService.addUser(user);
        return Map.of("success",success,"message",message);
    }

}

