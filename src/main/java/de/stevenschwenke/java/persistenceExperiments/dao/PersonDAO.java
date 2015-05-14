package de.stevenschwenke.java.persistenceExperiments.dao;

import java.util.List;

import de.stevenschwenke.java.persistenceExperiments.model.Person;

public interface PersonDAO {

	public void save(Person p);
	
	public List<Person> list();
	
}
