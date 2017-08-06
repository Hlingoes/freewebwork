package freewebwork;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMybatisPaging {
	
	private static Logger logger = Logger.getLogger(TestMybatisPaging.class);
	
	@Resource
	private TaskScheduleJobService taskScheduleJobService;

	@Test
	public void test1() {
		int pageNo = 1;
		int pageSize = 10;
		String jobGroup = "test";
		PageHelper.startPage(pageNo, pageSize);
		List<TaskScheduleJob> list = this.taskScheduleJobService.selectByJobGroup(jobGroup);
		// 用PageInfo对结果进行包装
		PageInfo<TaskScheduleJob> page = new PageInfo<TaskScheduleJob>(list);
		// 测试PageInfo全部属性
		System.out.println(page.getPageNum());
		System.out.println(page.getPageSize());
		System.out.println(page.getStartRow());
		System.out.println(page.getEndRow());
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(page.getNavigateFirstPage());
		System.out.println(page.getNavigateLastPage());
		System.out.println(page.isHasPreviousPage());
		System.out.println(page.isHasNextPage());
		logger.info(JSON.toJSONString(page));
	}

}
