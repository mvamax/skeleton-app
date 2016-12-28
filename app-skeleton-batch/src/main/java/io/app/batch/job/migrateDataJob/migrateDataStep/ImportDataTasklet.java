package io.app.batch.job.migrateDataJob.migrateDataStep;

import io.app.external.service.ExternalDataService;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;



public class ImportDataTasklet implements Tasklet {
	
	private ExternalDataService externalDataService;
	
	public ImportDataTasklet(ExternalDataService externalDataService) {
		this.externalDataService=externalDataService;
	}
	
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext context )
			throws Exception {
		externalDataService.listExternalData().stream().forEach( ed -> System.out.println(ed));
		return RepeatStatus.FINISHED;
	}


}
