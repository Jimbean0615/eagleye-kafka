/*
 * 类名 Test.java
 *
 * 版本信息 
 *
 * 日期 2016-2-17
 *
 * 版权声明Copyright (C) 2016 YouGou Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为优购科技开发研制，未经本公司正式书面同意，其他任何个人、团体不得
 * 使用、复制、修改或发布本软件。
 */
package com.yougou.eagleye.test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-17 下午5:43:35
 */
public class Test {
	
	private static ThreadLocal<AtomicLong> count = new ThreadLocal<AtomicLong>();
	private static ThreadLocal<Long> lastTime = new ThreadLocal<Long>();
	
	/**
	 * 批量算法
	 * 
	* @Title: isSave 
	* @Description: TODO
	* @return boolean  
	* @throws 
	* @add on 2016-2-16 下午4:04:10
	 */
	private static boolean isSave(){
		boolean status = false;
		System.out.println(count.get());
		System.out.println(lastTime.get());
		if(count.get() == null){
			count.set(new AtomicLong(0));
		}
		
		if(lastTime.get() == null){
			lastTime.set(-1L);
		}
		if(count.get().incrementAndGet()%100 == 0){//每100个交给一个线程进行批量保存
			status = true;
		}
		if(System.currentTimeMillis() - lastTime.get() > 5000){//或者五秒钟保存一次
			status = true;
		}
		if(status){
			lastTime.set(System.currentTimeMillis());
			count.get().getAndSet(0);
		}
		return status;
	}
	
	/** 
	 * @Title: main 
	 * @Description: TODO
	 * @param args void  
	 * @throws InterruptedException 
	 * @throws 
	 * @add on 2016-2-17 下午5:43:36
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println(isSave());
		Thread.sleep(5001);
		System.out.println(isSave());
		Thread.sleep(5001);
		System.out.println(isSave());
		
		System.out.println(new AtomicLong(0).get());
	}

}
