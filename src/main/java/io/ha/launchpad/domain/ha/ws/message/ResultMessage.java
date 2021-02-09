package io.ha.launchpad.domain.ha.ws.message;

import lombok.Data;

@Data
public class ResultMessage {
	
	private String id;
	
	private String type;
	
	private boolean success;
}
