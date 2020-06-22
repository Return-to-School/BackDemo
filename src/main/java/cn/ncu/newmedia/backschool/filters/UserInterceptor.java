package cn.ncu.newmedia.backschool.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author maoalong
 * @date 2020/2/6 20:18
 * @description
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = (String) request.getSession().getAttribute("UserId");

        if(userId==null){
            /*重定向到页面登录*/
            return false;
        }else{
            return true;
        }
    }
}
