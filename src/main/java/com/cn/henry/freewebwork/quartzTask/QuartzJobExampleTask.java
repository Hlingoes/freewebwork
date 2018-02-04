package com.cn.henry.freewebwork.quartzTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class QuartzJobExampleTask
{
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("开始执行测试打印： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ★★★★★★★★★★★");
	}

}
