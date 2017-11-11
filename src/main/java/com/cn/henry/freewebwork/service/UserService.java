package com.cn.henry.freewebwork.service;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.core.BaseService;
import com.cn.henry.freewebwork.entity.User;

public interface UserService extends BaseService<User> {
	
	List<User> findAllUser();

	List<User> findUserByParam(Map<String, Object> param);

	Integer findUserCount();

	Integer findUserCountByParam(Map<String, Object> param);

	void saveNewUser(User user, String[] role);

	void delUserById(Integer id);

	User findUserWithRoleById(Integer id);

	void editUser(User user, String[] role);

	User findByUserId(String userId);
}
