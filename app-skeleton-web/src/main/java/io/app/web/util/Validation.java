package io.app.web.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validation{
	
	public static class DefaultValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return true;
		}

		@Override
		public void validate(Object target, Errors errors) {
			System.out.println("default validator");
		}
		
	}
	
	String description() default "";
	
	Class<? extends Validator> validator() default DefaultValidator.class;
	
	String[] parameters() default {};
	
}