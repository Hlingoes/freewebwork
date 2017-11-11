package com.cn.henry.freewebwork.core;

/**
 * 响应信息的基本内容格式
 * @author Hlingoes
 * @cite https://github.com/snailxr/quartz-spring_demo
 *
 */
public class BaseResult {
	private boolean flag = true;
	
	// 提示信息
	private String msg;
	
	// 返回的数据
	private Object obj;

	public BaseResult() {

	}

	public BaseResult(boolean flag, String msg, Object obj) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.obj = obj;
	}

	public BaseResult(boolean flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
