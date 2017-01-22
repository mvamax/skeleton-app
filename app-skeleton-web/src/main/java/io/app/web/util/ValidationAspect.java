package io.app.web.util;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Aspect
@Component
public class ValidationAspect implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Transactional
	private void validate(Validation[] validations){
//		for(Validation v :validations){
//			(Validator) v=applicationContext.getBean(v.validator());
//			ReflectionUtils.invokeMethod("validate", v, )
//		}
	}
}
