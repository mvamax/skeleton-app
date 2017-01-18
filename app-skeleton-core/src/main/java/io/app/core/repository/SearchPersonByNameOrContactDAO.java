package io.app.core.repository;

import io.app.core.domain.Person;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;

public class SearchPersonByNameOrContactDAO {

	EntityManager em;
	
	public Page<Person> searchPersonByNameOrContact(String search,Boolean b){
		
		//faire la count Query
		//faire la search QUery if count > 0
		
		return null;
	}

	private Long countQuery(String search,Boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
