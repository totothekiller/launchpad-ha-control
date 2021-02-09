package io.ha.launchpad.domain.ha.ws.listener;

import io.ha.launchpad.domain.ha.ws.message.EventStateChangedMessage;

@FunctionalInterface
public interface StateChangedListener {

	
	public void onChanged(EventStateChangedMessage changed);
	
}
