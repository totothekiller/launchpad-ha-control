package io.ha.launchpad.domain.launchpad.handler.pad;

import io.ha.launchpad.config.properties.PadProperties;
import io.ha.launchpad.domain.ha.rest.ServiceCallRequest;
import io.ha.launchpad.domain.launchpad.handler.PadHandler;
import io.ha.launchpad.service.HomeAssistantRestApi;
import lombok.Data;
import lombok.ToString;
import net.thecodersbreakfast.lp4j.api.Pad;

@Data
@ToString(exclude = "api")
public class HACallServicePad implements PadHandler {
	
	private final PadProperties config;
	
	private final HomeAssistantRestApi api;
	
	private final Pad pad;
	
	/**
	 * @param config {@link PadProperties}
	 * @param api {@link HomeAssistantRestApi}
	 */
	public HACallServicePad(PadProperties config, HomeAssistantRestApi api)
	{
		this.config = config;
		this.api = api;
		this.pad = Pad.at(config.getX(), config.getY());
	}
	
	@Override
	public boolean accept(Pad other) {
		return this.pad.equals(other);
	}

	@Override
	public void onPadPressed(Pad pad, long timestamp) {
		
		ServiceCallRequest request = new ServiceCallRequest();
		request.setEntityId(config.getEntityId());
		
		// Call Service
		api.callService(config.getDomain(), config.getService(), request);
	}

	@Override
	public void onPadReleased(Pad pad, long timestamp) {
		// Nothing
	}
}
