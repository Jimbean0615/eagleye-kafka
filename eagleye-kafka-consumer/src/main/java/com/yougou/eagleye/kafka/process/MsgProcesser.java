package com.yougou.eagleye.kafka.process;

/**
 * Class description goes here.
 * 
 * @version
 * @author zhang.jb on 2016-2-4 上午10:27:46
 */
public interface MsgProcesser {
	
	public void process(String message);
}