package com.cn.henry.freewebwork.entity;

import java.util.List;

public class Progress {
    private Integer id;

    private String progress;

    private String createtime;

    private Integer custid;

    private Integer userid;

    private String mark;
    
    private User user;
    
    private Customer customer;
    
    private List<ProgressFile> progressFileList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<ProgressFile> getProgressFileList() {
		return progressFileList;
	}

	public void setProgressFileList(List<ProgressFile> progressFileList) {
		this.progressFileList = progressFileList;
	}
    
}