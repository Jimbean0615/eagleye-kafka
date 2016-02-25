package com.yougou.eagleye;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougou.eagleye.common.Contant;
import com.yougou.eagleye.elasticsearch.constants.AppConstants;
import com.yougou.eagleye.holder.SpringContextHolder;
import com.yougou.eagleye.kafka.client.ConsumerClient;
import com.yougou.eagleye.kafka.factory.ConsumerFactory;
import com.yougou.eagleye.kafka.process.MsgProcesser;

/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-4 上午11:23:10
 */
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	private static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";
	
	public static ClassPathXmlApplicationContext context;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static ThreadLocal<AtomicLong> count = new ThreadLocal<AtomicLong>();
	public static ThreadLocal<Long> lastTime = new ThreadLocal<Long>();
	private static ThreadLocal<List<XContentBuilder>> esdocList = new ThreadLocal<List<XContentBuilder>>();
	
	private static AppConstants appConstants;
	
	/** 
	 * @Title: main 
	 * @Description: TODO
	 * @param args void  
	 * @throws 
	 * @add on 2016-2-4 上午11:23:10
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		springContextStart();
		
		appConstants = SpringContextHolder.getBean(AppConstants.class);
		
		ConsumerClient client = ConsumerFactory.getInstance().getClient();
		
		client.registerProcessor(ConsumerFactory.getInstance().getConsumer(), Contant.EAGLEYEDUBBOCLIENTTOPIC, new MsgProcesser() {
			
			@Override
			public void process(String message) {
				// TODO Auto-generated method stub
				logger.info("process message with topic ==> [" + Contant.EAGLEYEDUBBOCLIENTTOPIC + "], message ==> [" + message + "]");
				
				processTraceMessage(message);
			}
		}, appConstants.getThreadPerTopicForTrace());
		
		client.registerProcessor(ConsumerFactory.getInstance().getApplogConsumer(), Contant.EAGLEYEAPPLOGCLIENTTOPIC, new MsgProcesser() {
			
			@Override
			public void process(String message) {
				// TODO Auto-generated method stub
				logger.info("process message with topic ==> [" + Contant.EAGLEYEAPPLOGCLIENTTOPIC + "], message ==> [" + message + "]");
				
				processAppLogMessage(message);
			}
		}, appConstants.getThreadPerTopicForAppLog());
	}
	
	/**
	 * 处理tracelog
	 * 
	* @Title: processTraceMessage 
	* @Description: TODO
	* @param message void  
	* @throws 
	* @add on 2016-2-16 上午10:33:11
	 */
	private static void processTraceMessage(String message){
		logger.info(message);
	}
	
	/**
	 * 处理alertlog
	 * 
	* @Title: processAppLogMessage 
	* @Description: TODO
	* @param message void  
	* @throws 
	* @add on 2016-2-16 上午10:33:26
	 */
	private static void processAppLogMessage(String message){
		logger.info(message);
	}
	
	/**
	 * 启动spring
	 * 
	* @Title: springContextStart 
	* @Description: TODO void  
	* @throws 
	* @add on 2016-2-16 下午4:03:41
	 */
	private static void springContextStart() {
        context = new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG.split("[,\\s]+"));
        context.start();
    }
	
	/**
	 * 获取spring bean
	 * 
	* @Title: getBeanInstance 
	* @Description: TODO
	* @param cls
	* @param name
	* @return T  
	* @throws 
	* @add on 2016-2-16 下午4:03:55
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private static <T> T getBeanInstance(Class<T> cls, String name){
		return (T) context.getBean(name);
	}
	
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
		if(count.get() == null){
			count.set(new AtomicLong(0));
		}
		
		if(lastTime.get() == null){
			lastTime.set(System.currentTimeMillis());
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
	
}
