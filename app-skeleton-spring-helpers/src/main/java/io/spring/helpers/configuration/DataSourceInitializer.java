package io.spring.helpers.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;

public class DataSourceInitializer implements InitializingBean,		ApplicationListener<DataSourceInitializedEvent> {

	private static final Log logger = LogFactory
			.getLog(DataSourceInitializer.class);

	@Autowired
	private ConfigurableApplicationContext applicationContext;

	private DataSource dataSource;

	private BasicDatasourceProperties properties;

	private boolean initialized = false;

	public void afterPropertiesSet() throws Exception {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running DDL scripts)");
			return;
		}
		runSchemaScripts();
	}
	
	public DataSourceInitializer(DataSource dataSource , BasicDatasourceProperties basicDatasourceProperties){
		this.dataSource = dataSource;
		this.properties=basicDatasourceProperties;
	}

	private void runSchemaScripts() {
		List<Resource> scripts = getScripts("{customDatasource}.datasource.schema",
				this.properties.getSchema(), "schema");
		if (!scripts.isEmpty()) {
			String username = this.properties.getUsername();
			String password = this.properties.getPassword();
			runScripts(scripts, username, password);
			try {
				this.applicationContext
						.publishEvent(new DataSourceInitializedEvent(
								this.dataSource));
				// The listener might not be registered yet, so don't rely on
				// it.
				if (!this.initialized) {
					runDataScripts();
					this.initialized = true;
				}
			} catch (IllegalStateException ex) {
				logger.warn("Could not send event to complete DataSource initialization ("
						+ ex.getMessage() + ")");
			}
		}
	}

	public void onApplicationEvent(DataSourceInitializedEvent event) {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running data scripts)");
			return;
		}
		// NOTE the event can happen more than once and
		// the event datasource is not used here
		if (!this.initialized) {
			runDataScripts();
			this.initialized = true;
		}
	}

	private void runDataScripts() {
		List<Resource> scripts = getScripts("{customDatasource}.datasource.data",
				this.properties.getData(), "data");
		String username = this.properties.getUsername();
		String password = this.properties.getPassword();
		runScripts(scripts, username, password);
	}

	private List<Resource> getScripts(String propertyName,
			List<String> resources, String fallback) {
		if (resources != null) {
			return getResources(propertyName, resources, true);
		}
		String platform = this.properties.getPlatform();
		List<String> fallbackResources = new ArrayList<String>();
		fallbackResources.add("classpath*:" + fallback + "-" + platform
				+ ".sql");
		fallbackResources.add("classpath*:" + fallback + ".sql");
		return getResources(propertyName, fallbackResources, false);
	}

	private List<Resource> getResources(String propertyName,
			List<String> locations, boolean validate) {
		List<Resource> resources = new ArrayList<Resource>();
		for (String location : locations) {
			for (Resource resource : doGetResources(location)) {
				if (resource.exists()) {
					resources.add(resource);
				} else if (validate) {
					throw new RuntimeException(propertyName +","+ resource);
				}
			}
		}
		return resources;
	}

	private Resource[] doGetResources(String location) {
		try {
			SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
					this.applicationContext,
					Collections.singletonList(location));
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to load resources from "
					+ location, ex);
		}
	}

	private void runScripts(List<Resource> resources, String username,
			String password) {
		if (resources.isEmpty()) {
			return;
		}
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setContinueOnError(this.properties.isContinueOnError());
		populator.setSeparator(this.properties.getSeparator());
		populator.setSqlScriptEncoding("utf8");
		for (Resource resource : resources) {
			populator.addScript(resource);
		}
		DataSource dataSource = this.dataSource;
		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
			dataSource = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.determineDriverClassName())
					.url(this.properties.determineUrl()).username(username)
					.password(password).build();
		}
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

}