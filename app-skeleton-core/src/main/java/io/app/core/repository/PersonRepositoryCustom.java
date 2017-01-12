package io.app.core.repository;

import io.app.core.domain.Person;

import java.util.stream.Stream;

import org.hibernate.ScrollableResults;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface PersonRepositoryCustom {

	Stream<Person> fetch(Specification<Person> spec,Sort s);
	
	ScrollableResults fetchHibernate();
}
