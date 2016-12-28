package io.app.core.join;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class FetchSpecification<T> {

	public static <T> Specification<T> withEagerFetch(
			List<JoinDescriptor> joinFields) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if (Long.class != query.getResultType()) {
					joinFields.forEach(jd -> root.fetch(jd.getAttributeName(),
							jd.getJoinType()));
					return null;
				} else {
					return null;
				}
			}
		};
	}
}
