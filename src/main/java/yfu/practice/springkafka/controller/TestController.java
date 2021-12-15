package yfu.practice.springkafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import yfu.practice.springkafka.dto.TestDto;
import yfu.practice.springkafka.service.TestProducer;

@RestController
public class TestController {
	
	@Autowired
	@Qualifier("Async")
	private TestProducer testProducerAsync;
	
	@Autowired
	@Qualifier("Sync")
	private TestProducer testProducerSync;

	@PostMapping(value = "/produceAsync")
	@ApiOperation("非同步發送")
	public void produceAsync(TestDto testDto) {
		testProducerAsync.produce(testDto);
	}
	
	@PostMapping(value = "/produceSync")
	@ApiOperation("同步發送")
	public void produceSync(TestDto testDto) {
		testProducerSync.produce(testDto);
	}
}