package yfu.practice.springkafka.kafka.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import yfu.practice.springkafka.dto.TestDto;
import yfu.practice.springkafka.service.impl.TestWorkInConsumer;

@Slf4j
@Component
public class TestConsumer {
	
	@Autowired
	private ApplicationContext context;
	
	@KafkaListener(topics = "${spring.kafka.topic-name}", groupId = "myListener", concurrency = "3",	// 可同時處理3個partition
			containerFactory = "kafkaListenerContainerFactory")		// containerFactory的beanName，預設值名稱是kafkaListenerContainerFactory
	public void myListener(TestDto testDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, Acknowledgment ack) {	
		log.info("Kafka Listener, Partition: {}, Thread Id: {}", partition, Thread.currentThread().getId());
		doWork(testDto, partition);
		ack.acknowledge();		// ActMode為手動commit時，可用Acknowledgment進行commit	
	}
	
	private void doWork(TestDto testDto, int partition) {
		List<TestDto> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(testDto);
		}
		
		list.forEach(e -> {
			TestWorkInConsumer service = context.getBean(TestWorkInConsumer.class);
			service.doWork(partition);
		});
	}
}