package io.app.core.specification;

import io.app.core.domain.PersonAllView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class PersonAllSpecification {

    public static <T extends PersonAllView> Specification<T> joins() {
	return new Specification<T>() {
	    @Override
	    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (Long.class != query.getResultType()) {
		    // Je suis dans la query
		    root.fetch("contact", JoinType.LEFT);
		    return null;
		} else {
		    root.join("contact", JoinType.INNER);
		    return null;
		}
	    }
	};
    }
}
