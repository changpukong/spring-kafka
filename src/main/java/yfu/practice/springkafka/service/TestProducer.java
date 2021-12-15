package yfu.practice.springkafka.service;

import yfu.practice.springkafka.dto.TestDto;

public interface TestProducer {

	void produce(TestDto testDto);
}