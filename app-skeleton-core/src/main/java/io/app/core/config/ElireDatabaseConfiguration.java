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
public class ElireDatabaseConfiguration{

	@Bean
	@Primary
	@ConfigurationProperties("elire.datasource")
	public BasicDatasourceProperties elireDatasourceProperties() {
		return new BasicDatasourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("elire.datasource.hikari")
	public DataSource elireDatasource(BasicDatasourceProperties elireDatasourceProperties) {
		DataSource d = elireDatasourceProperties.initializeDataSourceBuilder().build();
		return d;
	}
	
	@Bean
	@Primary
	public DataSourceInitializer elireDataSourceInitializer(DataSource elireDatasource, BasicDatasourceProperties elireDatasourceProperties) {
		return new DataSourceInitializer(elireDatasource,elireDatasourceProperties);
	}
	


}
