package com.first.first.Configuration;

import com.first.first.Service.UserServiceImp;
import com.first.first.bean.UserInformation;
import org.apache.catalina.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImp userServiceImp;

    /*登录认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        System.out.println(token.toString());
        //查询用户
        String username=token.getUsername();
        UserInformation userInformation=userServiceImp.findByAccountNumber(username);
        if (userInformation!=null){
            return new SimpleAuthenticationInfo(userInformation,userInformation.getPassword(),getName());
        }
        return null;
    }

    //权限认证

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        UserInformation userInformation=(UserInformation)principalCollection.getPrimaryPrincipal();
               info.addRole(userInformation.getRole());
        return info;
    }
}
