package io.app.web.param;

public interface ParamValidator {

	public boolean supports(Class<?> clazz);
		
	public void validate(Object[] target);
}
