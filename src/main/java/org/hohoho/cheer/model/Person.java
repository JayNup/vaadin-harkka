package org.hohoho.cheer.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Person {
    public Person() {
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public LocalDate getSyntymaaika() {
        return syntymaaika;
    }

    public void setSyntymaaika(LocalDate syntymaaika) {
        this.syntymaaika = syntymaaika;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nimi;
    private LocalDate syntymaaika;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public Person(String name, String address) {
        this.nimi = nimi;
        this.address = new Address(address); // Jos Address on oma luokka
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    @ManyToMany
    @JoinTable(
            name = "person_hobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<Hobby> hobbies = new HashSet<>();

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Column(name = "age")
    private int age;


}
