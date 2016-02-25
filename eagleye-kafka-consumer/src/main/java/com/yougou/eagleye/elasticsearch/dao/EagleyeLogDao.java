package com.yougou.eagleye.elasticsearch.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yougou.eagleye.elasticsearch.domain.EagleyeLog;

public interface EagleyeLogDao extends
		ElasticsearchRepository<EagleyeLog, String> {


	/**
	 * 根据body查询
	 * @param body
	 * @param pageable
	 * @return
	 */
	Page<EagleyeLog> findByBody(String body, Pageable pageable);
	
	Page<EagleyeLog> findByAppNameAndBodyAndTimestampBetweenOrderByVersionDesc(String appName,String body, Long from, Long to, Pageable pageable);
	

}