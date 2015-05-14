package de.stevenschwenke.java.persistenceExperiments.dao;

import java.util.List;

import de.stevenschwenke.java.persistenceExperiments.model.City;
import de.stevenschwenke.java.persistenceExperiments.model.Person;

public interface PersonDAO {

	public void save(Object p);
	
	public List<Person> list();

	public List<City> getCities();
}
