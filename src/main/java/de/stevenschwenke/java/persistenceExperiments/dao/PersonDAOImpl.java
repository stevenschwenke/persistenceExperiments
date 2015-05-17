package de.stevenschwenke.java.persistenceExperiments.dao;

import java.util.List;
import java.util.Set;

import de.stevenschwenke.java.persistenceExperiments.model.City;
import de.stevenschwenke.java.persistenceExperiments.model.Hobby;
import org.hibernate.FetchMode;
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
		for(Hobby h: p.getHobbies()) {
			session.persist(h);
		}
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
	public List<Person> loadDeep() {
		Session session = this.sessionFactory.openSession();
		List<Person> persons = session.createCriteria(Person.class).setFetchMode("hobbies", FetchMode.JOIN).list();
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
