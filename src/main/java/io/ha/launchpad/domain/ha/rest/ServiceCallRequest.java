package io.ha.launchpad.domain.ha.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServiceCallRequest {

	@JsonProperty("entity_id")
	private String entityId;
	
}
