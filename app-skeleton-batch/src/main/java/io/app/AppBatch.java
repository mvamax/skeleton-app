package io.app;


import io.app.core.config.Constants;
import io.app.core.config.DefaultConfigurationUtil;

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
