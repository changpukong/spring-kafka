package yfu.practice.springkafka.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import yfu.practice.springkafka.dto.TestDto;
import yfu.practice.springkafka.service.TestProducer;

@Service("Sync")
public class TestProducerSync implements TestProducer {

	@Autowired
	private KafkaTemplate<String, TestDto> kafkaTemplate;

	@Value("${spring.kafka.topic-name}")
	private String topicName;

	@Override
	public void produce(TestDto testDto) {
		for (int i = 0; i < 10; i++) {
			try {
				kafkaTemplate.send(topicName, testDto).get(3, TimeUnit.SECONDS);
				System.out.println("***成功");
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				System.out.println("***失敗");
			}
		}
	}
}