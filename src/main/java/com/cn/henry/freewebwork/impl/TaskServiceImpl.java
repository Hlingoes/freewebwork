package com.cn.henry.freewebwork.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.henry.freewebwork.dao.TaskMapper;
import com.cn.henry.freewebwork.entity.Task;
import com.cn.henry.freewebwork.service.TaskService;
import com.cn.henry.freewebwork.utils.ShiroUtil;

@Service("taskServiceImpl")
@Transactional
public class TaskServiceImpl implements TaskService{

    @Resource
    private TaskMapper taskMapper;

    /**
     * 添加新的待办任务
     * @param task
     */
    public void save(Task task) {
        task.setCreatetime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
        task.setUserid(ShiroUtil.getCurrentUserId());
        task.setDone(false);
        taskMapper.save(task);
    }

    /**
     * 获取当前登录用户的所有待办任务
     * @return
     */
    public List<Task> findAllByCurrentUser() {
        return taskMapper.findByUserId(ShiroUtil.getCurrentUserId());
    }

    /**
     * 改变任务的状态
     * @param taskId 任务ID
     * @param state 已完成 true,未完成 false
     */
    public void changeTaskState(String taskId, boolean state) {
        Task task = taskMapper.findById(taskId);
        if(task == null) {
            throw new NotFoundException();
        } else {
            if(task.getUserid().equals(ShiroUtil.getCurrentUserId())) {
                if(state) {
                    task.setDone(state);
                    task.setDonetime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
                } else {
                    task.setDone(state);
                    task.setDonetime("");
                }
                taskMapper.update(task);
            } else {
                throw new ForbiddenException();
            }
        }
    }

    /**
     * 根据客户id查询相关未完成的待办任务
     * @param id
     * @return
     */
    public List<Task> findunDoneTaskByCustId(Integer id) {
        return taskMapper.findByCustIdAndState(id,false);
    }
}
