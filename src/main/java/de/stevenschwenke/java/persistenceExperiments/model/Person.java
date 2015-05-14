package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
public class Person {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
	private Name name;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
	private Surname surname;

	private String country;

	/** Private constructor for Hibernate */
	private Person() {
	}

	public Person(String name, String surname) {
		setName(new Name(this,name));
		setSurname(new Surname(this, surname));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Surname getSurname() {
		return surname;
	}

	public void setSurname(Surname surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString(){
		return "id="+id+", name="+name+", country="+country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		return id == person.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
