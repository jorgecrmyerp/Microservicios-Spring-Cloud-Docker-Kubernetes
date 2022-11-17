package com.app.spring.gateway.server;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {
	 private Environment environment;

	    public void getActiveProfiles() {
	        for (String profileName : environment.getActiveProfiles()) {
	            System.out.println("**************************Currently active profile - " + profileName);
	        }  
	    }

}
