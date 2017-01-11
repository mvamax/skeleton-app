package io.app.core.service.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import io.app.core.domain.Person;
import io.app.core.join.FetchSpecification;
import io.app.core.join.JoinDescriptor;
import io.app.core.repository.PersonRepository;
import io.app.core.service.PersonService;
import io.app.core.specification.PersonSpecification;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly=true)
	public String fetch( StringBuilder sb) {
		// TODO Auto-generated method stub
		personRepository.fetch().forEach(o -> {
			sb.append(o.getFirstname().toString());
		});
		return sb.toString();
	}

	@Transactional(readOnly=true)
	public String fetchHibernate(StringBuilder sb) {
		ScrollableResults persons = personRepository.fetchHibernate();
		try{
		while(persons.next()){
			sb.append(((Person)persons.get()[0]).getContact()+",");
		}}finally{
			persons.close();
		}
		return sb.toString();
	}

}
