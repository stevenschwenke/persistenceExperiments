package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;
import java.util.Set;

/**
 * A hobby.
 */
@Entity
@Table(name="HOBBY")
public class Hobby {

    @Id
    @Column(name="hobbyId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int hobbyId;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Person> persons;

    private String string;

    /** Private constructor for Hibernate */
    private Hobby() {
    }

    public Hobby(Set<Person> persons, String string) {
        this.persons = persons;
        this.string = string;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
