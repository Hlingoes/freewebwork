package com.cn.henry.freewebwork.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cn.henry.freewebwork.dao.CustomerMapper;
import com.cn.henry.freewebwork.dao.ProgressFileMapper;
import com.cn.henry.freewebwork.dao.ProgressMapper;
import com.cn.henry.freewebwork.entity.Customer;
import com.cn.henry.freewebwork.entity.Progress;
import com.cn.henry.freewebwork.entity.ProgressFile;
import com.cn.henry.freewebwork.service.ProgressService;
import com.cn.henry.freewebwork.utils.ShiroUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("progressServiceImpl")
@Transactional
public class ProgressServiceImpl implements ProgressService{

    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private ProgressFileMapper progressFileMapper;

    @Resource
    private CustomerMapper customerMapper;

    /**
     * 新增跟进记录
     * @param progress 跟进对象
     * @param file 关联文件集合
     */
    public void saveNewProgress(Progress progress,MultipartFile[] file) {
        //更新客户的最近跟进信息
        Customer customer = customerMapper.findById(progress.getCustid());
        customer.setProgress(progress.getProgress());
        customer.setProgresstime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
        customerMapper.update(customer);
        progress.setUserid(ShiroUtil.getCurrentUserId());
        progress.setCreatetime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
        progressMapper.save(progress);
        //判断文件集合是否有文件
        if(file != null && file.length > 0) {
            for(MultipartFile multipartFile : file) {
                if(!multipartFile.isEmpty()) {
                    String key = null;
                    ProgressFile progressFile = new ProgressFile();
                    progressFile.setCreatetime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
                    progressFile.setUserid(ShiroUtil.getCurrentUserId());
                    progressFile.setCustid(progress.getCustid());
                    progressFile.setFilename(multipartFile.getOriginalFilename());
                    progressFile.setPath(key);
                    progressFile.setProgressid(progress.getId());
                    progressFileMapper.save(progressFile);
                }
            }
        }
    }

    /**
     * 根据客户Id，查询对应的跟进记录
     * @param id 客户ID
     * @return
     */
    public List<Progress> findProgressByCustId(Integer id) {
        return progressMapper.findByCustId(id);
    }

    /**
     * 根据客户ID，获取关联的文件
     * @param id
     * @return
     */
    public List<ProgressFile> findProgressFileByCustId(Integer id) {
        return progressFileMapper.findByCustId(id);
    }

	@Override
	public PageInfo<Progress> findProgressByPageAndParam(String userid, String progress, String date, String context,
			String p) {
		Map<String,Object> param = new HashMap<>();
        param.put("userid",userid);
        param.put("progress",progress);
        if(StringUtils.isNotEmpty(context)) {
            param.put("context", "%" + context + "%");
        }
        LocalDateTime dateTime = LocalDateTime.now();
        if(StringUtils.isNotEmpty(date)) {
            if("今天".equals(date)) {
                param.put("startDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
                param.put("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
            } else if("昨天".equals(date)) {
                dateTime = dateTime.minusDays(1);
                param.put("startDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
                param.put("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
            } else if("本周".equals(date)) {
                LocalDate localDate = LocalDate.now();
                localDate = localDate.minusDays(localDate.getDayOfWeek().getValue());
                param.put("startDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));
                localDate = localDate.minusDays(localDate.getDayOfWeek().getValue()).plusDays(7);
                param.put("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));
            } else if("本月".equals(date)) {
                LocalDate today = LocalDate.now();
                //本月的第一天
                LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
                param.put("startDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(firstday));
                //本月的最后一天
                LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
                param.put("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(lastDay));
            } else if("更早".equals(date)) {
                LocalDate today = LocalDate.now();
                LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
                param.put("endDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(firstday));
            }
        }
        PageHelper.startPage(Integer.valueOf(p), 10);
        List<Progress> progressList = progressMapper.findByParam(param);
        PageInfo<Progress> pageInfo = new PageInfo<Progress>(progressList);
        return pageInfo;
	}
}
