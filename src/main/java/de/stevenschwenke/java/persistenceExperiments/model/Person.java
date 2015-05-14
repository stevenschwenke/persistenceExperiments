package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
public class Person {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
	private Name name;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
	private Surname surname;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PERSON_ID")
	private City location;

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

	public City getLocation() {
		return location;
	}

	public void setLocation(City location) {
		this.location = location;
	}

	@Override
	public String toString(){
		return "id="+id+", name="+name;
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
