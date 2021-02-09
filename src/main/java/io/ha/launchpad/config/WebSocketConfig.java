package io.ha.launchpad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.ha.launchpad.config.properties.HomeAssistantProperties;
import io.ha.launchpad.service.HomeAssistantWebSocketApi;

@Configuration
@Profile("websocket")
public class WebSocketConfig {
	
	@Autowired
	private HomeAssistantProperties config;
	
	@Bean
	public WebSocketConnectionManager createWSManager(HomeAssistantWebSocketApi haws)
	{
		WebSocketClient client = new StandardWebSocketClient();

		WebSocketConnectionManager wscm = new WebSocketConnectionManager(client, haws, config.getWebsocket());
		
		wscm.setAutoStartup(true);
		
		return wscm;
	}
	
}
