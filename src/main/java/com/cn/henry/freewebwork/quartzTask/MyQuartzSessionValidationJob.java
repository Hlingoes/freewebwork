package com.cn.henry.freewebwork.quartzTask;

import org.apache.log4j.Logger;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 基于Quartz 2.* 版本的实现 
 * @author Hlingoes
 * @date 2018年2月3日下午11:48:53
 * @cite http://blog.csdn.net/rogerjava/article/details/72628631 处理shiro和spring quartz 冲突
 */
public class MyQuartzSessionValidationJob implements Job
{
	/** 
     * Key used to store the session manager in the job data map for this job. 
     */  
    public static final String SESSION_MANAGER_KEY = "sessionManager";  

    /*-------------------------------------------- 
    |    I N S T A N C E   V A R I A B L E S    | 
    ============================================*/  
    private final Logger log = Logger.getLogger(this.getClass());

    /*-------------------------------------------- 
    |         C O N S T R U C T O R S           | 
    ============================================*/  

    /*-------------------------------------------- 
    |  A C C E S S O R S / M O D I F I E R S    | 
    ============================================*/  

    /*-------------------------------------------- 
    |               M E T H O D S               | 
    ============================================*/  

    /** 
     * Called when the job is executed by quartz. This method delegates to the <tt>validateSessions()</tt> method on the 
     * associated session manager. 
     * 
     * @param context the Quartz job execution context for this execution. 
     */  
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        JobDataMap jobDataMap = context.getMergedJobDataMap();  
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);  
        if (log.isDebugEnabled()) {  
            log.debug("Executing session validation Quartz job...");  
        }  
        sessionManager.validateSessions();  
        if (log.isDebugEnabled()) {  
            log.debug("Session validation Quartz job complete.");  
        }  
    }  
}
