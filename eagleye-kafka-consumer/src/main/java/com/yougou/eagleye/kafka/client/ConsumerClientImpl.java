package com.yougou.eagleye.kafka.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.eagleye.Main;
import com.yougou.eagleye.kafka.factory.ConsumerFactory;
import com.yougou.eagleye.kafka.process.ConsumerTask;
import com.yougou.eagleye.kafka.process.MsgProcesser;

/**
 * Class description goes here.
 * 
 * @version
 * @author zhang.jb on 2016-2-4 上午10:27:46
 */
public class ConsumerClientImpl implements ConsumerClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerFactory.class);
	
//	private ConsumerConnector consumer = null;
	private ExecutorService executor = null;
	
	protected ConsumerClientImpl() {
//		this.consumer = ConsumerFactory.getInstance().getConsumer();
	}
	
	public static ConsumerClient getConsumerInstance(){
		return new ConsumerClientImpl();
	}
	
	@Override
	public void registerProcessor(ConsumerConnector consumer, String topic, MsgProcesser msgProcessor, int threadPerTopic) {
		// TODO Auto-generated method stub
		if (msgProcessor == null) {
			logger.error("Failed to register message processor, because processor is null.");
			return;
		}
		
		consume(consumer, topic, msgProcessor, threadPerTopic);
	}
	
	private void consume(ConsumerConnector consumer, String topic, MsgProcesser msgProcessor, int threadPerTopic) {
		//hook
		addShutdownHook(consumer, topic);
        
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //threadPerTopic为每个topic启动的consumer线程数
        //总consumer线程数=threadPerTopic*nodeCount（集群中节点个数）
        //高负载情况下优化方案为总consumer线程数=partition个数
        topicCountMap.put(topic, threadPerTopic);
        
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
        
        Map<String, List<KafkaStream<String, String>>> consumerMap = 
        		consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        
        List<KafkaStream<String, String>> streams = consumerMap.get(topic);
        
        //启动多线程
        executor = Executors.newFixedThreadPool(threadPerTopic);
        
        int threadNumber = 0;
        for (KafkaStream<String, String> stream : streams) {
        	threadNumber++;
            executor.submit(new ConsumerTask(stream, threadNumber, msgProcessor));
        }
        
        logger.info("kafka consumer started with topic ==> " + topic);
    }
    
    private void addShutdownHook(final ConsumerConnector consumer, final String topic){
    	 Runtime.getRuntime().addShutdownHook(new Thread() {
             public void run() {
                 try {
                     if (consumer != null) {
                         consumer.shutdown();
                     }
                     if (executor != null) {
                         executor.shutdown();
                     }
                     if (Main.context != null) {
                    	 Main.context.stop();
                    	 Main.context.close();
                    	 Main.context = null;
                     }
                 } catch (Throwable t) {
                     logger.error(t.getMessage(), t);
                 } finally {
                	 logger.info("kafka consumer stopped in hook, topic is " + topic);
                 }
             }
         });
    }

}
