package io.ha.launchpad.task;

import static net.thecodersbreakfast.lp4j.api.BackBufferOperation.NONE;
import static net.thecodersbreakfast.lp4j.api.Color.GREEN;
import static net.thecodersbreakfast.lp4j.api.ScrollSpeed.SPEED_MIN;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import io.ha.launchpad.config.properties.LaunchpadProperties;
import io.ha.launchpad.config.properties.PadProperties;
import io.ha.launchpad.domain.HAStateToLaunchpad;
import io.ha.launchpad.domain.handler.PadHandler;
import io.ha.launchpad.domain.handler.pad.HACallServicePad;
import io.ha.launchpad.service.EventDispatcherService;
import io.ha.launchpad.service.HomeAssistantRestApi;
import lombok.extern.slf4j.Slf4j;
import net.thecodersbreakfast.lp4j.api.Brightness;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;

@Slf4j
@Service
@Profile("connect")
public class ConnectLaunchpadTask implements ApplicationRunner {

	@Autowired
	private LaunchpadProperties launchpadProperties;
	
	@Autowired
	private EventDispatcherService eventDispatcherService;
	
	@Autowired
	private HomeAssistantRestApi homeAssistantRestApi;
	
	@Autowired
	private Launchpad launchpad;
	
	@Autowired
	private LaunchpadClient launchpadClient;
	
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		log.info("Welcome!");
		
		// Welcome text
		launchpadClient.reset();
		launchpadClient.setBrightness(Brightness.of(15));
		launchpadClient.scrollText("LP4HA", GREEN, SPEED_MIN, false, NONE);
		
		// Configure Handlers
		for (PadProperties padProperties : launchpadProperties.getPads()) {
			PadHandler handler = new HACallServicePad(padProperties, homeAssistantRestApi);
			eventDispatcherService.addPadHandler(handler);
		}
		
		// Configure Lauchpad
		launchpad.setListener(eventDispatcherService);
		
		log.info("System is ready! Type any char to close the program.");
		
		for (PadProperties padProperties : launchpadProperties.getPads()) {
			HAStateToLaunchpad haStateToLaunchpad = new HAStateToLaunchpad(padProperties, launchpadProperties, homeAssistantRestApi, launchpadClient);
			scheduledExecutorService.scheduleWithFixedDelay(haStateToLaunchpad, 0, 1, TimeUnit.SECONDS);
		}
		
		// Type any char to close the program
		System.in.read();

		scheduledExecutorService.shutdown();
		launchpad.close();
		log.info("Bye, Bye!");
	}

}
