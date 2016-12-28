package io.app.core.service;

import java.util.List;

import io.app.core.domain.Person;
import io.app.core.join.JoinDescriptor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

	Page<Person> searchByLastnameAndFirstname(String searchString,List<JoinDescriptor> joinFields, Pageable pageable);
}
