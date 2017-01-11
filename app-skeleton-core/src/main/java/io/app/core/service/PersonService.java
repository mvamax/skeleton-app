package io.app.core.service;

import io.app.core.domain.Person;
import io.app.core.join.JoinDescriptor;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

	Page<Person> searchByLastnameAndFirstname(String searchString,List<JoinDescriptor> joinFields, Pageable pageable);

	String fetch(StringBuilder sb);
	
	String fetchHibernate(StringBuilder sb);
}
