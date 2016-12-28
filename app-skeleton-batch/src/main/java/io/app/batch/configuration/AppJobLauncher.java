package io.app.batch.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

public class AppJobLauncher implements CommandLineRunner {

	private static final Logger log = LoggerFactory
			.getLogger(AppJobLauncher.class);

	public final static String jobNameParameter = "job";

	private JobParametersConverter converter = new DefaultJobParametersConverter();

	private JobLauncher jobLauncher;

	private JobExplorer jobExplorer;

	private String jobName;

	private ApplicationEventPublisher publisher;

	private Collection<Job> jobs;

	public AppJobLauncher(JobLauncher jobLauncher, JobExplorer jobExplorer) {
		 this.jobLauncher=jobLauncher;
		 this.jobExplorer=jobExplorer;
	}

	public void setJobName(String jobNames) {
		this.jobName = jobNames;
	}

	public void setJobParametersConverter(JobParametersConverter converter) {
		this.converter = converter;
	}

	@Autowired
	public void setJobs(Collection<Job> jobs) {
		this.jobs = jobs;
	}

	public void run(String... args) throws JobExecutionException {
		log.info("ElireRunner execution with: " + Arrays.asList(args));
		Properties props = StringUtils.splitArrayElementsIntoProperties(args,"=");
		setJobNameAndLaunchJob(props);
	}

	private void setJobNameAndLaunchJob(Properties props)
			throws JobExecutionException {
		if (props!=null && props.containsKey(jobNameParameter) ) {
			setJobName(props.getProperty(jobNameParameter));
			props.remove(jobNameParameter);
			log.info("soumission du job {} avec les propriétés {}",this.jobName, props);
			this.launchJobFromProperties(props);
		} else {
			log.error("no {} in commandLine", jobNameParameter);
		}
	}

	protected void launchJobFromProperties(Properties properties)
			throws JobExecutionException {
		log.info("conversions des paramètres");
		JobParameters jobParameters = this.converter.getJobParameters(properties);
		log.info(jobParameters.toString());
		executeJob(jobParameters);
	}

	private JobParameters getNextJobParameters(Job job,
			JobParameters additionalParameters) {
		String name = job.getName();
		log.info(name);
		JobParameters parameters = new JobParameters();
		List<JobInstance> lastInstances = this.jobExplorer.getJobInstances(name, 0, 1);
		JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
		Map<String, JobParameter> additionals = additionalParameters.getParameters();
		if (lastInstances.isEmpty()) {
			log.debug("pas d'ancienne instance de ce job");
			// Start from a completely clean sheet
			if (incrementer != null) {
				parameters = incrementer.getNext(new JobParameters());
			}
		} else {
			log.debug("ancienne instance de ce job détectée");
			List<JobExecution> previousExecutions = this.jobExplorer
					.getJobExecutions(lastInstances.get(0));
			JobExecution previousExecution = previousExecutions.get(0);
			if (previousExecution == null) {
				// Normally this will not happen - an instance exists with no
				// executions
				if (incrementer != null) {
					parameters = incrementer.getNext(new JobParameters());
				}
			} else if (isStoppedOrFailed(previousExecution)
					&& job.isRestartable()) {
				log.debug("retry de l'ancienne instance");
				// Retry a failed or stopped execution
				parameters = previousExecution.getJobParameters();
				// Non-identifying additional parameters can be removed to a
				// retry
				removeNonIdentifying(additionals);
			} else if (incrementer != null) {
				log.debug("récupération des nextParameters");
				// New instance so increment the parameters if we can
				JobParameters previousParameters = previousExecution.getJobParameters();
				JobParameters newParameters = incrementer.getNext(previousExecution.getJobParameters());
				for( String paramName  :newParameters.getParameters().keySet()){
					//On filtre pour ne garder que les paramètres incrementer
					if(!newParameters.getParameters().get(paramName).getValue().equals(previousParameters.getParameters().get(paramName).getValue())){
						parameters = new JobParametersBuilder(parameters).addParameter(paramName, newParameters.getParameters().get(paramName)).toJobParameters();
					}
				}
			}
		}
		log.debug("incrementer {}",parameters.toString());
		log.debug("additionals {}",additionals.toString());
		return merge(parameters, additionals);
	}

	private boolean isStoppedOrFailed(JobExecution execution) {
		BatchStatus status = execution.getStatus();
		return (status == BatchStatus.STOPPED || status == BatchStatus.FAILED);
	}

	private void removeNonIdentifying(Map<String, JobParameter> parameters) {
		HashMap<String, JobParameter> copy = new HashMap<String, JobParameter>(
				parameters);
		for (Map.Entry<String, JobParameter> parameter : copy.entrySet()) {
			if (!parameter.getValue().isIdentifying()) {
				parameters.remove(parameter.getKey());
			}
		}
	}

	private JobParameters merge(JobParameters parameters,
			Map<String, JobParameter> additionals) {
		Map<String, JobParameter> merged = new HashMap<String, JobParameter>();
		merged.putAll(parameters.getParameters());
		merged.putAll(additionals);
		parameters = new JobParameters(merged);
		return parameters;
	}

	private void executeJob(JobParameters jobParameters)
			throws JobExecutionException {
		this.jobs.forEach(j -> System.out.println(j.getName()));
		Optional<Job> job = this.jobs.stream()
				.filter(j -> j.getName().equals(this.jobName)).findFirst();
		if (job.isPresent()) {
			log.info(job.get().toString());
			this.execute(job.get(), jobParameters);
		} else {
			log.error("no job for {}", this.jobName);
		}
	}

	protected void execute(Job job, JobParameters jobParameters)
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException,
			JobParametersNotFoundException {
		log.info("*******EXECUTION*******");
		JobParameters nextParameters = getNextJobParameters(job, jobParameters);
		log.info("nextParameters" + nextParameters);
		if (nextParameters != null) {
			JobExecution execution = this.jobLauncher.run(job, nextParameters);
			if (this.publisher != null) {
				this.publisher.publishEvent(new JobExecutionEvent(execution));
			}
		}
	}

}