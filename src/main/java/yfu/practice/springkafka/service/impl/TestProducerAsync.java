package yfu.practice.springkafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import yfu.practice.springkafka.dto.TestDto;
import yfu.practice.springkafka.service.TestProducer;

@Service("Async")
public class TestProducerAsync implements TestProducer {

	@Autowired
	private KafkaTemplate<String, TestDto> kafkaTemplate;
	
	@Value("${spring.kafka.topic-name}")
	private String topicName;
	
	@Override
	public void produce(TestDto testDto) {
		for (int i = 0; i < 10; i++) {
			System.out.println("***i = " + i);
			
			ListenableFuture<SendResult<String, TestDto>> future = kafkaTemplate.send(topicName, testDto);
			future.addCallback(new KafkaSendCallback<String, TestDto>() {
				
				@Override
				public void onSuccess(SendResult<String, TestDto> result) {
					System.out.println("***成功");
				}
				
				@Override
				public void onFailure(KafkaProducerException ex) {
					System.out.println("***失敗");
				}
			});
		}
	}
}