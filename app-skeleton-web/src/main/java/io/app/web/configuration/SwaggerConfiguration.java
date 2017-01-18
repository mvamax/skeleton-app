package io.app.web.configuration;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SPRING_WEB)
				.forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class,
						java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class,
						Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class,
						Date.class)
						.select()
//				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
						.apis(RequestHandlerSelectors.basePackage("io.app"))
				
				.build();
	}
}