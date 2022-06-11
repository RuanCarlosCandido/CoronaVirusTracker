package org.coronaVirusTracker.configuration;

import org.coronaVirusTracker.exceptions.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Configuration of the threads scheduling, to properly handle exceptions. It's
 * necessary because the GlobalExceptionHandler only works for the Controller
 * workflow, i.e, scheduled tasks will not be handled
 * 
 * @author ruan.lima
 *
 */
@EnableScheduling // Permission to run the application automatically at a configuration (see the
					// service)
@Configuration
public class SchedulingConfiguration implements SchedulingConfigurer {
	private final ThreadPoolTaskScheduler taskScheduler;

	SchedulingConfiguration() {
		taskScheduler = new ThreadPoolTaskScheduler();

		taskScheduler.setErrorHandler(
				throwable -> new GlobalExceptionHandler().handleAllExceptions(new Exception(throwable), null));

		taskScheduler.setThreadNamePrefix("Sheduled - ");

		taskScheduler.initialize();
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler);
	}

}
