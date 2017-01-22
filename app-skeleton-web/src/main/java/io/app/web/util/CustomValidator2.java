package io.app.web.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomValidator2 implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
	return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
	System.out.println("i custom validate2!!!!");
    }
}