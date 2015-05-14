package de.stevenschwenke.java.persistenceExperiments.dao;

import java.util.List;

import de.stevenschwenke.java.persistenceExperiments.model.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import de.stevenschwenke.java.persistenceExperiments.model.Person;

public class PersonDAOImpl implements PersonDAO {

	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void save(Person p) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
		session.close();
	}

	public void save(Object o) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(o);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Person> list() {
		Session session = this.sessionFactory.openSession();
		List<Person> persons = session.createQuery("from Person").list();
		session.close();
		return persons;
	}

	public List<City> getCities() {
		Session session = this.sessionFactory.openSession();
		List<City> cities = session.createQuery("from City").list();
		session.close();
		return cities;
	}

}
