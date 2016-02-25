package com.yougou.eagleye.elasticsearch.constants;





public class AppConstants {

	
	public AppConstants(){
		
	}
	
	/**
	 * 用户session的属性名称
	 */
	public static final String EAGLEYE_SESSION_USERNAME = "EAGLEYE_SESSION_USERNAME";
	
	/**
	 * 记录日志信息的threadLocal
	 */
	public static final ThreadLocal<String> syslogContent = new ThreadLocal<String>();
	
	
	
	
	/**
	 * 用户session的属性名称
	 */
	public static final String EAGLEYE_ADMIN_SESSION = "EAGLEYE_ADMIN_SESSION";
	
	/**
	 * 数据状态 删除
	 */
	public static final String DATASTATUS_DEL = "0";
	/**
	 * 数据状态 有效
	 */
	public static final String DATASTATUS_VALID = "1";
	/**
	 * 数据状态 无效
	 */
	public static final String DATASTATUS_INVALID = "2";
	
	
	/****************************flash****************************************/
	/**
	 * 数据重复
	 */
	public static final String FLASH_REPEAT = "REPEAT";
	
	/**
	 * 异常
	 */
	public static final String FLASH_ERROR = "ERROR";
	
	/**
	 * 成功
	 */
	public static final String FLASH_SUCCESS = "SUCCESS";
	
	
	
	/************************************预警类型*********************************************/
	
	/**
	 * 不发送
	 */
	public static final String ALERT_TYPE_NOSENT = "n";
	
	/**
	 * 只发送手机
	 */
	public static final String ALERT_TYPE_MOBILE = "m";
	
	/**
	 * 只发送邮件
	 */
	public static final String ALERT_TYPE_EMAIL = "e";
	
	/**
	 * 手机邮箱都发送
	 */
	public static final String ALERT_TYPE_ALLSENT = "me";
	
	
	/**
	 * 存储在缓存中的rulejson 的key前缀
	 */
	public static final String KEY_PREFIX = "eagleye_";
	
	
	
	
	/*********************************************send email and sms interface**************************************/
	
	/**
	 * 发送邮件地址
	 */
	private String sendEmailUrl;
	
	/**
	 * 发送短信接口地址
	 */
	private String sendSmsUrl;
	
	/**
	 * 预警时间间隔
	 */
	private long alertFrequence;
	
	/**
	 * 预警状态, 是否开启预警
	 */
	private boolean alertStatus;
	
	
	/**
	 * 是否开启跟踪预警
	 */
	private boolean traceAlertStatus;
	
	/**
	 * 是否开启存储
	 */
	private boolean storageStatus = true;
	
	

	public boolean isStorageStatus() {
		return storageStatus;
	}

	public void setStorageStatus(boolean storageStatus) {
		this.storageStatus = storageStatus;
	}

	public boolean getAlertStatus() {
		return alertStatus;
	}

	public void setAlertStatus(boolean alertStatus) {
		this.alertStatus = alertStatus;
	}

	public long getAlertFrequence() {
		return alertFrequence;
	}

	public void setAlertFrequence(long alertFrequence) {
		this.alertFrequence = alertFrequence;
	}

	public String getSendEmailUrl() {
		return sendEmailUrl;
	}

	public void setSendEmailUrl(String sendEmailUrl) {
		this.sendEmailUrl = sendEmailUrl;
	}

	public String getSendSmsUrl() {
		return sendSmsUrl;
	}

	public void setSendSmsUrl(String sendSmsUrl) {
		this.sendSmsUrl = sendSmsUrl;
	}
	
	

	public boolean isTraceAlertStatus() {
		return traceAlertStatus;
	}
	
	public void setTraceAlertStatus(boolean traceAlertStatus) {
		this.traceAlertStatus = traceAlertStatus;
	}
	
	
	/*************************************************consume trace log queue flush size*******************************************************/
	
	

	

	/**
	 * 缓冲队列最大容量
	 */
	private long flushSize;


	public long getFlushSize() {
		return flushSize;
	}

