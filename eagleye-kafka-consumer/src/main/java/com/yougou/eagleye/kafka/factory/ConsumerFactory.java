package com.yougou.eagleye.kafka.factory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.eagleye.elasticsearch.constants.AppConstants;
import com.yougou.eagleye.holder.SpringContextHolder;
import com.yougou.eagleye.kafka.client.ConsumerClient;
import com.yougou.eagleye.kafka.client.ConsumerClientImpl;

/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-4 上午9:46:51
 */
public final class ConsumerFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerFactory.class);
	
    private ConsumerConnector consumer;
    private ConsumerConnector applogConsumer;
    private ConsumerClient client;
    
	private ConsumerFactory() {
		logger.debug("ConsumerFactory Init Success");
    }
	
	/*
	 * 单例模式
	 */
	private static class ConsumerFactoryHolder{
        /*
         * 静态初始化器，由JVM来保证线程安全
         */
        private static ConsumerFactory instance = new ConsumerFactory();
        
        static {
            Properties props = new Properties();
            
            AppConstants appConstants = SpringContextHolder.getBean(AppConstants.class);
            
            //zookeeper 配置
            props.put("zookeeper.connect", appConstants.getZookeeperConnect());
     
            //group 代表一个消费组
            props.put("group.id", appConstants.getGroupId());
            
            //用户可自定义的client id，附加在每一条消息上来帮助跟踪
            //默认值同group_id
          	try {
				props.put("client.id", InetAddress.getLocalHost().getHostAddress().toString());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				props.put("client.id", appConstants.getClientId());
			}
          		
            //zk连接
            props.put("zookeeper.session.timeout.ms", appConstants.getZookeeperSessionTimeoutMs());
            props.put("zookeeper.connection.timeout.ms", appConstants.getZookeeperConnectionTimeoutMs());
            props.put("zookeeper.sync.time.ms", appConstants.getZookeeperSyncTimeMs());
            
            //是否自动提交：这里提交意味着客户端会自动定时更新offset到zookeeper
            //默认为true
            props.put("auto.commit.enable", appConstants.getAutoCommitEnable());
            //auto.commit.interval.ms:自动更新时间。默认60 * 1000
            props.put("auto.commit.interval.ms", appConstants.getAutoCommitIntervalMs());
            //largest表示接收最大的offset(即最新消息)
            //smallest表示最小offset,即从topic的开始位置消费所有消息.
            props.put("auto.offset.reset", appConstants.getAutoOffsetReset());
            
            //每次取的块的大小（默认1024*1024），多个消息通过块来批量发送给消费者，
            //指定块大小可以指定有多少消息可以一次取出。注意若一个消息就超过了该块指定的大小，它将拿不到
            props.put("fetch.message.max.bytes", appConstants.getFetchMessageMaxBytes());
            //最大取多少块缓存到消费者(默认10)
            props.put("queued.max.message.chunks", appConstants.getQueuedMaxMessageChunks());
            
            ConsumerConfig config = new ConsumerConfig(props);
            
            instance.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
            instance.applogConsumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
            instance.client = ConsumerClientImpl.getConsumerInstance();
            logger.debug("Consumer&Client Init Success");
        }
	}
	
	public static ConsumerFactory getInstance() {
		return ConsumerFactoryHolder.instance;
	}
	
	public ConsumerClient getClient() {
		return ConsumerFactoryHolder.instance.client;
	}
	
	public ConsumerConnector getConsumer() {
		return ConsumerFactoryHolder.instance.consumer;
	}

	public ConsumerConnector getApplogConsumer() {
		return ConsumerFactoryHolder.instance.applogConsumer;
	}
	
}
