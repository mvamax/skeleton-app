package io.app.core.specification;

import java.util.List;

import io.app.core.domain.Person;
import io.app.core.join.FetchSpecification;
import io.app.core.join.JoinDescriptor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.springframework.data.jpa.domain.Specifications.where;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PersonSearchByLastnameAndFirstnameSpecification {

	private static final String firstnameAttribut="firstname";
	private static final String lastnameAttribut="lastname";
	
	
	public static Specification<Person> spec(String lastname, String firstname,Boolean contact){
		return where(
				joins( contact)
		).and(
				contactIsNotNull(contact)
		)
		.or(  
				lastnameContainsIgnoreCase(lastname)
		)
		.or(  
				firstnameContainsIgnoreCase(firstname)
		)	;
		
	}
	
	private static Specification<Person> joins(Boolean contact) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				if (Long.class != query.getResultType()) {
					//Je suis dans la query
					if(BooleanUtils.isTrue(contact)){
						root.fetch("contact",JoinType.INNER);
					}else{
						root.fetch("contact",JoinType.LEFT);
					}
					return null;
				} else {
					if(BooleanUtils.isTrue(contact)){
						root.join("contact",JoinType.INNER);
					}
					return null;
				}
			}
		};
	}

	private static Specification<Person> contactIsNotNull(Boolean contact) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				if(BooleanUtils.isTrue(contact)){
					return cb.isNotNull(root.get("contact"));
				}else{
					return null;
				}
			}
		};
	}
	
	private static Specification<Person> firstnameContainsIgnoreCase(String searchString) {
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
	
	private static Specification<Person> lastnameContainsIgnoreCase(String searchString) {
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
