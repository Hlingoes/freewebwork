package com.cn.henry.freewebwork.core;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class JqGridPage<T> {
	
    // 总记录数
	private long records;
	
	// 当前页数
	private int page;
	
	// 总页数
	private int total;
	
	//结果集
	private List<T> rows;
	
	public JqGridPage() {

	}
	
	public JqGridPage(PageInfo<T> pageInfo) {
		super();
		this.records = pageInfo.getTotal();
		this.page = pageInfo.getPageNum();
		this.total = pageInfo.getPages();
		this.rows = pageInfo.getList();
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
