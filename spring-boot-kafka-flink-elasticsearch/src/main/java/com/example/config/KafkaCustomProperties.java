package com.example.config;

import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.databind.ser.std.*;
import org.apache.kafka.clients.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.kafka.*;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.convert.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.util.unit.*;

import java.io.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Configuration
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaCustomProperties {
	
	/**
	 * Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster. Applies to all components unless overridden.
	 */
	private List<String> bootstrapServers = new ArrayList<>(Collections.singletonList("localhost:9092"));
	
	/**
	 * ID to pass to the server when making requests. Used for server-side logging.
	 */
	private String clientId;
	
	
	/**
	 * 配置zk集群
	 */
	private List<String> zookeeperConnects = new ArrayList<>(Collections.singletonList("localhost:2181"));
	/**
	 * 配置需要写入的topic
	 */
	private String topic;


	/**
	 * 匹配输入es的个数
	 */
	private int elasticsearchIndexBulk = 100;
	
	
	/**
	 * Additional properties, common to producers and consumers, used to configure the
	 * client.
	 */
	private final Map<String, String> properties = new HashMap<>();
	
	private final KafkaCustomProperties.Consumer consumer = new KafkaCustomProperties.Consumer();
	
	private final KafkaCustomProperties.Producer producer = new KafkaCustomProperties.Producer();
	
	private final KafkaCustomProperties.Admin admin = new KafkaCustomProperties.Admin();
	
	private final KafkaCustomProperties.Streams streams = new KafkaCustomProperties.Streams();
	
	private final KafkaCustomProperties.Listener listener = new KafkaCustomProperties.Listener();
	
	private final KafkaCustomProperties.Ssl ssl = new KafkaCustomProperties.Ssl();
	
	private final KafkaCustomProperties.Jaas jaas = new KafkaCustomProperties.Jaas();
	
	private final KafkaCustomProperties.Template template = new KafkaCustomProperties.Template();
	
	private final KafkaCustomProperties.Security security = new KafkaCustomProperties.Security();
	
	public int getElasticsearchIndexBulk() {
		return elasticsearchIndexBulk;
	}
	
	public void setElasticsearchIndexBulk(final int elasticsearchIndexBulk) {
		this.elasticsearchIndexBulk = elasticsearchIndexBulk;
	}
	

	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(final String topic) {
		this.topic = topic;
	}
	
	public List<String> getZookeeperConnects() {
		return zookeeperConnects;
	}
	
	public void setZookeeperConnects(final List<String> zookeeperConnects) {
		this.zookeeperConnects = zookeeperConnects;
	}
	
	public List<String> getBootstrapServers() {
		return this.bootstrapServers;
	}
	
	public void setBootstrapServers(List<String> bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public Map<String, String> getProperties() {
		return this.properties;
	}
	
	public KafkaCustomProperties.Consumer getConsumer() {
		return this.consumer;
	}
	
	public KafkaCustomProperties.Producer getProducer() {
		return this.producer;
	}
	
	public KafkaCustomProperties.Listener getListener() {
		return this.listener;
	}
	
	public KafkaCustomProperties.Admin getAdmin() {
		return this.admin;
	}
	
	public KafkaCustomProperties.Streams getStreams() {
		return this.streams;
	}
	
	public KafkaCustomProperties.Ssl getSsl() {
		return this.ssl;
	}
	
	public KafkaCustomProperties.Jaas getJaas() {
		return this.jaas;
	}
	
	public KafkaCustomProperties.Template getTemplate() {
		return this.template;
	}
	
	public KafkaCustomProperties.Security getSecurity() {
		return this.security;
	}
	
	private Map<String, Object> buildCommonProperties() {
		Map<String, Object> properties = new HashMap<>();
		if (this.bootstrapServers != null) {
			properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		}
		if (this.clientId != null) {
			properties.put(CommonClientConfigs.CLIENT_ID_CONFIG, this.clientId);
		}
		properties.putAll(this.ssl.buildProperties());
		properties.putAll(this.security.buildProperties());
		if (!CollectionUtils.isEmpty(this.properties)) {
			properties.putAll(this.properties);
		}
		return properties;
	}
	
	/**
	 * Create an initial map of consumer properties from the state of this instance.
	 * <p>
	 * This allows you to add additional properties, if necessary, and override the
	 * default kafkaConsumerFactory bean.
	 *
	 * @return the consumer properties initialized with the customizations defined on this
	 * 		instance
	 */
	public Map<String, Object> buildConsumerProperties() {
		Map<String, Object> properties = buildCommonProperties();
		properties.putAll(this.consumer.buildProperties());
		return properties;
	}
	
	/**
	 * Create an initial map of producer properties from the state of this instance.
	 * <p>
	 * This allows you to add additional properties, if necessary, and override the
	 * default kafkaProducerFactory bean.
	 *
	 * @return the producer properties initialized with the customizations defined on this
	 * 		instance
	 */
	public Map<String, Object> buildProducerProperties() {
		Map<String, Object> properties = buildCommonProperties();
		properties.putAll(this.producer.buildProperties());
		return properties;
	}
	
	/**
	 * Create an initial map of admin properties from the state of this instance.
	 * <p>
	 * This allows you to add additional properties, if necessary, and override the
	 * default kafkaAdmin bean.
	 *
	 * @return the admin properties initialized with the customizations defined on this
	 * 		instance
	 */
	public Map<String, Object> buildAdminProperties() {
		Map<String, Object> properties = buildCommonProperties();
		properties.putAll(this.admin.buildProperties());
		return properties;
	}
	
	/**
	 * Create an initial map of streams properties from the state of this instance.
	 * <p>
	 * This allows you to add additional properties, if necessary.
	 *
	 * @return the streams properties initialized with the customizations defined on this
	 * 		instance
	 */
	public Map<String, Object> buildStreamsProperties() {
		Map<String, Object> properties = buildCommonProperties();
		properties.putAll(this.streams.buildProperties());
		return properties;
	}
	
	public static class Consumer {
		
		private final KafkaCustomProperties.Ssl ssl = new KafkaCustomProperties.Ssl();
		
		private final KafkaCustomProperties.Security security = new KafkaCustomProperties.Security();
		
		/**
		 * Frequency with which the consumer offsets are auto-committed to Kafka if
		 * 'enable.auto.commit' is set to true.
		 */
		private Duration autoCommitInterval;
		
		/**
		 * What to do when there is no initial offset in Kafka or if the current offset no
		 * longer exists on the server.
		 */
		private String autoOffsetReset;
		
		/**
		 * Comma-delimited list of host:port pairs to use for establishing the initial
		 * connections to the Kafka cluster. Overrides the global property, for consumers.
		 */
		private List<String> bootstrapServers;
		
		/**
		 * ID to pass to the server when making requests. Used for server-side logging.
		 */
		private String clientId;
		
		/**
		 * Whether the consumer's offset is periodically committed in the background.
		 */
		private Boolean enableAutoCommit;
		
		/**
		 * Maximum amount of time the server blocks before answering the fetch request if
		 * there isn't sufficient data to immediately satisfy the requirement given by
		 * "fetch-min-size".
		 */
		private Duration fetchMaxWait;
		
		/**
		 * Minimum amount of data the server should return for CustConsumerConfig fetch request.
		 */
		private DataSize fetchMinSize;
		
		/**
		 * Unique string that identifies the consumer group to which this consumer
		 * belongs.
		 */
		private String groupId;
		
		/**
		 * Expected time between heartbeats to the consumer coordinator.
		 */
		private Duration heartbeatInterval;
		
		/**
		 * Isolation level for reading messages that have been written transactionally.
		 */
		private KafkaCustomProperties.IsolationLevel isolationLevel = KafkaCustomProperties.IsolationLevel.READ_UNCOMMITTED;
		
		/**
		 * Deserializer class for keys.
		 */
		private Class<?> keyDeserializer = StringDeserializer.class;
		
		/**
		 * Deserializer class for values.
		 */
		private Class<?> valueDeserializer = StringDeserializer.class;
		
		/**
		 * Maximum number of records returned in CustConsumerConfig single call to poll().
		 */
		private Integer maxPollRecords;
		
		/**
		 * Additional consumer-specific properties used to configure the client.
		 */
		private final Map<String, String> properties = new HashMap<>();
		
		public KafkaCustomProperties.Ssl getSsl() {
			return this.ssl;
		}
		
		public KafkaCustomProperties.Security getSecurity() {
			return this.security;
		}
		
		public Duration getAutoCommitInterval() {
			return this.autoCommitInterval;
		}
		
		public void setAutoCommitInterval(Duration autoCommitInterval) {
			this.autoCommitInterval = autoCommitInterval;
		}
		
		public String getAutoOffsetReset() {
			return this.autoOffsetReset;
		}
		
		public void setAutoOffsetReset(String autoOffsetReset) {
			this.autoOffsetReset = autoOffsetReset;
		}
		
		public List<String> getBootstrapServers() {
			return this.bootstrapServers;
		}
		
		public void setBootstrapServers(List<String> bootstrapServers) {
			this.bootstrapServers = bootstrapServers;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		public Boolean getEnableAutoCommit() {
			return this.enableAutoCommit;
		}
		
		public void setEnableAutoCommit(Boolean enableAutoCommit) {
			this.enableAutoCommit = enableAutoCommit;
		}
		
		public Duration getFetchMaxWait() {
			return this.fetchMaxWait;
		}
		
		public void setFetchMaxWait(Duration fetchMaxWait) {
			this.fetchMaxWait = fetchMaxWait;
		}
		
		public DataSize getFetchMinSize() {
			return this.fetchMinSize;
		}
		
		public void setFetchMinSize(DataSize fetchMinSize) {
			this.fetchMinSize = fetchMinSize;
		}
		
		public String getGroupId() {
			return this.groupId;
		}
		
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
		
		public Duration getHeartbeatInterval() {
			return this.heartbeatInterval;
		}
		
		public void setHeartbeatInterval(Duration heartbeatInterval) {
			this.heartbeatInterval = heartbeatInterval;
		}
		
		public KafkaCustomProperties.IsolationLevel getIsolationLevel() {
			return this.isolationLevel;
		}
		
		public void setIsolationLevel(KafkaCustomProperties.IsolationLevel isolationLevel) {
			this.isolationLevel = isolationLevel;
		}
		
		public Class<?> getKeyDeserializer() {
			return this.keyDeserializer;
		}
		
		public void setKeyDeserializer(Class<?> keyDeserializer) {
			this.keyDeserializer = keyDeserializer;
		}
		
		public Class<?> getValueDeserializer() {
			return this.valueDeserializer;
		}
		
		public void setValueDeserializer(Class<?> valueDeserializer) {
			this.valueDeserializer = valueDeserializer;
		}
		
		public Integer getMaxPollRecords() {
			return this.maxPollRecords;
		}
		
		public void setMaxPollRecords(Integer maxPollRecords) {
			this.maxPollRecords = maxPollRecords;
		}
		
		public Map<String, String> getProperties() {
			return this.properties;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getAutoCommitInterval).asInt(Duration::toMillis)
			   .to(properties.in(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG));
			map.from(this::getAutoOffsetReset).to(properties.in(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
			map.from(this::getBootstrapServers).to(properties.in(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
			map.from(this::getClientId).to(properties.in(ConsumerConfig.CLIENT_ID_CONFIG));
			map.from(this::getEnableAutoCommit).to(properties.in(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
			map.from(this::getFetchMaxWait).asInt(Duration::toMillis)
			   .to(properties.in(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG));
			map.from(this::getFetchMinSize).asInt(DataSize::toBytes)
			   .to(properties.in(ConsumerConfig.FETCH_MIN_BYTES_CONFIG));
			map.from(this::getGroupId).to(properties.in(ConsumerConfig.GROUP_ID_CONFIG));
			map.from(this::getHeartbeatInterval).asInt(Duration::toMillis)
			   .to(properties.in(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG));
			map.from(() -> getIsolationLevel().name().toLowerCase(Locale.ROOT))
			   .to(properties.in(ConsumerConfig.ISOLATION_LEVEL_CONFIG));
			map.from(this::getKeyDeserializer).to(properties.in(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
			map.from(this::getValueDeserializer).to(properties.in(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
			map.from(this::getMaxPollRecords).to(properties.in(ConsumerConfig.MAX_POLL_RECORDS_CONFIG));
			return properties.with(this.ssl, this.security, this.properties);
		}
		
	}
	
	public static class Producer {
		
		private final KafkaCustomProperties.Ssl ssl = new KafkaCustomProperties.Ssl();
		
		private final KafkaCustomProperties.Security security = new KafkaCustomProperties.Security();
		
		/**
		 * Number of acknowledgments the producer requires the leader to have received
		 * before considering CustConsumerConfig request complete.
		 */
		private String acks;
		
		/**
		 * Default batch size. A small batch size will make batching less common and may
		 * reduce throughput (CustConsumerConfig batch size of zero disables batching entirely).
		 */
		private DataSize batchSize;
		
		/**
		 * Comma-delimited list of host:port pairs to use for establishing the initial
		 * connections to the Kafka cluster. Overrides the global property, for producers.
		 */
		private List<String> bootstrapServers;
		
		/**
		 * Total memory size the producer can use to buffer records waiting to be sent to
		 * the server.
		 */
		private DataSize bufferMemory;
		
		/**
		 * ID to pass to the server when making requests. Used for server-side logging.
		 */
		private String clientId;
		
		/**
		 * Compression type for all data generated by the producer.
		 */
		private String compressionType;
		
		/**
		 * Serializer class for keys.
		 */
		private Class<?> keySerializer = StringSerializer.class;
		
		/**
		 * Serializer class for values.
		 */
		private Class<?> valueSerializer = StringSerializer.class;
		
		/**
		 * When greater than zero, enables retrying of failed sends.
		 */
		private Integer retries;
		
		/**
		 * When non empty, enables transaction support for producer.
		 */
		private String transactionIdPrefix;
		
		/**
		 * Additional producer-specific properties used to configure the client.
		 */
		private final Map<String, String> properties = new HashMap<>();
		
		public KafkaCustomProperties.Ssl getSsl() {
			return this.ssl;
		}
		
		public KafkaCustomProperties.Security getSecurity() {
			return this.security;
		}
		
		public String getAcks() {
			return this.acks;
		}
		
		public void setAcks(String acks) {
			this.acks = acks;
		}
		
		public DataSize getBatchSize() {
			return this.batchSize;
		}
		
		public void setBatchSize(DataSize batchSize) {
			this.batchSize = batchSize;
		}
		
		public List<String> getBootstrapServers() {
			return this.bootstrapServers;
		}
		
		public void setBootstrapServers(List<String> bootstrapServers) {
			this.bootstrapServers = bootstrapServers;
		}
		
		public DataSize getBufferMemory() {
			return this.bufferMemory;
		}
		
		public void setBufferMemory(DataSize bufferMemory) {
			this.bufferMemory = bufferMemory;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		public String getCompressionType() {
			return this.compressionType;
		}
		
		public void setCompressionType(String compressionType) {
			this.compressionType = compressionType;
		}
		
		public Class<?> getKeySerializer() {
			return this.keySerializer;
		}
		
		public void setKeySerializer(Class<?> keySerializer) {
			this.keySerializer = keySerializer;
		}
		
		public Class<?> getValueSerializer() {
			return this.valueSerializer;
		}
		
		public void setValueSerializer(Class<?> valueSerializer) {
			this.valueSerializer = valueSerializer;
		}
		
		public Integer getRetries() {
			return this.retries;
		}
		
		public void setRetries(Integer retries) {
			this.retries = retries;
		}
		
		public String getTransactionIdPrefix() {
			return this.transactionIdPrefix;
		}
		
		public void setTransactionIdPrefix(String transactionIdPrefix) {
			this.transactionIdPrefix = transactionIdPrefix;
		}
		
		public Map<String, String> getProperties() {
			return this.properties;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getAcks).to(properties.in(ProducerConfig.ACKS_CONFIG));
			map.from(this::getBatchSize).asInt(DataSize::toBytes).to(properties.in(ProducerConfig.BATCH_SIZE_CONFIG));
			map.from(this::getBootstrapServers).to(properties.in(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
			map.from(this::getBufferMemory).as(DataSize::toBytes)
			   .to(properties.in(ProducerConfig.BUFFER_MEMORY_CONFIG));
			map.from(this::getClientId).to(properties.in(ProducerConfig.CLIENT_ID_CONFIG));
			map.from(this::getCompressionType).to(properties.in(ProducerConfig.COMPRESSION_TYPE_CONFIG));
			map.from(this::getKeySerializer).to(properties.in(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
			map.from(this::getRetries).to(properties.in(ProducerConfig.RETRIES_CONFIG));
			map.from(this::getValueSerializer).to(properties.in(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
			return properties.with(this.ssl, this.security, this.properties);
		}
		
	}
	
	public static class Admin {
		
		private final KafkaCustomProperties.Ssl ssl = new KafkaCustomProperties.Ssl();
		
		private final KafkaCustomProperties.Security security = new KafkaCustomProperties.Security();
		
		/**
		 * ID to pass to the server when making requests. Used for server-side logging.
		 */
		private String clientId;
		
		/**
		 * Additional admin-specific properties used to configure the client.
		 */
		private final Map<String, String> properties = new HashMap<>();
		
		/**
		 * Whether to fail fast if the broker is not available on startup.
		 */
		private boolean failFast;
		
		public KafkaCustomProperties.Ssl getSsl() {
			return this.ssl;
		}
		
		public KafkaCustomProperties.Security getSecurity() {
			return this.security;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		public boolean isFailFast() {
			return this.failFast;
		}
		
		public void setFailFast(boolean failFast) {
			this.failFast = failFast;
		}
		
		public Map<String, String> getProperties() {
			return this.properties;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getClientId).to(properties.in(ProducerConfig.CLIENT_ID_CONFIG));
			return properties.with(this.ssl, this.security, this.properties);
		}
		
	}
	
	/**
	 * High (and some medium) priority Streams properties and CustConsumerConfig general properties bucket.
	 */
	public static class Streams {
		
		private final KafkaCustomProperties.Ssl ssl = new KafkaCustomProperties.Ssl();
		
		private final KafkaCustomProperties.Security security = new KafkaCustomProperties.Security();
		
		/**
		 * Kafka streams application.id property; default spring.application.name.
		 */
		private String applicationId;
		
		/**
		 * Whether or not to auto-start the streams factory bean.
		 */
		private boolean autoStartup = true;
		
		/**
		 * Comma-delimited list of host:port pairs to use for establishing the initial
		 * connections to the Kafka cluster. Overrides the global property, for streams.
		 */
		private List<String> bootstrapServers;
		
		/**
		 * Maximum memory size to be used for buffering across all threads.
		 */
		private DataSize cacheMaxSizeBuffering;
		
		/**
		 * ID to pass to the server when making requests. Used for server-side logging.
		 */
		private String clientId;
		
		/**
		 * The replication factor for change log topics and repartition topics created by
		 * the stream processing application.
		 */
		private Integer replicationFactor;
		
		/**
		 * Directory location for the state store.
		 */
		private String stateDir;
		
		/**
		 * Additional Kafka properties used to configure the streams.
		 */
		private final Map<String, String> properties = new HashMap<>();
		
		public KafkaCustomProperties.Ssl getSsl() {
			return this.ssl;
		}
		
		public KafkaCustomProperties.Security getSecurity() {
			return this.security;
		}
		
		public String getApplicationId() {
			return this.applicationId;
		}
		
		public void setApplicationId(String applicationId) {
			this.applicationId = applicationId;
		}
		
		public boolean isAutoStartup() {
			return this.autoStartup;
		}
		
		public void setAutoStartup(boolean autoStartup) {
			this.autoStartup = autoStartup;
		}
		
		public List<String> getBootstrapServers() {
			return this.bootstrapServers;
		}
		
		public void setBootstrapServers(List<String> bootstrapServers) {
			this.bootstrapServers = bootstrapServers;
		}
		
		public DataSize getCacheMaxSizeBuffering() {
			return this.cacheMaxSizeBuffering;
		}
		
		public void setCacheMaxSizeBuffering(DataSize cacheMaxSizeBuffering) {
			this.cacheMaxSizeBuffering = cacheMaxSizeBuffering;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		public Integer getReplicationFactor() {
			return this.replicationFactor;
		}
		
		public void setReplicationFactor(Integer replicationFactor) {
			this.replicationFactor = replicationFactor;
		}
		
		public String getStateDir() {
			return this.stateDir;
		}
		
		public void setStateDir(String stateDir) {
			this.stateDir = stateDir;
		}
		
		public Map<String, String> getProperties() {
			return this.properties;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getApplicationId).to(properties.in("application.id"));
			map.from(this::getBootstrapServers).to(properties.in(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
			map.from(this::getCacheMaxSizeBuffering).asInt(DataSize::toBytes)
			   .to(properties.in("cache.max.bytes.buffering"));
			map.from(this::getClientId).to(properties.in(CommonClientConfigs.CLIENT_ID_CONFIG));
			map.from(this::getReplicationFactor).to(properties.in("replication.factor"));
			map.from(this::getStateDir).to(properties.in("state.dir"));
			return properties.with(this.ssl, this.security, this.properties);
		}
		
	}
	
	public static class Template {
		
		/**
		 * Default topic to which messages are sent.
		 */
		private String defaultTopic;
		
		public String getDefaultTopic() {
			return this.defaultTopic;
		}
		
		public void setDefaultTopic(String defaultTopic) {
			this.defaultTopic = defaultTopic;
		}
		
	}
	
	public static class Listener {
		
		/**
		 * The offset commit behavior enumeration.
		 */
		public enum AckMode {
			
			/**
			 * Commit after each record is processed by the listener.
			 */
			RECORD,
			
			/**
			 * Commit whatever has already been processed before the next poll.
			 */
			BATCH,
			
			/**
			 * Commit pending updates after
			 */
			TIME,
			
			/**
			 * Commit pending updates after
			 * exceeded.
			 */
			COUNT,
			
			/**
			 * Commit pending updates after
			 * ackTime} has elapsed.
			 */
			COUNT_TIME,
			
			/**
			 * User takes responsibility for acks using an
			 */
			MANUAL,
			
			/**
			 * User takes responsibility for acks using an
			 * immediately processes the commit.
			 */
			MANUAL_IMMEDIATE,
			
		}
		
		public enum Type {
			
			/**
			 * Invokes the endpoint with one ConsumerRecord at CustConsumerConfig time.
			 */
			SINGLE,
			
			/**
			 * Invokes the endpoint with CustConsumerConfig batch of ConsumerRecords.
			 */
			BATCH
			
		}
		
		/**
		 * Listener type.
		 */
		private KafkaCustomProperties.Listener.Type type = KafkaCustomProperties.Listener.Type.SINGLE;
		
		/**
		 * Listener AckMode. See the spring-kafka documentation.
		 */
		private AckMode ackMode;
		
		/**
		 * Prefix for the listener's consumer client.id property.
		 */
		private String clientId;
		
		/**
		 * Number of threads to run in the listener containers.
		 */
		private Integer concurrency;
		
		/**
		 * Timeout to use when polling the consumer.
		 */
		private Duration pollTimeout;
		
		/**
		 * Multiplier applied to "pollTimeout" to determine if CustConsumerConfig consumer is
		 * non-responsive.
		 */
		private Float noPollThreshold;
		
		/**
		 * Number of records between offset commits when ackMode is "COUNT" or
		 * "COUNT_TIME".
		 */
		private Integer ackCount;
		
		/**
		 * Time between offset commits when ackMode is "TIME" or "COUNT_TIME".
		 */
		private Duration ackTime;
		
		/**
		 * Time between publishing idle consumer events (no data received).
		 */
		private Duration idleEventInterval;
		
		/**
		 * Time between checks for non-responsive consumers. If CustConsumerConfig duration suffix is not
		 * specified, seconds will be used.
		 */
		@DurationUnit(ChronoUnit.SECONDS)
		private Duration monitorInterval;
		
		/**
		 * Whether to log the container configuration during initialization (INFO level).
		 */
		private Boolean logContainerConfig;
		
		/**
		 * Whether the container should fail to start if at least one of the configured
		 * topics are not present on the broker.
		 */
		private boolean missingTopicsFatal = false;
		
		public KafkaCustomProperties.Listener.Type getType() {
			return this.type;
		}
		
		public void setType(KafkaCustomProperties.Listener.Type type) {
			this.type = type;
		}
		
		public AckMode getAckMode() {
			return this.ackMode;
		}
		
		public void setAckMode(AckMode ackMode) {
			this.ackMode = ackMode;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		
		public Integer getConcurrency() {
			return this.concurrency;
		}
		
		public void setConcurrency(Integer concurrency) {
			this.concurrency = concurrency;
		}
		
		public Duration getPollTimeout() {
			return this.pollTimeout;
		}
		
		public void setPollTimeout(Duration pollTimeout) {
			this.pollTimeout = pollTimeout;
		}
		
		public Float getNoPollThreshold() {
			return this.noPollThreshold;
		}
		
		public void setNoPollThreshold(Float noPollThreshold) {
			this.noPollThreshold = noPollThreshold;
		}
		
		public Integer getAckCount() {
			return this.ackCount;
		}
		
		public void setAckCount(Integer ackCount) {
			this.ackCount = ackCount;
		}
		
		public Duration getAckTime() {
			return this.ackTime;
		}
		
		public void setAckTime(Duration ackTime) {
			this.ackTime = ackTime;
		}
		
		public Duration getIdleEventInterval() {
			return this.idleEventInterval;
		}
		
		public void setIdleEventInterval(Duration idleEventInterval) {
			this.idleEventInterval = idleEventInterval;
		}
		
		public Duration getMonitorInterval() {
			return this.monitorInterval;
		}
		
		public void setMonitorInterval(Duration monitorInterval) {
			this.monitorInterval = monitorInterval;
		}
		
		public Boolean getLogContainerConfig() {
			return this.logContainerConfig;
		}
		
		public void setLogContainerConfig(Boolean logContainerConfig) {
			this.logContainerConfig = logContainerConfig;
		}
		
		public boolean isMissingTopicsFatal() {
			return this.missingTopicsFatal;
		}
		
		public void setMissingTopicsFatal(boolean missingTopicsFatal) {
			this.missingTopicsFatal = missingTopicsFatal;
		}
		
	}
	
	public static class Ssl {
		
		/**
		 * Password of the private key in the key store file.
		 */
		private String keyPassword;
		
		/**
		 * Location of the key store file.
		 */
		private Resource keyStoreLocation;
		
		/**
		 * Store password for the key store file.
		 */
		private String keyStorePassword;
		
		/**
		 * Type of the key store.
		 */
		private String keyStoreType;
		
		/**
		 * Location of the trust store file.
		 */
		private Resource trustStoreLocation;
		
		/**
		 * Store password for the trust store file.
		 */
		private String trustStorePassword;
		
		/**
		 * Type of the trust store.
		 */
		private String trustStoreType;
		
		/**
		 * SSL protocol to use.
		 */
		private String protocol;
		
		public String getKeyPassword() {
			return this.keyPassword;
		}
		
		public void setKeyPassword(String keyPassword) {
			this.keyPassword = keyPassword;
		}
		
		public Resource getKeyStoreLocation() {
			return this.keyStoreLocation;
		}
		
		public void setKeyStoreLocation(Resource keyStoreLocation) {
			this.keyStoreLocation = keyStoreLocation;
		}
		
		public String getKeyStorePassword() {
			return this.keyStorePassword;
		}
		
		public void setKeyStorePassword(String keyStorePassword) {
			this.keyStorePassword = keyStorePassword;
		}
		
		public String getKeyStoreType() {
			return this.keyStoreType;
		}
		
		public void setKeyStoreType(String keyStoreType) {
			this.keyStoreType = keyStoreType;
		}
		
		public Resource getTrustStoreLocation() {
			return this.trustStoreLocation;
		}
		
		public void setTrustStoreLocation(Resource trustStoreLocation) {
			this.trustStoreLocation = trustStoreLocation;
		}
		
		public String getTrustStorePassword() {
			return this.trustStorePassword;
		}
		
		public void setTrustStorePassword(String trustStorePassword) {
			this.trustStorePassword = trustStorePassword;
		}
		
		public String getTrustStoreType() {
			return this.trustStoreType;
		}
		
		public void setTrustStoreType(String trustStoreType) {
			this.trustStoreType = trustStoreType;
		}
		
		public String getProtocol() {
			return this.protocol;
		}
		
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getKeyPassword).to(properties.in(SslConfigs.SSL_KEY_PASSWORD_CONFIG));
			map.from(this::getKeyStoreLocation).as(this::resourceToPath)
			   .to(properties.in(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG));
			map.from(this::getKeyStorePassword).to(properties.in(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG));
			map.from(this::getKeyStoreType).to(properties.in(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG));
			map.from(this::getTrustStoreLocation).as(this::resourceToPath)
			   .to(properties.in(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG));
			map.from(this::getTrustStorePassword).to(properties.in(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG));
			map.from(this::getTrustStoreType).to(properties.in(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG));
			map.from(this::getProtocol).to(properties.in(SslConfigs.SSL_PROTOCOL_CONFIG));
			return properties;
		}
		
		private String resourceToPath(Resource resource) {
			try {
				return resource.getFile().getAbsolutePath();
			} catch (IOException ex) {
				throw new IllegalStateException("Resource '" + resource + "' must be on CustConsumerConfig file system", ex);
			}
		}
		
	}
	
	public static class Jaas {
		
		/**
		 * Whether to enable JAAS configuration.
		 */
		private boolean enabled;
		
		/**
		 * Login module.
		 */
		private String loginModule = "com.sun.security.auth.module.Krb5LoginModule";
		
		
		/**
		 * Additional JAAS options.
		 */
		private final Map<String, String> options = new HashMap<>();
		
		public boolean isEnabled() {
			return this.enabled;
		}
		
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		public String getLoginModule() {
			return this.loginModule;
		}
		
		public void setLoginModule(String loginModule) {
			this.loginModule = loginModule;
		}
		
		
		public Map<String, String> getOptions() {
			return this.options;
		}
		
		public void setOptions(Map<String, String> options) {
			if (options != null) {
				this.options.putAll(options);
			}
		}
		
	}
	
	public static class Security {
		
		/**
		 * Security protocol used to communicate with brokers.
		 */
		private String protocol;
		
		public String getProtocol() {
			return this.protocol;
		}
		
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		
		public Map<String, Object> buildProperties() {
			KafkaCustomProperties.Properties properties = new KafkaCustomProperties.Properties();
			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
			map.from(this::getProtocol).to(properties.in(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG));
			return properties;
		}
		
	}
	
	public enum IsolationLevel {
		
		/**
		 * Read everything including aborted transactions.
		 */
		READ_UNCOMMITTED((byte) 0),
		
		/**
		 * Read records from committed transactions, in addition to records not part of
		 * transactions.
		 */
		READ_COMMITTED((byte) 1);
		
		private final byte id;
		
		IsolationLevel(byte id) {
			this.id = id;
		}
		
		public byte id() {
			return this.id;
		}
		
	}
	
	@SuppressWarnings("serial")
	private static class Properties extends HashMap<String, Object> {
		
		<V> java.util.function.Consumer<V> in(String key) {
			return (value) -> put(key, value);
		}
		
		KafkaCustomProperties.Properties with(KafkaCustomProperties.Ssl ssl, KafkaCustomProperties.Security security,
		                                      Map<String, String> properties) {
			putAll(ssl.buildProperties());
			putAll(security.buildProperties());
			putAll(properties);
			return this;
		}
		
	}
	
}

