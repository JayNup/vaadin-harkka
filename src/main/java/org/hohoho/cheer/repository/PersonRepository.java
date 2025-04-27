package org.hohoho.cheer.repository;

import org.hohoho.cheer.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
