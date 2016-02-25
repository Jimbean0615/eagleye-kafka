package com.yougou.eagleye.kafka.client;

import kafka.javaapi.consumer.ConsumerConnector;

import com.yougou.eagleye.kafka.process.MsgProcesser;

/**
 * Class description goes here.
 * 
 * @version
 * @author zhang.jb on 2016-2-4 上午10:27:46
 */
public interface ConsumerClient {

  public void registerProcessor(ConsumerConnector consumer, String topic, MsgProcesser msgProcessor, int threadPerTopic);
 
}