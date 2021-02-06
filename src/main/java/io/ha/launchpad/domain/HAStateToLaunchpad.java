package io.ha.launchpad.domain;

import org.springframework.http.ResponseEntity;

import io.ha.launchpad.config.properties.LaunchpadProperties;
import io.ha.launchpad.config.properties.PadProperties;
import io.ha.launchpad.service.HomeAssistantRestApi;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;

@Data
@Slf4j
public class HAStateToLaunchpad implements Runnable {
	
	private final PadProperties padProperties;
	private final LaunchpadProperties launchpadProperties;
	private final HomeAssistantRestApi homeAssistantRestApi;
	private final LaunchpadClient launchpadClient;
	
	@Override
	public void run() {
		// Retrieve pad properties
		String entityId = padProperties.getEntityId();
		int padX = padProperties.getX();
		int padY = padProperties.getY();

		ResponseEntity<StateResult> responseEntity = homeAssistantRestApi.getState(entityId);
		//log.info("State = {}", state.getBody());
		
		String entityState = responseEntity.getBody().getState();
		
		if (entityId.contains("switch")) {
			if (entityState.contains("on")) {
				launchpadClient.setPadLight(Pad.at(padX, padY), Color.YELLOW, BackBufferOperation.NONE);
			} else if (entityState.contains("off")) {
				launchpadClient.setPadLight(Pad.at(padX, padY), Color.BLACK, BackBufferOperation.NONE);
			}
		} else if (entityId.contains("cover")) {
			if (entityState.contains("open")) {
				launchpadClient.setPadLight(Pad.at(padX, padY), Color.GREEN, BackBufferOperation.NONE);
			} else if (entityState.contains("close")) {
				launchpadClient.setPadLight(Pad.at(padX, padY), Color.RED, BackBufferOperation.NONE);
			}
		}
	}
}
