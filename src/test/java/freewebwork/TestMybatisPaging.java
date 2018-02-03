package freewebwork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMybatisPaging {
	
	private static Logger logger = Logger.getLogger(TestMybatisPaging.class);
	
	private static ObjectMapper jsonMapper = new ObjectMapper();
	
	@Resource
	private TaskScheduleJobService taskScheduleJobService;

	@Test
	public void test1() throws JsonProcessingException {
		int pageNo = 1;
		int pageSize = 10;
		String jobGroup = "test";
		PageHelper.startPage(pageNo, pageSize);
		Map<String, String> condition = new HashMap<String, String> ();
        condition.put("sidx", "updateTime");
        condition.put("sord", "desc");
        condition.put("jobGroup", jobGroup); // 用作对条件查询的测试
		List<TaskScheduleJob> list = this.taskScheduleJobService.selectByCondition(condition);
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
		logger.info(jsonMapper.writeValueAsString(page));
	}

}