	public void setFlushSize(long flushSize) {
		this.flushSize = flushSize;
	}
	
	
	/**
	 * 调用时消耗的时间预警阈值
	 */
	public static final Integer SPEND_TIME_THRESHOLD = 30000;
	
	/**
	 * 调用方类型, 消费方
	 */
	public static final String DUBBO_INVOKE_TYPE_CONSUMER = "consumer";
	
	/**
	 * 调用方类型, 提供方
	 */
	public static final String DUBBO_INVOKE_TYPE_PROVIDER = "provider";
	
	/**
	 * 存储所有服务名的redis set key
	 */
	public static final String EAGLEYE_SERVICE_REDIS_KEY = "eagleyedubboservice";
	
	
	
	public static final String TRACE_LOG_PREFIX = "tracelogs_";
	
	public static final String TRACE_LOG_TYPE = "log";
	
	
	/*********************************************kafka consumer config**************************************/
	private String zookeeperConnect;
	private String groupId;
	private String clientId;
	private String zookeeperSessionTimeoutMs;
	private String zookeeperConnectionTimeoutMs;
	private String zookeeperSyncTimeMs;
	private String autoCommitEnable;
	private String autoCommitIntervalMs;
	private String autoOffsetReset;
	private String fetchMessageMaxBytes;
	private String queuedMaxMessageChunks;
	
	private Integer threadPerTopicForTrace;
	private Integer threadPerTopicForAppLog;
	
	public String getZookeeperConnect() {
		return zookeeperConnect;
	}

	public void setZookeeperConnect(String zookeeperConnect) {
		this.zookeeperConnect = zookeeperConnect;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getZookeeperSessionTimeoutMs() {
		return zookeeperSessionTimeoutMs;
	}

	public void setZookeeperSessionTimeoutMs(String zookeeperSessionTimeoutMs) {
		this.zookeeperSessionTimeoutMs = zookeeperSessionTimeoutMs;
	}

	public String getZookeeperConnectionTimeoutMs() {
		return zookeeperConnectionTimeoutMs;
	}

	public void setZookeeperConnectionTimeoutMs(String zookeeperConnectionTimeoutMs) {
		this.zookeeperConnectionTimeoutMs = zookeeperConnectionTimeoutMs;
	}

	public String getZookeeperSyncTimeMs() {
		return zookeeperSyncTimeMs;
	}

	public void setZookeeperSyncTimeMs(String zookeeperSyncTimeMs) {
		this.zookeeperSyncTimeMs = zookeeperSyncTimeMs;
	}

	public String getAutoCommitEnable() {
		return autoCommitEnable;
	}

	public void setAutoCommitEnable(String autoCommitEnable) {
		this.autoCommitEnable = autoCommitEnable;
	}

	public String getAutoCommitIntervalMs() {
		return autoCommitIntervalMs;
	}

	public void setAutoCommitIntervalMs(String autoCommitIntervalMs) {
		this.autoCommitIntervalMs = autoCommitIntervalMs;
	}

	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	public void setAutoOffsetReset(String autoOffsetReset) {
		this.autoOffsetReset = autoOffsetReset;
	}

	public String getFetchMessageMaxBytes() {
		return fetchMessageMaxBytes;
	}

	public void setFetchMessageMaxBytes(String fetchMessageMaxBytes) {
		this.fetchMessageMaxBytes = fetchMessageMaxBytes;
	}

	public String getQueuedMaxMessageChunks() {
		return queuedMaxMessageChunks;
	}

	public void setQueuedMaxMessageChunks(String queuedMaxMessageChunks) {
		this.queuedMaxMessageChunks = queuedMaxMessageChunks;
	}

	public Integer getThreadPerTopicForTrace() {
		return threadPerTopicForTrace;
	}

	public void setThreadPerTopicForTrace(Integer threadPerTopicForTrace) {
		this.threadPerTopicForTrace = threadPerTopicForTrace;
	}

	public Integer getThreadPerTopicForAppLog() {
		return threadPerTopicForAppLog;
	}

	public void setThreadPerTopicForAppLog(Integer threadPerTopicForAppLog) {
		this.threadPerTopicForAppLog = threadPerTopicForAppLog;
	}
	
}
