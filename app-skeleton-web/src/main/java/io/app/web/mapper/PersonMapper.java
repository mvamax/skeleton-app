package io.app.web.mapper;

import io.app.core.domain.Person;
import io.app.web.view.PersonView;

import org.springframework.stereotype.Component;

@Component
public class PersonMapper extends AbstractMapper<PersonView, Person> {

    @Override
    public PersonView toView(Person s) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Person toEntity(PersonView t) {
	// TODO Auto-generated method stub
	return null;
    }

}
