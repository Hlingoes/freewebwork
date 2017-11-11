package com.cn.henry.freewebwork.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cn.henry.freewebwork.entity.Progress;
import com.cn.henry.freewebwork.entity.ProgressFile;
import com.github.pagehelper.PageInfo;

public interface ProgressService {

	void saveNewProgress(Progress progress, MultipartFile[] file);

	List<Progress> findProgressByCustId(Integer id);

	List<ProgressFile> findProgressFileByCustId(Integer id);
	
	PageInfo<Progress> findProgressByPageAndParam(String userid, String progress, String date, String context, String p);
}
