package org.hohoho.cheer.model;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String katuosoite;
    private String postinumero;
    private String kaupunki;

    public Address() {
    }
    @OneToOne(mappedBy = "address")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getPostinumero() {
        return postinumero;
    }
    public Address(String address) {
        // Alusta Address-luokan kentät tässä
        this.katuosoite = address; // Korvaa "someField" oikealla kentällä
    }
    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }
    public boolean contains(String city) {
        return this.kaupunki.contains(city); // Korvaa cityName oikealla kentällä
    }
    public String getKaupunki() {
        return kaupunki;
    }

    public void setKaupunki(String kaupunki) {
        this.kaupunki = kaupunki;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
