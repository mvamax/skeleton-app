package io.app.core.config;

import java.sql.Connection;

import javax.inject.Provider;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;

@Configuration
public class QueryDslConfiguration {

    @Autowired
    DataSource datasource;

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {

	final SQLTemplates templates = PostgreSQLTemplates.builder().build(); // change
	// to your
	// Templates
	final com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
	configuration.setExceptionTranslator(new SpringExceptionTranslator());
	return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {
	final Provider<Connection> provider = new SpringConnectionProvider(datasource);
	return new SQLQueryFactory(querydslConfiguration(), provider);
    }

}
