package org.faze.library.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.faze.library.models.Book;
import org.faze.library.models.Person;
import org.faze.library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

  PeopleRepository peopleRepository;

  @Autowired
  public PeopleService(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
  }

  public List<Person> findAll() {
    return peopleRepository.findAll();
  }

  public Person findOne(int id) {
    Optional<Person> foundPerson = peopleRepository.findById(id);
    return foundPerson.orElse(null);
  }

  @Transactional
  public void update(int id, Person updatedPerson) {
    updatedPerson.setId(id);
    peopleRepository.save(updatedPerson);
  }

  @Transactional
  public void delete(int id) {
    peopleRepository.deleteById(id);
  }

  @Transactional
  public void save(Person person) {
    peopleRepository.save(person);
  }

  public List<Book> getBooksByPersonId(int id) {
    Optional<Person> person = peopleRepository.findById(id);

    if (person.isPresent()) {
      Hibernate.initialize(person.get().getBooks());

      // Проверка - книга у читателя больше 10 дней?
      person.get().getBooks().forEach(book -> {
        long diffInMillies = Math.abs(book.getDateOfReceipt().getTime() - new Date().getTime());
        // 864000000 милисекунд = 10 суток = 10*24*60*60*1000
        if (diffInMillies > 864000000) {
          book.setExpired(true); // книга просрочена
        }
      });

      return person.get().getBooks();
    } else {
      return Collections.emptyList();
    }
  }

  public Optional<Person> getPersonByFullName(String fullName) {
    return peopleRepository.findByFullName(fullName);
  }
}
