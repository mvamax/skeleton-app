package io.app.batch.job.migrateDataJob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author y3gdtu
 */
@Configuration
public class InitializeDataJob {
	
	public final static String incrementerParameter="run.id";

	private static final String InsertionJeuEssaiJob = "initializeDataJob";
	
	private final static String[] requiredParameters={incrementerParameter};
	private final static String[] optionalParameters={};
		
	@Autowired
	private JobBuilderFactory jobBuilders;
	
	@Autowired
	Step importDataStep;
	
	@Bean
	public Job InsertionJeuEssai(){
		return jobBuilders.get(InsertionJeuEssaiJob)
				.incrementer(new RunIdIncrementer())
				.validator(new DefaultJobParametersValidator(requiredParameters,optionalParameters))
				.start(importDataStep)
				.preventRestart()
				.build();
	}


}
