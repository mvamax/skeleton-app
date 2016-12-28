package io.app;


import io.spring.helpers.configuration.Constants;
import io.spring.helpers.configuration.DefaultConfigurationUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBatch {

	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppBatch.class);
		app.setWebEnvironment(false);
		DefaultConfigurationUtil.addConfiguration(app, Constants.CORE_CONF, Constants.BATCH_CONF, Constants.EXTERNAL_CONF);
		app.run(args);
	}
}
