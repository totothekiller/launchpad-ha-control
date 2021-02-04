package io.ha.launchpad.config;

import javax.sound.midi.MidiUnavailableException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

@Configuration
@Profile("launchpad")
public class LaunchpadConfig {
	
	@Bean(destroyMethod = "close")
	public Launchpad connectMidiLaunchpad() throws MidiUnavailableException
	{
        MidiDeviceConfiguration device = MidiDeviceConfiguration.autodetect();
        
        if(device == null)
        {
        	throw new IllegalArgumentException("Impossible to find Launchpad...");
        }
        
		return new MidiLaunchpad(device);
	}
	
	@Bean(initMethod = "reset")
	public LaunchpadClient createMidiClient(Launchpad launchpad)
	{
		return launchpad.getClient();
	}
	
}
