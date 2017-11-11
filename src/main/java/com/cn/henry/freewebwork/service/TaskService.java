package com.cn.henry.freewebwork.service;

import java.util.List;

import com.cn.henry.freewebwork.entity.Task;

public interface TaskService {
	
	void save(Task task);

    List<Task> findAllByCurrentUser();

    public void changeTaskState(String taskId, boolean state);

    List<Task> findunDoneTaskByCustId(Integer id);
}
