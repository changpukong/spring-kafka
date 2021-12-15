package yfu.practice.springkafka.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import yfu.practice.springkafka.dto.TestDto;

@Component
public class Listener {

	@KafkaListener(topics = "${spring.kafka.topic-name}", groupId = "myListener", concurrency = "3",	// 可同時處理3個partition
			containerFactory = "kafkaListenerContainerFactory")		// containerFactory的beanName，預設值名稱是kafkaListenerContainerFactory
	public void myListener(TestDto testDto, Acknowledgment ack) {	// ActMode為手動commit時，可用Acknowledgment進行commit
		StringBuilder sb = new StringBuilder()
				.append("***Thread Id: ").append(Thread.currentThread().getId())
				.append(", TestDto: ").append(testDto);
		System.out.println(sb);
		ack.acknowledge();		// commit
	}
}