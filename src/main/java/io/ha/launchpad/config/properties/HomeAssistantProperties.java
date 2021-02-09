package io.ha.launchpad.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "ha")
public class HomeAssistantProperties {
	
	private String url;
	
	private String token;
	
	private String websocket;
}
