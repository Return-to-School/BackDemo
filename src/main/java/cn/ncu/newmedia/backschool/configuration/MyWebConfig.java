package cn.ncu.newmedia.backschool.configuration;

/**
 * @author maoalong
 * @date 2019/10/20 19:18
 * @description 配置资源路径，以便可以通过url直接访问数据资源
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /*与application配置文件中的文件存储路径对应*/
        registry.addResourceHandler("/files/**").addResourceLocations("file:c:/形象大使回母校/");
    }
}