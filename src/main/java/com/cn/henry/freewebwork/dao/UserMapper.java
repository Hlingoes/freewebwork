package com.cn.henry.freewebwork.dao;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.entity.User;

public interface UserMapper {

	/**
	 * 根据手机号码查找用户
	 * 
	 * @param tel
	 * @return
	 */
	User findByTel(String tel);

	/**
	 * 获取所有的用户
	 * 
	 * @return
	 */
	List<User> findAll();

	/**
	 * 根据DataTables中的参数进行查询
	 * 
	 * @param param
	 * @return
	 */
	List<User> findByParam(Map<String, Object> param);

	Long findUserCount();

	Long findUserCountByParam(Map<String, Object> param);

	void save(User user);

	/**
	 * 保存用户的角色的对应关系
	 * 
	 * @param user
	 *            用户对象
	 * @param role
	 *            角色ID数组
	 */
	void saveUserAndRole(User user, String[] role);

	/**
	 * 根据主键删除用户
	 * 
	 * @param id
	 */
	void del(Integer id);

	/**
	 * 根据用户主键删除和角色的对应关系
	 * 
	 * @param id
	 *            用户的主键
	 */
	void delUserAndRole(Integer id);

	/**
	 * 根据用户的ID查询用户以及管理的角色
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	User findUserWithRoleById(Integer id);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	void editUser(User user);

	/**
	 * 根据主键查找用户
	 * 
	 * @param userId
	 * @return
	 */
	User findById(Integer userId);

	/**
	 * 根据微信中的UserID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	User findByUserId(String userId);
}
