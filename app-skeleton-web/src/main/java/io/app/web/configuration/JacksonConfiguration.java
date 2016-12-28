package io.app.web.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration implements
		Jackson2ObjectMapperBuilderCustomizer {

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonBuilder) {
		jacksonBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		jacksonBuilder.serializationInclusion(Include.NON_EMPTY);
	}
	
	//Evite de ramener les proxy hibernate
	@Bean
	public Hibernate4Module hibernate4Module() {
		return new Hibernate4Module();
	}
	
	//Se charge des serializations ZonedDateTime ou LocalDateTime java 8
	@Bean
	public JavaTimeModule javaTimeModule() {
		return new JavaTimeModule();
	}
}
