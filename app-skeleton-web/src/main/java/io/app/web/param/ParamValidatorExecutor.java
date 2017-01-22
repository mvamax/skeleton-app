package io.app.web.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ParamValidatorExecutor {

	@Autowired
	List<ParamValidator> validators;
	
	private Map<Class<?>,ParamValidator> mapValidators = new HashMap<Class<?>, ParamValidator>();
	
//	@Transactional
//	public void validate(List<Param> params){
//		for(Param param : params){
//			for(ParamValidator paramValidator : validators){
//				if(paramValidator.supports(param.getClazz())){
//					paramValidator.validate(param.getParams());
//				}
//			}
//		}
//	}
//	

	@Transactional
	public void validate(List<Param> params){
		for(Param param : params){
			mapValidators.get(param.getClazz()).validate(param.getParams());
		}
	}
	
	@PostConstruct
	private void listToMap(){
		validators.forEach(v -> mapValidators.put(v.getClass(), v));
	}
}
