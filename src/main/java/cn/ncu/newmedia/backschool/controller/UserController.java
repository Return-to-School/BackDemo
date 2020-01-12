package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.pojo.Student;
import cn.ncu.newmedia.backschool.pojo.User;
import cn.ncu.newmedia.backschool.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author maoalong
 * @date 2020/1/12 16:22
 * @description
 */
@Controller("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    private String showLoginPage(){return "login";}


    /**
     * 利用shiro进行登录验证
     * @param user
     * @return
     */
    @RequestMapping("/validate")
    private String validate(@RequestBody User user){
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

}

