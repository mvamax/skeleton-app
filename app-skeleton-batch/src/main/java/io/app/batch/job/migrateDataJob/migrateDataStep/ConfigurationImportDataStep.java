package io.app.batch.job.migrateDataJob.migrateDataStep;

import io.app.external.service.ExternalDataService;
import io.app.external.service.impl.ExternalDataServiceImpl;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationImportDataStep {


	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Autowired
	ExternalDataService externalDataService;
	
	@Bean
	public TaskletStep importDataStep(){
		ImportDataTasklet tasklet = new ImportDataTasklet(externalDataService);
		return stepBuilders.get("importDataStep").tasklet(tasklet).build();
	}
	
}
