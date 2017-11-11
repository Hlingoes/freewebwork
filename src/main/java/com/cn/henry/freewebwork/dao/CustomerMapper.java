package com.cn.henry.freewebwork.dao;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.entity.Customer;

public interface CustomerMapper {

	/**
	 * 添加新客户
	 * 
	 * @param customer
	 */
	void save(Customer customer);

	/**
	 * 根据DataTables的查询方式获取 客户列表
	 * 
	 * @param param
	 * @return
	 */
	List<Customer> findByParam(Map<String, Object> param);

	/**
	 * 获取客户的总数量
	 * 
	 * @return
	 */
	Long count();

	/**
	 * 根据DataTables的查询方式获取客户总数量
	 * 
	 * @param param
	 * @return
	 */
	Long countByParam(Map<String, Object> param);

	/**
	 * 根据主键查询客户
	 * 
	 * @param id
	 * @return
	 */
	Customer findById(Integer id);

	/**
	 * 根据主键删除客户
	 * 
	 * @param id
	 */
	void del(Integer id);

	/**
	 * 修改客户信息
	 * 
	 * @param customer
	 */
	void update(Customer customer);

	/**
	 * 根据用户ID获取用户的客户以及公开客户
	 * 
	 * @param currentUserId
	 * @return
	 */
	List<Customer> findByUserIdAndEmptyUserId(Integer currentUserId);

	/**
	 * 首页柱状图的统计
	 * 
	 * @return
	 */
	List<Map<String, Object>> findTotal();
}
