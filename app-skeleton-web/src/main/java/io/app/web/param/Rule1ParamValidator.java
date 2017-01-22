package io.app.web.param;

import org.springframework.stereotype.Component;

@Component
public class Rule1ParamValidator implements ParamValidator {

    @Override
    public boolean supports(Class<?> clazz) {
	return clazz.equals(this.getClass());
    }

    @Override
    public void validate(Object[] target) {
	if (target != null) {
	    if (org.apache.commons.lang3.StringUtils.isNotBlank((String) target[0])) {
		throw new RuntimeException("toto");
	    }
	}
    }

}
