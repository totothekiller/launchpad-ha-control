package io.ha.launchpad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Class
 * 
 * @author pfeifft
 */
@SpringBootApplication
public class LaunchpadHAControlApplication {

	/**
	 * Run the Spring application
	 *
	 * @param args args of main method
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(LaunchpadHAControlApplication.class, args);
	}
	
}
