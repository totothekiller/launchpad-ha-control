package io.ha.launchpad.service;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import io.ha.launchpad.config.properties.HomeAssistantProperties;
import io.ha.launchpad.domain.ServiceCallRequest;
import io.ha.launchpad.domain.StateResult;
import lombok.extern.slf4j.Slf4j;

/**
 * REST API to Home Assistant
 * 
 * @author pfeifft
 */
@Slf4j
@Service
public class HomeAssistantRestApi {

	@Autowired
	private HomeAssistantProperties config;
	
	private RestTemplate http;
	
	public HomeAssistantRestApi(RestTemplateBuilder restTemplateBuilder) {
		http = restTemplateBuilder.build();
    }
	
	/**
	 * Calls a service within a specific domain. Will return when the service has been executed or after 10 seconds, whichever comes first.
	 * 
	 * @param domain
	 * @param service
	 * @param param {@link ServiceCallRequest}
	 * @return {@link ResponseEntity}
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity<JsonNode> callService(String domain, String service, ServiceCallRequest param)
	{
		// Build URL
		final URI url = UriComponentsBuilder
							.fromHttpUrl(config.getUrl())
							.path("/api")
							.path("/services")
							.path("/{domain}")
							.path("/{service}")
							.build(domain, service);
		
		// Create Header
		final HttpHeaders headers = new HttpHeaders();
		
		headers.setBearerAuth(config.getToken());
		headers.setContentType(APPLICATION_JSON);
		
		// Create Request
		final HttpEntity request = new HttpEntity<>(param, headers);
		
		final ResponseEntity<JsonNode> response = http.exchange(url, POST, request, JsonNode.class);
		
		return response;
	}
	
	/**
	 * Returns a state object for specified entity_id. Returns 404 if not found.
	 * 
	 * @param entityId
	 * @return ResponseEntity
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity<StateResult> getState(String entityId)
	{
		// Build URL
		final URI url = UriComponentsBuilder
							.fromHttpUrl(config.getUrl())
							.path("/api")
							.path("/states")
							.path("/{entityId}")
							.build(entityId);
		
		// Create Header
		final HttpHeaders headers = new HttpHeaders();
		
		headers.setBearerAuth(config.getToken());
		headers.setContentType(APPLICATION_JSON);
		
		// Create Request
		final HttpEntity request = new HttpEntity<>(headers);
		
		// Make Request
		final ResponseEntity<StateResult> response = http.exchange(url, GET, request, StateResult.class);
		
		return response;
	}
	
}
