package com.cn.henry.freewebwork.dao;

import java.util.List;

import com.cn.henry.freewebwork.entity.Role;

public interface RoleMapper {
	/**
	 * 根据用户ID查找具有的角色列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> findByUserId(Integer userId);

}
