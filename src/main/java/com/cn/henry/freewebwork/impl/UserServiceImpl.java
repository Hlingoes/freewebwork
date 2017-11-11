package com.cn.henry.freewebwork.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cn.henry.freewebwork.dao.UserMapper;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.UserService;
import com.cn.henry.freewebwork.utils.ShiroUtil;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private UserMapper userMapper;
	
	@Value("${user.salt}")
	private String passwordSalt;

	public List<User> findAllUser() {
		return userMapper.findAll();
	}

	/**
	 * 根据DataTables中的参数进行查询
	 * 
	 * @param param
	 * @return
	 */
	public List<User> findUserByParam(Map<String, Object> param) {
		return userMapper.findByParam(param);
	}

	/**
	 * 获取用户的总数量
	 * 
	 * @return
	 */
	public Integer findUserCount() {
		return userMapper.findUserCount().intValue();
	}

	/**
	 * 根据查询条件获取用户的数量
	 * 
	 * @param param
	 * @return
	 */
	public Integer findUserCountByParam(Map<String, Object> param) {
		return userMapper.findUserCountByParam(param).intValue();
	}

	/**
	 * 保存新用户
	 * 
	 * @param user
	 * @param role
	 */
	public void saveNewUser(User user, String[] role) {
		// 产生微信端使用的userid
		user.setUserid(String.valueOf(System.currentTimeMillis()));
		user.setPassword(DigestUtils.md5Hex(user.getPassword() + passwordSalt));
		user.setCreatetime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
		user.setState(User.USER_STATE_OK);
		userMapper.save(user);
		// 保存用户的角色的关系
		if (role != null) {
			userMapper.saveUserAndRole(user, role);
		}
		logger.info("{}添加了新用户{}", ShiroUtil.getCurrentUserName(), user.getUsername());
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户ID
	 */
	public void delUserById(Integer id) {
		// 先删除和角色的对应关系
		userMapper.delUserAndRole(id);
		// 再删除用户
		userMapper.del(id);
		logger.info("{}删除了用户{}", ShiroUtil.getCurrentUserName(), id);
	}

	/**
	 * 根据用户的ID查询用户以及管理的角色
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public User findUserWithRoleById(Integer id) {
		return userMapper.findUserWithRoleById(id);
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 * @param role
	 */
	public void editUser(User user, String[] role) {
		// 删除原有的和角色之间的关系，
		userMapper.delUserAndRole(user.getId());
		// 然后重建
		userMapper.saveUserAndRole(user, role);
		// 修改用户
		userMapper.editUser(user);
	}

	/**
	 * 根据微信的UserID获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User findByUserId(String userId) {
		return userMapper.findByUserId(userId);
	}

	@Override
	public int deleteByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User selectByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(User record) {
		// TODO Auto-generated method stub
		return 0;
	}
}
