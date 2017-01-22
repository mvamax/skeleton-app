package io.app.web.response;


import io.app.core.domain.Person;
import io.app.web.param.ParamValidatorExecutor;

@Response
public class PersonPageableResponse extends SimplePageableResponse<Person>{

    public PersonPageableResponse(ParamValidatorExecutor executor) {
	super(executor);
    }
}


