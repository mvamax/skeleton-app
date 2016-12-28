package io.app.core.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class UseExistingOrGenerateSequenceGenerator extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		 Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
		 return id != null ? id : super.generate(session, object);
	}
}