package io.app.core.repository;

import io.app.core.domain.Person;

import java.util.stream.Stream;

import org.hibernate.ScrollableResults;

public interface PersonRepositoryCustom {

	Stream<Person> fetch();
	
	ScrollableResults fetchHibernate();
}
