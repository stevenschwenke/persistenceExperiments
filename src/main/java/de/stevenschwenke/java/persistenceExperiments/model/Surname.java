package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

/**
 * Last name.
 */
@Entity
@Table(name="SURNAME")
public class Surname {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Person person;

    private String string;

    /** Private constructor for Hibernate */
    private Surname() {
    }

    public Surname(Person person, String string) {
        this.person = person;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
