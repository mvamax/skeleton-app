package io.app.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Bean
	public AppJobLauncher elireJobLauncher(JobLauncher jobLauncher, JobExplorer jobExplorer) {
		AppJobLauncher runner = new AppJobLauncher(jobLauncher, jobExplorer);
		return runner;
	}

}
