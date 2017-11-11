package com.cn.henry.freewebwork.service;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.entity.Customer;

public interface CustomerService {

	void saveNewCustomer(Customer customer);

	List<Customer> findUserByParam(Map<String, Object> param);

	Integer findCustomerCount();

	Integer findUserCountByParam(Map<String, Object> param);

	Customer findCustomerById(Integer id);

	void delCustomer(Integer id);

	void publicCustomer(Integer id);

	void tranCustomer(Integer custId, Integer userId);

	List<Customer> findCustomerByCurrentUser();

	List<Map<String, Object>> homeTotal();

	List<Customer> findCustomerByUserId(Integer id);

	Customer findCustById(Integer id);
}
