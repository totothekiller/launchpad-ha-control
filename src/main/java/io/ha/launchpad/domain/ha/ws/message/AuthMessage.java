package io.ha.launchpad.domain.ha.ws.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthMessage {

	private String type;
	
	@JsonProperty("access_token")
	private String accessToken;
	
	private String message;
}
