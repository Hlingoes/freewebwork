package com.cn.henry.freewebwork.entity;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.cn.henry.freewebwork.utils.Excel.ExcelCell;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskScheduleJob {
	
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
    private Long jobId;

    //格式化日期属性  
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ExcelCell(index = 7)
    private Timestamp createTime;

    //格式化日期属性  
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ExcelCell(index = 8)
    private Timestamp updateTime;

    @NotNull(message = "任务名不能为空")
    @ExcelCell(index = 0)
    private String jobName;

    @ExcelCell(index = 1)
    private String jobGroup;

    @ExcelCell(index = 2)
    private String jobStatus;

    @NotNull(message = "cron表达式不能为空")
    @ExcelCell(index = 3)
    private String cronExpression;

    @ExcelCell(index = 4)
    private String description;

    @NotNull(message = "执行的bean不能为空")
    @ExcelCell(index = 5)
    private String beanClass;

    private String isConcurrent;

    // 项目没有使用spring托管的方式，这个字段没有用处
    private String springId;

    @NotNull(message = "方法名不能为空")
    @ExcelCell(index = 6)
    private String methodName;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus == null ? null : jobStatus.trim();
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass == null ? null : beanClass.trim();
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent == null ? null : isConcurrent.trim();
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId == null ? null : springId.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }
    
}