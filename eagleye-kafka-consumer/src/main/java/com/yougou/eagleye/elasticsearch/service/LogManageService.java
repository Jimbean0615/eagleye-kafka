package com.yougou.eagleye.elasticsearch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.eagleye.elasticsearch.dao.EagleyeLogDao;
import com.yougou.eagleye.elasticsearch.domain.EagleyeLog;

@Service("logManageService")
public class LogManageService{
	
	private final static Logger logger = LoggerFactory.getLogger(LogManageService.class);

	@Autowired
    private EagleyeLogDao eagleyeLogDao;
	
	public void saveAlertLog(EagleyeLog log) throws Exception{
		if(log!=null){
			this.eagleyeLogDao.save(log);
			logger.debug("save EagleyeLog id==>" + log.getId());
		}
	}
	
}