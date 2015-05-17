package de.stevenschwenke.java.persistenceExperiments.dao;

import java.util.List;

import de.stevenschwenke.java.persistenceExperiments.model.City;
import de.stevenschwenke.java.persistenceExperiments.model.Person;

public interface DAO {

	public void save(Object p);

	public void save(Person p);
	
	public List<Person> loadDeep();

	public List<City> getCities();
}
