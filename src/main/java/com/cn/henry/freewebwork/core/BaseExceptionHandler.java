package com.cn.henry.freewebwork.core;

import java.io.IOException;

import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	public BaseResult schedulerExceptionHandler(SchedulerException e) {
		log.error(e.getMessage(), e);
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(false);
		baseResult.setMsg("任务状态改变失败！");
		return baseResult;
	}
	
	@ExceptionHandler(IOException.class)
	public void iOExceptionHandler(IOException e) {
		log.error(e.getMessage(), e);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public void notFoundExceptionHandler (NotFoundException e){
		log.error(e.getMessage(), e);
	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public void jsonProcessingExceptionHandler(IOException e) {
		log.error(e.getMessage(), e);
	}

}
