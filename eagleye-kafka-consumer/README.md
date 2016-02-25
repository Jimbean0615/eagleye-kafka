# eagleye-kafka-consumer

## 1.用途
eagleye-kafka-consumer用户从接收kafka日志消息

## 2.使用方法
	
### 2.1 打包
	
使用maven package打包，将打包后的tar.gz文件上传服务器，解压
	
### 2.2 启停
	
> #### 启动应用程序 
> bin/start.sh
> #### 停止应用程序
> bin/stop.sh
> 或者采用kill pid方式等待程序内线程执行完毕自动停止，避免使用kill -9强制关闭

## 3.配置
	
### 编辑项目中config.properties，配置如下：
	
```
####elasticsearch
eagleye.admin.es.address=192.168.211.250:9300
eagleye.admin.es.clustername=eagleye_elasticsearch


####trace redis
eagleye.admin.trace.redis.pool.maxActive=300
eagleye.admin.trace.redis.pool.maxIdle=50
eagleye.admin.trace.redis.pool.maxWait=1000
eagleye.admin.trace.redis.pool.testOnBorrow=false
eagleye.admin.trace.redis.ip=192.168.211.247
eagleye.admin.trace.redis.port=6379


####alert redis
eagleye.admin.alert.redis.pool.maxActive=300
eagleye.admin.alert.redis.pool.maxIdle=50
eagleye.admin.alert.redis.pool.maxWait=1000
eagleye.admin.alert.redis.pool.testOnBorrow=false
eagleye.admin.alert.redis.ip=192.168.211.247
eagleye.admin.alert.redis.port=6379



####send email and sms interface
eagleye.admin.sendEmailUrl=http://127.0.0.1:8849/messenger/sendEmailForSystem.do
eagleye.admin.sendSmsUrl=http://127.0.0.1:8849/messenger/sendSms.do


####alert frequence default 10 min
eagleye.admin.alert.frequence=1200

####open or close alert
eagleye.admin.alert.status=false

####trace alert frequence default 600 second
eagleye.admin.trace.alert.frequence=600

####open or close trace alert
eagleye.admin.trace.alert.status=false

####open or close storage
eagleye.admin.storageStatus=true



####kafka consumer
zookeeper.connect=192.168.211.250:2181
group.id=eagleye-client-group
client.id=
zookeeper.session.timeout.ms=5000
zookeeper.connection.timeout.ms=1000
zookeeper.sync.time.ms=200
auto.commit.enable=true
auto.commit.interval.ms=5000
auto.offset.reset=largest
fetch.message.max.bytes=1000000
queued.max.message.chunks=100

####consumer threads
threadPerTopicForTrace=4
threadPerTopicForAppLog=4
```


## 4.部分kafka工具介绍

### 创建topic
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 8 --topic eagleyetracechannel

### 查看已经创建的topic列表  
> bin/kafka-topics.sh --list --zookeeper localhost:2181 

### 查看此topic的属性  
> bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic eagleyetracechannel 

### 查看 offset
> bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper localhost:2181 --group eagleye-client-group

### 生产者连接Kafka Broker发布一个消息  
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic eagleyetracechannel 

### 消费者连接Zookeeper获取消息
> bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic eagleyetracechannel --from-beginning 



## 5.其他

### 安装zookeeper
> wget http://mirrors.hust.edu.cn/apache/zookeeper/zookeeper-3.4.6/zookeeper-3.4.6.tar.gz  
> gzip -d zookeeper-3.4.6.tar.gz  
> tar -xvf zookeeper-3.4.6.tar  

### 安装kafka
> wget http://apache.fayea.com/apache-mirror/kafka/0.8.1.1/kafka_2.8.0-0.8.1.1.tgz  
> gtar xvzf kafka_2.8.0-0.8.1.1.tgz 

### 搭建kafka集群
>
>




