package com.cn.henry.freewebwork.dao;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.entity.Progress;

public interface ProgressMapper {
    /**
     * 根据查询参数统计总记录数
     * @param param
     * @return
     */
    int countByParam(Map<String, Object> param);

    /**
     * 根据查询参数获取记录
     * @param param
     * @return
     */
    List<Progress> findByParam(Map<String, Object> param);

    /**
     * 保存新的进度对象
     * @param progress
     */
    void save(Progress progress);

    /**
     * 根据客户Id，查询对应的跟进记录
     * @param id 客户ID
     * @return
     */
    List<Progress> findByCustId(Integer id);
}
