package com.cn.henry.freewebwork.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;

import com.cn.henry.freewebwork.dao.RoleMapper;
import com.cn.henry.freewebwork.dao.UserMapper;
import com.cn.henry.freewebwork.entity.Role;
import com.cn.henry.freewebwork.entity.User;

@Controller
public class MyShiroRealm extends AuthorizingRealm {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	/**
	 * 权限认证方法
	 * 
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User shiroUser = (User) principalCollection.getPrimaryPrincipal();
		User user = userMapper.findByTel(shiroUser.getTel());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 获取用户对应的角色列表
			List<Role> roleList = roleMapper.findByUserId(user.getId());
			for (Role role : roleList) {
				info.addRole(role.getRolename());
			}
			return info;
		}
		return null;
	}

	/**
	 * 登录方法
	 * 
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 1. 把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 2. 从UsernamePasswordToken中获取tel
		String tel = token.getUsername();
		User user = userMapper.findByTel(tel);
		if (user != null) {
			if (User.USER_STATE_DISABLE.equals(user.getState())) {
				throw new LockedAccountException("该账号已被禁用");
			}
	        // 根据用户的情况，来构建AuthenticationInfo对象并返回，通常使用的实现类为SimpleAuthenticationInfo
	        // 以下信息从数据库中获取
	        // （1）principal：认证的实体信息，可以是tel，也可以是数据表对应的用户的实体类对象
	        Object principal = user;
	        // （2）credentials：密码
	        Object credentials = user.getPassword();
	        // （3）realmName：当前realm对象的name，调用父类的getName()方法即可
	        String realmName = super.getName();
	        // （4）盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
	        ByteSource credentialsSalt = ByteSource.Util.bytes(tel);
	        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
	        return info;
		}
		throw new UnknownAccountException("账号或密码错误");
	}
}
