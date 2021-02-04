package io.ha.launchpad.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.ha.launchpad.domain.handler.PadHandler;
import lombok.extern.slf4j.Slf4j;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.LaunchpadListener;
import net.thecodersbreakfast.lp4j.api.Pad;

@Slf4j
@Service
public class EventDispatcherService implements LaunchpadListener {
	
	private List<PadHandler> padHandler = new LinkedList<>();
	
	public void addPadHandler(PadHandler ph)
	{
		padHandler.add(ph);
	}
	
	@Override
	public void onPadPressed(Pad pad, long timestamp) {
		
		log.debug("Pad pressed = {}", pad);
		
		for (PadHandler handler : padHandler) {
			if(handler.accept(pad))
			{
				// TODO Mettre dans un autre thread
				try
				{
					handler.onPadPressed(pad, timestamp);
				} catch (Exception e) {
					log.warn("Handler error", e);
				}
				
			}
		}
	}

	@Override
	public void onPadReleased(Pad pad, long timestamp) {
		
		log.debug("Pad released = {}", pad);
		
		for (PadHandler handler : padHandler) {
			if(handler.accept(pad))
			{
				// TODO Mettre dans un autre thread
				try {
					handler.onPadReleased(pad, timestamp);
				} catch (Exception e) {
					log.warn("Handler error", e);
				}
			}
		}
	}

	@Override
	public void onButtonPressed(Button button, long timestamp) {
		log.debug("Button pressed = {}", button);
	}

	@Override
	public void onButtonReleased(Button button, long timestamp) {
		log.debug("Button released = {}", button);
	}

	@Override
	public void onTextScrolled(long timestamp) {
		log.debug("Text Scrolled");
	}
	
}
