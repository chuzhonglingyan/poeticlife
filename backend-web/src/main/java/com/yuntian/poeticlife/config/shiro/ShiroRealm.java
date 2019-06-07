package com.yuntian.poeticlife.config.shiro;

import com.yuntian.basecommon.util.PasswordUtil;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.service.BackendOperaterService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/2/20 0020 23:27
 * @Description:
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private BackendOperaterService backendOperaterService;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.error("————进入身份认证————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken; // 从数据库获取对应用户名密码的用户
        BackendOperater user = backendOperaterService.findBy("accountName", token.getUsername());
        String savePassWord = new String((char[]) token.getCredentials());
        if (null == user) {
            throw new UnknownAccountException("账号不存在");
        } else if (!PasswordUtil.verify(savePassWord, user.getPassWord())) {
            throw new UnknownAccountException("密码不正确");
        }
        token.setUsername(String.valueOf(user.getId()));
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getId(), savePassWord, getName());
        log.error("————身份认证通过————");
        return simpleAuthenticationInfo;
    }


    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.error("————权限认证————");
        Long userId = (Long) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); //获得该用户角色
        String role = backendOperaterService.getRole(userId);
        log.error("当前角色：" + role);
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        // 设置该用户拥有的角色
        info.setRoles(set);
        //获取用户权限
        List<Menu> menuList = backendOperaterService.getNavMenuListByOperater(userId);
        Set<String> stringPermissions = new HashSet<>();
        menuList.forEach(menu -> {
            stringPermissions.add(menu.getMenuCode());
        });
        //添加权限
        info.setStringPermissions(stringPermissions);

        return info;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);

    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }


}
