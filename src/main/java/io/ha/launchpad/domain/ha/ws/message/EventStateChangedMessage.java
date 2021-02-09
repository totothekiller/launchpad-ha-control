package io.ha.launchpad.domain.ha.ws.message;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EventStateChangedMessage {

	private EventStateChangedDataMessage data;
	
	@JsonProperty("event_type")
	private String eventType;
	
	@JsonProperty("time_fired")
	private OffsetDateTime timeFired;
}	
