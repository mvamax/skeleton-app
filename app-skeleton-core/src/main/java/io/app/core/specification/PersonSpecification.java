package io.app.core.specification;

import io.app.core.domain.Person;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {

	private static final String firstnameAttribut="firstname";
	private static final String lastnameAttribut="lastname";
	
	public static Specification<Person> firstnameContainsIgnoreCase(String searchString) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				if(StringUtils.isNotBlank(searchString)){
					return cb.like(cb.lower(root.get(firstnameAttribut)),"%"+searchString.toLowerCase()+"%");
				}else{
					return null;
				}
			}
		};
	}
	
	public static Specification<Person> lastnameContainsIgnoreCase(String searchString) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				if(StringUtils.isNotBlank(searchString)){
					return cb.like(cb.lower(root.get(lastnameAttribut)),"%"+searchString.toLowerCase()+"%");
				}else{
					return null;
				}
			}
		};
	}
	
}
