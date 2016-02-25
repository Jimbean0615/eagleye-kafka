package com.yougou.eagleye.kafka.demo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.eagleye.kafka.process.ConsumerTask;
 
/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-4 上午9:46:51
 */
public class KafkaConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
    private final ConsumerConnector consumer;
    private ExecutorService executor;
    
    private KafkaConsumer() {
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "10.10.10.174:2181");
 
        //group 代表一个消费组
        props.put("group.id", "eagleye-dubbo-client-group");
        
        //用户可自定义的client id，附加在每一条消息上来帮助跟踪
        //默认值同group_id
      	props.put("client.id", "");
      		
        //zk连接
        props.put("zookeeper.session.timeout.ms", "5000");
        props.put("zookeeper.connection.timeout.ms", "1000");
        props.put("zookeeper.sync.time.ms", "200");
        
        //是否自动提交：这里提交意味着客户端会自动定时更新offset到zookeeper
        //默认为true
        props.put("auto.commit.enable", "true");
        //auto.commit.interval.ms:自动更新时间。默认60 * 1000
        props.put("auto.commit.interval.ms", "5000");
        //largest表示接收最大的offset(即最新消息)
        //smallest表示最小offset,即从topic的开始位置消费所有消息.
        props.put("auto.offset.reset", "largest");
        
        //每次取的块的大小（默认1024*1024），多个消息通过块来批量发送给消费者，
        //指定块大小可以指定有多少消息可以一次取出。注意若一个消息就超过了该块指定的大小，它将拿不到
        props.put("fetch.message.max.bytes", "1000000");
        //最大取多少块缓存到消费者(默认10)
        props.put("queued.max.message.chunks", "100");
        
        ConsumerConfig config = new ConsumerConfig(props);
        
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }
 
    public void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //每个topic启动的线程数
        Integer threadPerTopic = 4;
        topicCountMap.put(KafkaProducer.TOPIC, threadPerTopic);
 
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
        
        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        
        List<KafkaStream<String, String>> streams = consumerMap.get(KafkaProducer.TOPIC);
        
        //启动多线程
        executor = Executors.newFixedThreadPool(threadPerTopic);
        
        int threadNumber = 0;
        for (KafkaStream<String, String> stream : streams) {
        	threadNumber++;
            executor.submit(new ConsumerTask(stream, threadNumber, null));
        }
        
        //hock
        shutdown();
        
        logger.info("kafka consumer started!");
    }
    
    private void shutdown(){
    	 Runtime.getRuntime().addShutdownHook(new Thread() {
             public void run() {
                 try {
                     if (consumer != null) {
                         consumer.shutdown();
                     }
                     if (executor != null) {
                         executor.shutdown();
                     }
                     logger.info("kafka consumer stopped!");
                 } catch (Throwable t) {
                     logger.error(t.getMessage(), t);
                 }
             }
         });
    }
    
    public static void main(String[] args) {
        new KafkaConsumer().consume();
    }
}