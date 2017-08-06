package freewebwork;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);

	@Resource
	private UserService userService;

	public void test1() {
		int id = 1;
		User user = this.userService.selectByPrimaryKey(id);
		logger.info(JSON.toJSONString(user));
	}
	
	public void test02() {
		int id = 2;
		int result = this.userService.deleteByPrimaryKey(id);
		logger.info("==========测试结果为 ：" + JSON.toJSONString(result) + "==============");
	}
	
	public void test03() {
		User user = new User();
		user.setAge(28);
		user.setPassword("No-one");
		user.setUserName("guest");
		int result = this.userService.insert(user);
		logger.info("==========测试结果为 ：" + JSON.toJSONString(result) + "==============");
	}
	
	public void test04() {
		User user = new User();
		// id为主键，此方法会判断id=3是否在表中存在
		user.setId(3);
		user.setAge(28);
		user.setPassword("everyone");
		user.setUserName("guest");
		int result = this.userService.updateByPrimaryKey(user);
		logger.info("==========测试结果为 ：" + JSON.toJSONString(result) + "==============");
	}
	
	@Test
	public void test05() {
		User user = new User();
		// id为主键，此方法会判断id=3是否在表中存在
		user.setId(3);
		user.setAge(28);
		int result = this.userService.updateByPrimaryKeySelective(user);
		logger.info("==========测试结果为 ：" + JSON.toJSONString(result) + "==============");
	}
}
