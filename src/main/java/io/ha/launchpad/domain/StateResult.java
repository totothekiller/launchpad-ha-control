package io.ha.launchpad.domain;

import java.time.OffsetDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StateResult {
	
	@JsonProperty("entity_id")
	private String entityId;
	
	@JsonProperty("last_changed")
	private OffsetDateTime lastChanged;
	
	@JsonProperty("last_updated")
	private OffsetDateTime lastUpdated;
	
	private String state;
	
	private Map<String, String> attributes;
}
