package io.ha.launchpad.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.ha.launchpad.config.properties.HomeAssistantProperties;
import io.ha.launchpad.domain.ha.ws.listener.StateChangedListener;
import io.ha.launchpad.domain.ha.ws.message.AuthMessage;
import io.ha.launchpad.domain.ha.ws.message.EventStateChangedMessage;
import io.ha.launchpad.domain.ha.ws.message.ResultMessage;
import io.ha.launchpad.domain.ha.ws.message.SubscribeEventsMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("websocket")
public class HomeAssistantWebSocketApi extends TextWebSocketHandler {

	@Autowired
	private ObjectMapper json;
	
	@Autowired
	private HomeAssistantProperties config;
	
	private Map<String, List<StateChangedListener>> listeners = new HashMap<>();
	
	public void addListener(String entityId, StateChangedListener listener)
	{
		// Find key
		List<StateChangedListener> list = listeners.get(entityId);
		
		// Create if not exist
		if(list == null)
		{
			list = new LinkedList<>();
			listeners.put(entityId, list);
		}
		
		// Append Listener
		list.add(listener);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.trace("[WS] Connected !");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		log.trace("[WS] Input Message : {}", message);
		
		// Parse JSON
		final JsonNode node = json.readTree(message.getPayload());


		switch(node.get("type").asText())
		{
		case "auth_required":

			log.debug("[WS] Send Auth Request");
			
			
			final AuthMessage authRequest = new AuthMessage();
			
			authRequest.setType("auth");
			authRequest.setAccessToken(config.getToken());
			
			String payload = json.writeValueAsString(authRequest);
			
			// Send
			session.sendMessage(new TextMessage(payload));

			break;
			
		case "auth_ok":
			
			log.info("[WS] Successfully Authenticated !");
		
			
			// Subscribe to events
			
			SubscribeEventsMessage subscribeRequest = new SubscribeEventsMessage();
			
			subscribeRequest.setId("1234");
			subscribeRequest.setEventType("state_changed");
			
			String payloadRequest = json.writeValueAsString(subscribeRequest);
			
			// Send
			session.sendMessage(new TextMessage(payloadRequest));
			
			break;
			
		case "auth_invalid":
			
			String reason = node.get("message").asText("");
			
			log.error("[WS] Authentification KO : {}", reason);
			
			break;
			
		case "result":
			
			ResultMessage response = json.readValue(node.traverse(), ResultMessage.class);
			
			log.debug("[WS] Reponse = {}", response);
			
			break;
			
			
		case "event":
			
			JsonNode eventNode = node.get("event");
			
			switch (eventNode.get("event_type").asText()) {
			
			case "state_changed":
				
				EventStateChangedMessage escm = json.readValue(eventNode.traverse(), EventStateChangedMessage.class);
				
				log.debug("Event changed = {}", escm);
				
				// Get listeners
				List<StateChangedListener> selectedListeners = listeners.get(escm.getData().getEntityId());
				
				if(selectedListeners != null)
				{
					for (StateChangedListener stateChangedListener : selectedListeners) {
						
						try {
							stateChangedListener.onChanged(escm);
						} catch (Exception e)
						{
							log.warn("Error during execution of listener", e);
						}
					}
				}
				
				break;

			}
			
			
			break;
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("[WS] Closed !");
	}

}
