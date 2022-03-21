package yfu.practice.springkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import yfu.practice.springkafka.dto.TestDto;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;
	
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TestDto>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, TestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		/*
		 * 產生3個KafkaListenerContainer同時處理3個Partition
		 * 可在@KafkaListener覆蓋設定
		 */
		factory.setConcurrency(3);
		
		ContainerProperties props = factory.getContainerProperties();
		props.setPollTimeout(3000);
		props.setAckMode(AckMode.MANUAL_IMMEDIATE);		// 手動commit
		return factory;
	}

	@Bean
	public ConsumerFactory<String, TestDto> consumerFactory() {
//		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(TestDto.class));	// 直接指定序列化器
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}
	
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		// 反序列化器要與Producer的序列化器成對
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// Consumer的JsonDeserializer要另外指定信任的package 或 在ConsumerFactory的建構子傳入JsonDeserializer的實例
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(JsonDeserializer.TRUSTED_PACKAGES, "yfu.practice.springkafka.dto");
		/*
		 * 當指定Topic沒有初始offset要如何重置
		 * earliest: 自動重置為最早的offset
		 * latest: 自動重置為最新的offset
		 * none: 在Consumer group中尋找前一個offset，若無拋例外
		 * anything else: 拋例外
		 */
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");	
		return configs;
	}
}