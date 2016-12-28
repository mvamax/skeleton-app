package io.app.core.service.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import io.app.core.domain.Person;
import io.app.core.join.FetchSpecification;
import io.app.core.join.JoinDescriptor;
import io.app.core.repository.PersonRepository;
import io.app.core.service.PersonService;
import io.app.core.specification.PersonSpecification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

	private PersonRepository personRepository;
	
	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository=personRepository;
	}
	
	public Page<Person> searchByLastnameAndFirstname(String searchString,List<JoinDescriptor> joinFields,Pageable pageable) {
		return  personRepository.findAll(
				where(
						FetchSpecification.<Person> withEagerFetch(joinFields)
				).or(  
						PersonSpecification.lastnameContainsIgnoreCase(searchString)
				).or(  
						PersonSpecification.firstnameContainsIgnoreCase(searchString)
				)		
				,pageable);
	}

}
