package io.ha.launchpad.task;

import static net.thecodersbreakfast.lp4j.api.BackBufferOperation.NONE;
import static net.thecodersbreakfast.lp4j.api.Color.GREEN;
import static net.thecodersbreakfast.lp4j.api.ScrollSpeed.SPEED_MIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import io.ha.launchpad.config.properties.LaunchpadProperties;
import io.ha.launchpad.config.properties.PadProperties;
import io.ha.launchpad.domain.handler.PadHandler;
import io.ha.launchpad.domain.handler.pad.HACallServicePad;
import io.ha.launchpad.service.EventDispatcherService;
import io.ha.launchpad.service.HomeAssistantRestApi;
import lombok.extern.slf4j.Slf4j;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;

@Slf4j
@Service
@Profile("connect")
public class ConnectLaunchpadTask implements ApplicationRunner {

	@Autowired
	private LaunchpadProperties config;
	
	@Autowired
	private EventDispatcherService dispatcher;
	
	@Autowired
	private HomeAssistantRestApi ha;
	
	@Autowired
	private Launchpad launchpad;
	
	@Autowired
	private LaunchpadClient client;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		log.info("Welcome !");
		
		// Welcome text
		client.scrollText("Welcome !!", GREEN, SPEED_MIN, false, NONE);
		
		// Configurer les Handler
		for (PadProperties padConfig : config.getPads()) {
			
			PadHandler handler = new HACallServicePad(padConfig, ha);
			
			dispatcher.addPadHandler(handler);
		}
		
		// Configure Lauchpad
		launchpad.setListener(dispatcher);
		
		log.info("System is ready !, type any char to close program.");
		
		System.in.read();
		
		launchpad.close();
		
		log.info("Bye, Bye !");
	}

}
