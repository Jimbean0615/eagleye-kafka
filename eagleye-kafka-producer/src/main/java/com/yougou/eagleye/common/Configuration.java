package com.yougou.eagleye.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Configuration {

	private static Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private static final Configuration configuration = new Configuration(); 
	
	private static ResourceBundle rb = null;
	
	//=====kafka producer config======
	private String metadataBrokerList;
	private String serializerClass;
	private String keySerializerClass;
	private String requestTimeoutMs;
	private String messageSendMaxRetries;
	private String requestRequiredAcks;
	private String partitionerClass;
	private String compressionCodec;
	private String retryBackoffMs;
	private String producerType;
	private String queueBufferingMaxMs;
	private String queueBufferingMaxMessages;
	private String queueEnqueueTimeoutMs;
	private String batchNumMessages;
	private String topicMetadataRefreshIntervalMs;
	private String sendBufferBytes;
	
	private Configuration(){
		
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					"/etc/yougouconf/eagleye-client/config.properties"));// 生产环境加载该配置文件
			rb = new PropertyResourceBundle(in);
		} catch (Exception e){
			logger.error("eagleye-dubbo-client loading /etc/yougouconf/eagleye-client/config.properties kafka producer properties error");
			try {
				rb = ResourceBundle.getBundle("config");
			} catch (Exception e2) {
				logger.error("eagleye-dubbo-client loading classpath:config.properties kafka producer properties error");
			}
		}
		
		if(rb!=null){
			
			//kafka
			try {
				this.metadataBrokerList = rb.getString("metadata.broker.list");
				logger.info("eagleye-dubbo-client loading brokers : " + this.metadataBrokerList);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading metadataBrokerList",e);
			}
			
			try {
				this.serializerClass = rb.getString("serializer.class");
				logger.info("eagleye-dubbo-client loading serializerClass : " + this.serializerClass);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading serializerClass",e);
			}
			
			try {
				this.keySerializerClass = rb.getString("key.serializer.class");
				logger.info("eagleye-dubbo-client loading keySerializerClass : " + this.keySerializerClass);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading keySerializerClass",e);
			}
			
			try {
				this.requestTimeoutMs = rb.getString("request.timeout.ms");
				logger.info("eagleye-dubbo-client loading requestTimeoutMs : " + this.requestTimeoutMs);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading requestTimeoutMs",e);
			}
			
			try {
				this.messageSendMaxRetries = rb.getString("message.send.max.retries");
				logger.info("eagleye-dubbo-client loading messageSendMaxRetries : " + this.messageSendMaxRetries);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading messageSendMaxRetries",e);
			}
			
			try {
				this.requestRequiredAcks = rb.getString("request.required.acks");
				logger.info("eagleye-dubbo-client loading requestRequiredAcks : " + this.requestRequiredAcks);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading requestRequiredAcks",e);
			}
			
			try {
				this.partitionerClass = rb.getString("partitioner.class");
				logger.info("eagleye-dubbo-client loading partitionerClass : " + this.partitionerClass);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading partitionerClass",e);
			}
			
			try {
				this.compressionCodec = rb.getString("compression.codec");
				logger.info("eagleye-dubbo-client loading compressionCodec : " + this.compressionCodec);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading compressionCodec",e);
			}
			
			try {
				this.retryBackoffMs = rb.getString("retry.backoff.ms");
				logger.info("eagleye-dubbo-client loading retryBackoffMs : " + this.retryBackoffMs);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading retryBackoffMs",e);
			}
			
			try {
				this.producerType = rb.getString("producer.type");
				logger.info("eagleye-dubbo-client loading producerType : " + this.producerType);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading producerType",e);
			}
			
			try {
				this.queueBufferingMaxMs = rb.getString("queue.buffering.max.ms");
				logger.info("eagleye-dubbo-client loading queueBufferingMaxMs : " + this.queueBufferingMaxMs);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading queueBufferingMaxMs",e);
			}
			
			try {
				this.queueBufferingMaxMessages = rb.getString("queue.buffering.max.messages");
				logger.info("eagleye-dubbo-client loading queueBufferingMaxMessages : " + this.queueBufferingMaxMessages);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading queueBufferingMaxMessages",e);
			}
			
			try {
				this.queueEnqueueTimeoutMs = rb.getString("queue.enqueue.timeout.ms");
				logger.info("eagleye-dubbo-client loading queueEnqueueTimeoutMs : " + this.queueEnqueueTimeoutMs);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading queueEnqueueTimeoutMs",e);
			}
			
			try {
				this.batchNumMessages = rb.getString("batch.num.messages");
				logger.info("eagleye-dubbo-client loading batchNumMessages : " + this.batchNumMessages);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading batchNumMessages",e);
			}
			
			try {
				this.topicMetadataRefreshIntervalMs = rb.getString("topic.metadata.refresh.interval.ms");
				logger.info("eagleye-dubbo-client loading topicMetadataRefreshIntervalMs : " + this.topicMetadataRefreshIntervalMs);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading topicMetadataRefreshIntervalMs",e);
			}
			
			try {
				this.sendBufferBytes = rb.getString("send.buffer.bytes");
				logger.info("eagleye-dubbo-client loading sendBufferBytes : " + this.sendBufferBytes);
			} catch (Exception e) {
				logger.error("eagleye-dubbo-client loading sendBufferBytes",e);
			}
			
		}
		
	}
	
	public static Configuration getInstance(){
		return configuration;
	}

	public String getMetadataBrokerList() {
		return metadataBrokerList;
	}

	public void setMetadataBrokerList(String metadataBrokerList) {
		this.metadataBrokerList = metadataBrokerList;
	}

	public String getSerializerClass() {
		return serializerClass;
	}

	public void setSerializerClass(String serializerClass) {
		this.serializerClass = serializerClass;
	}

	public String getKeySerializerClass() {
		return keySerializerClass;
	}

	public void setKeySerializerClass(String keySerializerClass) {
		this.keySerializerClass = keySerializerClass;
	}

	public String getRequestTimeoutMs() {
		return requestTimeoutMs;
	}

	public void setRequestTimeoutMs(String requestTimeoutMs) {
		this.requestTimeoutMs = requestTimeoutMs;
	}

	public String getMessageSendMaxRetries() {
		return messageSendMaxRetries;
	}

	public void setMessageSendMaxRetries(String messageSendMaxRetries) {
		this.messageSendMaxRetries = messageSendMaxRetries;
	}

	public String getRequestRequiredAcks() {
		return requestRequiredAcks;
	}

	public void setRequestRequiredAcks(String requestRequiredAcks) {
		this.requestRequiredAcks = requestRequiredAcks;
	}

	public String getPartitionerClass() {
		return partitionerClass;
	}

	public void setPartitionerClass(String partitionerClass) {
		this.partitionerClass = partitionerClass;
	}

	public String getCompressionCodec() {
		return compressionCodec;
	}

	public void setCompressionCodec(String compressionCodec) {
		this.compressionCodec = compressionCodec;
	}

	public String getRetryBackoffMs() {
		return retryBackoffMs;
	}

	public void setRetryBackoffMs(String retryBackoffMs) {
		this.retryBackoffMs = retryBackoffMs;
	}

	public String getProducerType() {
		return producerType;
	}

	public void setProducerType(String producerType) {
		this.producerType = producerType;
	}

	public String getQueueBufferingMaxMs() {
		return queueBufferingMaxMs;
	}

	public void setQueueBufferingMaxMs(String queueBufferingMaxMs) {
		this.queueBufferingMaxMs = queueBufferingMaxMs;
	}

	public String getQueueBufferingMaxMessages() {
		return queueBufferingMaxMessages;
	}

	public void setQueueBufferingMaxMessages(String queueBufferingMaxMessages) {
		this.queueBufferingMaxMessages = queueBufferingMaxMessages;
	}

	public String getQueueEnqueueTimeoutMs() {
		return queueEnqueueTimeoutMs;
	}

	public void setQueueEnqueueTimeoutMs(String queueEnqueueTimeoutMs) {
		this.queueEnqueueTimeoutMs = queueEnqueueTimeoutMs;
	}

	public String getBatchNumMessages() {
		return batchNumMessages;
	}

	public void setBatchNumMessages(String batchNumMessages) {
		this.batchNumMessages = batchNumMessages;
	}

	public String getTopicMetadataRefreshIntervalMs() {
		return topicMetadataRefreshIntervalMs;
	}

	public void setTopicMetadataRefreshIntervalMs(
			String topicMetadataRefreshIntervalMs) {
		this.topicMetadataRefreshIntervalMs = topicMetadataRefreshIntervalMs;
	}

	public String getSendBufferBytes() {
		return sendBufferBytes;
	}

	public void setSendBufferBytes(String sendBufferBytes) {
		this.sendBufferBytes = sendBufferBytes;
	}
	
}
