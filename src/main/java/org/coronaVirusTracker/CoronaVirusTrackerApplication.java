package org.coronaVirusTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //Permission to run the application automatically at a configuration (see the service)
public class CoronaVirusTrackerApplication {  

	public static void main(String[] args) {		
		SpringApplication.run(CoronaVirusTrackerApplication.class, args);
	}

}
