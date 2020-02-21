//package cn.ncu.newmedia.backschool.realm;
//
//import cn.ncu.newmedia.backschool.dao.UserDao;
//import cn.ncu.newmedia.backschool.dao.UserRoleDao;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Set;
//
///**
// * @author maoalong
// * @date 2020/1/12 20:38
// * @description
// */
//public class CustomRealm extends AuthorizingRealm {
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private UserRoleDao userRoleDao;
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        String username = (String) principalCollection.getPrimaryPrincipal();
//
//        /*获取用户的角色，进行授权*/
//        Set<String> roles = getRolesByUsername(username);
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addRoles(roles);
//        return simpleAuthorizationInfo;
//    }
//
//    private Set<String> getRolesByUsername(String username) {
//        return userRoleDao.getRolesByAccount(username);
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String username = (String) authenticationToken.getPrincipal();
//
//        /*通过账号获取密码，进行认证*/
//        String password = getPasswordByUsername(username);
//        if(password==null) return null;
//
//        SimpleAuthenticationInfo simpleAccountRealm = new SimpleAuthenticationInfo(username,password,"CustomRealm");
//        return simpleAccountRealm;
//    }
//
//    private String getPasswordByUsername(String username) {
//        return userDao.getPasswordByAccount(username);
//    }
//}
