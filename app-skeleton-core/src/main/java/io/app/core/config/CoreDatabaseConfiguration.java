package io.app.core.config;

import io.spring.helpers.configuration.BasicDatasourceProperties;
import io.spring.helpers.configuration.DataSourceInitializer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class CoreDatabaseConfiguration{

	@Bean
	@Primary
	@ConfigurationProperties("core.datasource")
	public BasicDatasourceProperties coreDatasourceProperties() {
		return new BasicDatasourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("core.datasource.hikari")
	public DataSource coreDatasource(BasicDatasourceProperties coreDatasourceProperties) {
		DataSource d = coreDatasourceProperties.initializeDataSourceBuilder().build();
		return d;
	}
	
	@Bean
	@Primary
	public DataSourceInitializer coreDataSourceInitializer(DataSource coreDatasource, BasicDatasourceProperties coreDatasourceProperties) {
		return new DataSourceInitializer(coreDatasource,coreDatasourceProperties);
	}
	


}
