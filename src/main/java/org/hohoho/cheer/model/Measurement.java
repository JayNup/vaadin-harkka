package org.hohoho.cheer.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double weight;
    private String bloodPressure;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
}
