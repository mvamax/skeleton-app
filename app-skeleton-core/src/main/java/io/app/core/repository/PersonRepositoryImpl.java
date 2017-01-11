package io.app.core.repository;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;
import io.app.core.domain.Person;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.util.CloseableIterator;
import org.springframework.data.util.StreamUtils;

public class PersonRepositoryImpl implements PersonRepositoryCustom {
	

	@PersistenceContext
	EntityManager em;
	
	public Stream<Person> fetch(){
//    	Query jpaQuery = query.createQuery(values);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> query = cb.createQuery(Person.class); 
		Root<Person> root = query.from(Person.class); 
		TypedQuery<Person> q = em.createQuery(query);
		q.setHint(HINT_FETCH_SIZE, "10");
		PersistenceProvider persistenceProvider = PersistenceProvider.fromEntityManager(em);
		CloseableIterator<Object> iter =  persistenceProvider.executeQueryWithResultStream(q);
		return StreamUtils.createStreamFromIterator(iter).map( o -> (Person) o);
	}
	
	public ScrollableResults fetchHibernate(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> query = cb.createQuery(Person.class); 
		Root<Person> root = query.from(Person.class); 
		//predicate
		TypedQuery<Person> q = em.createQuery(query);
		org.hibernate.Query hibernateQuery = q.unwrap(org.hibernate.Query.class);
		
		Session session = em.unwrap(Session.class);
		StatelessSession s = session.getSessionFactory().openStatelessSession();
		Criteria c = s.createCriteria(Person.class);
		
//		org.hibernate.Query q=s.createQuery("select p from Person p left join fetch p.contact as contact");
		hibernateQuery.setFetchSize(10);
		hibernateQuery.setReadOnly(true);
		ScrollableResults persons = hibernateQuery.scroll(ScrollMode.FORWARD_ONLY);
		return persons;
	}
}