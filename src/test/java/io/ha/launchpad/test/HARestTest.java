package io.ha.launchpad.test;

import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import io.ha.launchpad.domain.ServiceCallRequest;
import io.ha.launchpad.domain.StateResult;
import io.ha.launchpad.service.HomeAssistantRestApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class HARestTest {

	@Autowired
	private HomeAssistantRestApi ha;
	
	@Test
	public void testSunState()
	{
		// Get Sun states
		ResponseEntity<StateResult> state = ha.getState("sun.sun");
		
		log.info("State = {}", state.getBody());
		
		Assertions.assertEquals(OK, state.getStatusCode());
	}
	
	@Test
	public void testCallService()
	{
		ServiceCallRequest request = new ServiceCallRequest();
		request.setEntityId("light.garage_light");
		
		// Toggle
		ResponseEntity<JsonNode> response = ha.callService("light", "toggle", request);
		
		log.info("Response = {}", response.getBody());
		
		Assertions.assertEquals(OK, response.getStatusCode());
	}
	
}
