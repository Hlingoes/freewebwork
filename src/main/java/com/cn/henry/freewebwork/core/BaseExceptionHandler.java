package com.cn.henry.freewebwork.core;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 统一处理controller抛出的异常
 * @author Hlingoes
 * @time 2017-9-3 22:51:19
 */
@ControllerAdvice
public class BaseExceptionHandler {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@ExceptionHandler(SchedulerException.class)
	@ResponseBody
	public BaseResult SchedulerExceptionHandler(SchedulerException e) {
		log.error(e.getMessage(), e);
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(false);
		baseResult.setMsg("任务状态改变失败！");
		return baseResult;
	}

}
