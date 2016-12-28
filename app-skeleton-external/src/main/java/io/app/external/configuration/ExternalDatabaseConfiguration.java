package io.app.external.configuration;

import io.spring.helpers.configuration.BasicDatasourceProperties;
import io.spring.helpers.configuration.DataSourceInitializer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ExternalDatabaseConfiguration  {

	@Bean
	@ConfigurationProperties("external.datasource")
	@Qualifier("externalDatasourceProperties")
	public BasicDatasourceProperties externalDatasourceProperties() {
		return new BasicDatasourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "external.datasource.hikari")
	@Qualifier("externalDatasource")
	public DataSource externalDatasource(@Qualifier("externalDatasourceProperties")
			BasicDatasourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}
	
	@Bean
	public DataSourceInitializer externalDataSourceInitializer(
			@Qualifier("externalDatasource") DataSource externalDatasource,
			@Qualifier("externalDatasourceProperties") BasicDatasourceProperties externalDatasourceProperties) {
		return new DataSourceInitializer(externalDatasource,externalDatasourceProperties);
	}
	

}
