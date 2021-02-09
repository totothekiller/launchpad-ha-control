package io.ha.launchpad.domain.ha.ws.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubscribeEventsMessage {

	private String id;
	
	private String type = "subscribe_events";
	
	@JsonProperty("event_type")
	private String eventType;
	
}
