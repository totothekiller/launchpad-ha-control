package io.ha.launchpad.domain.ha.ws.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.ha.launchpad.domain.ha.rest.StateResult;
import lombok.Data;

@Data
public class EventStateChangedDataMessage {
	
	@JsonProperty("entity_id")
	private String entityId;
	
	@JsonProperty("new_state")
	private StateResult newState;
	
	@JsonProperty("old_state")
	private StateResult oldState;

}
