package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

/**
 * First name.
 */
@Entity
@Table(name="NAME")
public class Name {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Person person;

    private String string;

    /** Private constructor for Hibernate */
    private Name() {
    }

    public Name(Person person, String string) {
        this.person = person;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
