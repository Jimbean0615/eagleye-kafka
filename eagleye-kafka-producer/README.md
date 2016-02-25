# eagleye-kafka-producer

## 接入方式

```
<dependency>
	<groupId>com.yougou</groupId>
	<artifactId>eagleye-kafka-producer</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

## 使用方法

```
try {
	Producer<String, String> producer = ProducerFactory.getProducer();
	producer.send(new KeyedMessage<String, String>(topic, key, message));
} catch (Exception e) {
    logger.error(" send log to kafka error :[" + e.getMessage() +"]");
}
```
