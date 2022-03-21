package yfu.practice.springkafka.service.impl;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestWorkInConsumer {
	
	private int count = 0;
	
	private Random random = new Random();

//	@Async
	public void doWork(int partition) {
		int localCount = ++count;
		long id = Thread.currentThread().getId();
		
		log.info("開始, Partition: {}, Service Count: {}, Thread Id: {}", partition, localCount, id);
		
		try {
			Thread.sleep(random.nextInt(2000));
		} catch (InterruptedException e) {
			log.error("睡覺也會出錯?", e);
		}
		
		log.info("結束, Partition: {}, Service Count: {}, Thread Id: {}", partition, localCount, id);
	}
	
}