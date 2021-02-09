package io.ha.launchpad.test;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.ha.launchpad.service.HomeAssistantWebSocketApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("websocket")
public class HAWebSocketTest {

	
	@Autowired
	private HomeAssistantWebSocketApi ws;
	
	@Test
	public void authTest() throws InterruptedException
	{
		
		CountDownLatch latch = new CountDownLatch(1);
		
		
		ws.addListener("light.garage_light", d -> {
			
			log.info("Detected Event");
			
			latch.countDown();
		});
		

		latch.await();
		
		log.debug("End");
		
	}
	
}
