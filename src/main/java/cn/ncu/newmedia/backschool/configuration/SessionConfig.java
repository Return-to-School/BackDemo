package cn.ncu.newmedia.backschool.configuration;

import cn.ncu.newmedia.backschool.filters.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maoalong
 * @date 2020/2/8 23:01
 * @description
 */
@Configuration
public class SessionConfig implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns(new String[]{"/activity/**","/apply/**",
                "/feedback/**","/user/**","/student/**"}).excludePathPatterns(
                new String[]{"/**/verification","/**/register"});
    }
}
