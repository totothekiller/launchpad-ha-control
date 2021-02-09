package io.ha.launchpad.domain.launchpad.handler;

import net.thecodersbreakfast.lp4j.api.Button;

public interface ButtonHandler {

	boolean accept(Button pad);
	
	void onButtonPressed(Button button, long timestamp);
	
	void onButtonReleased(Button button, long timestamp);
	
}
