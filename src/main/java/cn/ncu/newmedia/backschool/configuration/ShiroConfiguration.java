//package cn.ncu.newmedia.backschool.configuration;
//
//import cn.ncu.newmedia.backschool.realm.CustomRealm;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author maoalong
// * @date 2020/1/12 20:37
// * @description
// */
//@Configuration
//public class ShiroConfiguration {
//    @Bean
//    public CustomRealm getCustomeRealm(){
//        CustomRealm customRealm = new CustomRealm();
//        return customRealm;
//    }
//
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        autoProxyCreator.setProxyTargetClass(true);
//        return autoProxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
//            DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//
//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomRealm customRealm){
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        defaultWebSecurityManager.setRealm(customRealm);
//        return defaultWebSecurityManager;
//    }
//
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
//
//        shiroFilterFactoryBean.setLoginUrl("/user/login");
//        shiroFilterFactoryBean.setSuccessUrl("/user/index");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/loginError");
//        loadShiroFilterChain(shiroFilterFactoryBean);
//        return shiroFilterFactoryBean;
//    }
//
//    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
//        Map<String,String> filterChain = new HashMap<>();
//        filterChain.put("/user/login","anon");
//        filterChain.put("/user/verification","anon");
//        filterChain.put("/**","authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
//    }
//}
