package yfu.practice.springkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		// 建立到Kafka cluster的"初始"連接主機。無論設置為何，客戶端都會使用所有的server
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		return new KafkaAdmin(configs);
	}
	
	/**
	 * 如果Topic不存在就建立
	 * @param topicName
	 * @return
	 */
	@Bean
	public NewTopic myTopic(@Value("${spring.kafka.topic-name}") String topicName) {
		return TopicBuilder.name(topicName)
				.partitions(3)
				.replicas(2)
				.build();
	}
}