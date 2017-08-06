package com.cn.henry.freewebwork.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.henry.freewebwork.dao.UserMapper;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource  
    private UserMapper userDao;  

	@Override
	public int deleteByPrimaryKey(Object userId) {
		return this.userDao.deleteByPrimaryKey(Integer.parseInt(String.valueOf(userId)));
	}

	@Override
	public int insert(User record) {
		return this.userDao.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return this.userDao.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Object userId) {
		return this.userDao.selectByPrimaryKey(Integer.parseInt(String.valueOf(userId)));
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return this.userDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return this.userDao.updateByPrimaryKey(record);
	}
}
