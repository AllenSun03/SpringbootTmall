package com.how2java.tmall.realm;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

//自定义realm，shiro从Realm里获取安全数据
//意思就是自己写一个类继承AuthorizingRealm类，然后重写AuthorizingRealm的getAuthorizationInfo方法
public class JPARealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	//重写授权方法doGetAuthorizationInfo
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		//SimpleAuthorizationInfo：代表用户角色权限信息
		////通过名称查找权限，简化操作（正常是通过名称找角色，通过角色查询对应的权限）
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		return s;
	}

	//重写认证方法doGetAuthenticationInfo
	//用户登录时会调用这个方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 从token里获取身份信息：用户名userName，token代表用户输入的信息
		// principal : 主体的标示，可以有多个，但是需要具有唯一性，常见的有用户名，手机号，邮箱等
		String userName = token.getPrincipal().toString();
		//根据用户名（唯一标识）从数据库里查到这个用户
		User user = userService.getByName(userName);
		//取出这个用户的密码
		String passwordInDB = user.getPassword();
		//取出这个用户的盐
		String salt = user.getSalt();
		//SimpleAuthenticationInfo ：代表该用户的认证信息。参数为“用户名+密码+盐”
		//this.getName()是获取CachingRealm的名字
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt),
				getName());
		return authenticationInfo;
	}

}
