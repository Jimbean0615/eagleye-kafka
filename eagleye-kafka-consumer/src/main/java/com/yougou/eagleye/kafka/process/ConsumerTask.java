package com.yougou.eagleye.kafka.process;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.eagleye.kafka.demo.KafkaConsumer;
 
/**
 * Class description goes here.
 *
 * @version  
 * @author  zhang.jb on 2016-2-4 上午9:46:51
 */
public class ConsumerTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
    private KafkaStream<String, String> stream;
    private int threadNumber;
    private MsgProcesser msgProcesser;
 
    public ConsumerTask(KafkaStream<String, String> stream, int threadNumber,
    		MsgProcesser msgProcesser) {
    	this.stream = stream;
    	this.threadNumber = threadNumber;
    	this.msgProcesser =  msgProcesser;
    }
 
    public void run() {
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext()) {
        	if(msgProcesser  == null){
        		logger.debug("Thread " + threadNumber + ": " + it.next().message());
        	}else{
        		msgProcesser.process(it.next().message());
        	}
        }
    }
}