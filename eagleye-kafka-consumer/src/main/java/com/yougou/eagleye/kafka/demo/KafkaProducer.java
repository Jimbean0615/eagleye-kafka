package com.yougou.eagleye.kafka.demo;

import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-4 上午9:46:51
 */
public class KafkaProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	
	private final Producer<String, String> producer;
	public final static String TOPIC = "eagleyealertchannel";

	private KafkaProducer() {
		Properties props = new Properties();
		//此处配置的是kafka的端口
		props.put("metadata.broker.list", "10.10.10.174:9092,10.10.10.176:9092");
		
		//配置value的序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		//配置key的序列化类
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");

		//请求超时时间，默认值10000
		props.put("request.timeout.ms", "3000");
		
		//如果producer发送消息失败了会自动重发，本选项指定了重发的次数
		props.put("message.send.max.retries", "3");
		
		//0意味着不需要等待broker的ack，默认值
		//1意味着leader replica已经接收到数据后，producer会得到一个ack
		//-1意味着在所有的ISR都接收到数据后，producer才得到一个ack
		props.put("request.required.acks", "0");

		//用来把消息分到各个partition中，默认行为是对key进行hash
		props.put("partitioner.class", "kafka.producer.DefaultPartitioner");

		//默认0表示不压缩，1表示用gzip压缩，2表示用snappy压缩。
		//压缩后消息中会有头来指明消息压缩类型，故在消费者端消息解压是透明的无需指定
		props.put("compression.codec", "1");
		
		//允许你指定特定的topic对其进行压缩
		//如果compression codec设置了除NoCompressionCodec以外的值，那么仅会对本选项指定的topic进行压缩
		//如果compression codec为NoCompressionCodec，那么压缩就不会启用
		props.put("compressed.topics", TOPIC);
		
		//在每次重发之前，producer会刷新相关的topic的元数据，来看看是否选出了一个新leader
		//由于选举leader会花一些时间，此选项指定了在刷新元数据前等待的时间
		props.put("retry.backoff.ms", "100");
		
		//默认sync表示同步，async表示异步。异步可以提高发送吞吐量，但是也可能导致丢失未发送过去的消息
		props.put("producer.type", "async");
		//默认5000，表示每隔多久处理。此值会提高吞吐量，但是会增加消息的到达延时
		props.put("queue.buffering.max.ms", "1000");
		//允许buffer的最大消息数量，默认值10000
		props.put("queue.buffering.max.messages", "5000");

		//默认值-1阻塞，设置为0表示抛弃
		//当消息在producer端沉积的条数达到 queue.buffering.max.meesages 时，
		//阻塞一定时间后，队列仍然没有enqueue，此时producer可以继续阻塞或者将消息抛弃
		props.put("queue.enqueue.timeout.ms", "0");

		//在异步模式下，一个batch发送的消息数量。
		//producer会等待直到要发送的消息数量达到这个值，之后才会发送。
		//但如果消息数量不够，达到queue.buffer.max.ms时也会直接发送
		props.put("batch.num.messages", "200");
		
		//当出现错误时(缺失partition，leader不可用等)，producer通常会从broker拉取最新的topic的元数据
		//它也会每隔一段时间轮询(默认是每隔10分钟)。如果设置了一个负数，那么只有当发生错误时才会刷新元数据
		props.put("topic.metadata.refresh.interval.ms", "600000");
		
		//socket的发送缓存大小
		props.put("send.buffer.bytes", "100000");
		
		producer = new Producer<String, String>(new ProducerConfig(props));
	}
	
	public void produce() {
		//String key = String.valueOf(System.currentTimeMillis() + new Random().nextInt(255));
		String key = "192.168.2." + new Random().nextInt(255);   
		String data = "hello kafka message " + key;
		data = "#EAGLEYE#yougou-order-center#yougou-order-center167#1455598200073#订单[orderSubNo=A560216100002_1]为锁定状态,不能进行取消订单操作[错误]。";
		producer.send(new KeyedMessage<String, String>(TOPIC, key, data));
		logger.info(data);
		System.out.println("producer==>>" + key + "=" + data);
		producer.close();
	}

	public static void main(String[] args) {
		new KafkaProducer().produce();
	}
}