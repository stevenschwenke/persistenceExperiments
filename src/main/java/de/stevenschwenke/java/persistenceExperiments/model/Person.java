package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="PERSON")
public class Person {

	@Id
	@Column(name="personId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int personId;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
	private Name name;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
	private Surname surname;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PERSON_ID")
	private City location;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "person_hobby", joinColumns = {
			@JoinColumn(name = "personId") },
			inverseJoinColumns = { @JoinColumn(name = "hobbyId") })
	private Set<Hobby> hobbies = new HashSet<Hobby>();

	/** Private constructor for Hibernate */
	private Person() {
	}

	public Person(String name, String surname) {
		setName(new Name(this,name));
		setSurname(new Surname(this, surname));
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
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

	public Set<Hobby> getHobbies() {
		return hobbies;
	}

	public void setHobbies(Set<Hobby> hobbies) {
		this.hobbies = hobbies;
	}

	public void addHobby(Hobby hobby) {
		this.hobbies.add(hobby);
	}

	@Override
	public String toString(){
		return "personId="+ personId +", name="+name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		return personId == person.personId;

	}

	@Override
	public int hashCode() {
		return personId;
	}
}
