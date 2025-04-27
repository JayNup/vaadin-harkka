package org.hohoho.cheer.controller;

import org.hohoho.cheer.model.Person;
import org.hohoho.cheer.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("personControllerService")
class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findFiltered(String name, String city) {
        return personRepository.findAll().stream()
                .filter(p -> (name == null || p.getNimi().toLowerCase().contains(name.toLowerCase())))
                .filter(p -> (city == null ||
                        (p.getAddress() != null && p.getAddress().getKaupunki() != null &&
                                p.getAddress().getKaupunki().toLowerCase().contains(city.toLowerCase()))
                ))
                .collect(Collectors.toList());
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
