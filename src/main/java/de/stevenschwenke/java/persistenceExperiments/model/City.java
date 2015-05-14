package de.stevenschwenke.java.persistenceExperiments.model;

import javax.persistence.*;

/**
 * A city.
 */
@Entity
@Table(name = "CITY")
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "city", cascade = CascadeType.ALL)
    private CityName name;

    /** Default constructor for Hibernate */
    private City() {
    }

    public City(String name) {
        this.name = new CityName(this, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public CityName getName() {
        return name;
    }

    public void setName(CityName name) {
        this.name = name;
    }
}
