package org.hohoho.cheer.service;

import org.hohoho.cheer.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("personServiceImpl")
public class PersonService {

    // Example method to filter Person entities
    public List<Person> findFiltered(String name, String city) {
        List<Person> persons = findAll(); // Method to fetch all persons from the database

        return persons.stream()
                .filter(p -> (name == null || p.getNimi().contains(name)) &&
                        (city == null || p.getAddress().contains(city)))
                .collect(Collectors.toList());
    }

    public List<Person> findAll() {
        // Normally, you'd fetch this from a database, e.g., using JPA repository
        return List.of(
                new Person("Alice", "New York"),
                new Person("Bob", "London"),
                new Person("Charlie", "New York")
        );
    }
}
