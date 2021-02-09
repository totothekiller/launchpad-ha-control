package io.ha.launchpad.domain.ha.rest;

import java.time.OffsetDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StateResult {
	
	@JsonProperty("entity_id")
	private String entityId;
	
	private String state;
	
	@JsonProperty("last_changed")
	private OffsetDateTime lastChanged;
	
	@JsonProperty("last_updated")
	private OffsetDateTime lastUpdated;
	
	private Map<String, Object> attributes;
}
