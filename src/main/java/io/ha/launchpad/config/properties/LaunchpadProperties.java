package io.ha.launchpad.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "launchpad")
public class LaunchpadProperties {

	private List<PadProperties> pads;
	
}
