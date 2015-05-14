package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

/**
 * First name.
 */
@Entity
@Table(name="CITYNAME")
public class CityName {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private City city;

    private String string;

    /** Private constructor for Hibernate */
    private CityName() {
    }

    public CityName(City city, String string) {
        this.city = city;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
