package org.faze.library.repositories;

import java.util.List;
import org.faze.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

  // найти книги, название которых начинается на title
  List<Book> findByTitleStartingWith(String title);
}
