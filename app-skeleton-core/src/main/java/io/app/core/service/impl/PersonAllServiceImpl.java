package io.app.core.service.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import io.app.core.domain.PersonAllView;
import io.app.core.repository.Person1ViewRepository;
import io.app.core.repository.Person2ViewRepository;
import io.app.core.repository.PersonAllRepository;
import io.app.core.service.PersonAllService;
import io.app.core.specification.PersonAllSpecification;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonAllServiceImpl implements PersonAllService {

    PersonAllRepository personAllRepository;
    Person2ViewRepository person2ViewRepository;
    Person1ViewRepository person1ViewRepository;

    public PersonAllServiceImpl(PersonAllRepository personAllRepository, Person2ViewRepository person2ViewRepository,
	    Person1ViewRepository person1ViewRepository) {
	this.personAllRepository = personAllRepository;
	this.person2ViewRepository = person2ViewRepository;
	this.person1ViewRepository = person1ViewRepository;
    }

    @Override
    public List<? extends PersonAllView> findAllUnionWithJpa(Long type) {
	if (type != null && 1L == type) {
	    return person1ViewRepository.findAll(where(PersonAllSpecification.joins()));
	} else if (type != null && 2L == type) {
	    return person2ViewRepository.findAll(where(PersonAllSpecification.joins()));
	} else {
	    return personAllRepository.findAll(where(PersonAllSpecification.joins()));
	}
    }

    @Override
    public List<PersonAllView> findAllUnionWithJpa() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PersonAllView> findAllUnionWithQUeryDsl() {
	// TODO Auto-generated method stub
	return null;
    }

}
