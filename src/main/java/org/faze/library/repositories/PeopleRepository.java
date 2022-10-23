package org.faze.library.repositories;

import java.util.Optional;
import org.faze.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

  Optional<Person> findByFullName(String fullName);
}
