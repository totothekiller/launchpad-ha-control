package io.ha.launchpad.domain.launchpad.handler;

import net.thecodersbreakfast.lp4j.api.Pad;

public interface PadHandler {
	
	boolean accept(Pad pad);
	
	void onPadPressed(Pad pad, long timestamp);
	
	void onPadReleased(Pad pad, long timestamp);
	
}
