package org.faze.library.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.faze.library.models.Book;
import org.faze.library.models.Person;
import org.faze.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BooksService {

  private final BooksRepository booksRepository;

  @Autowired
  public BooksService(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  public List<Book> findAll(boolean sortByYear) {
    if (sortByYear) {
      return booksRepository.findAll(Sort.by("year"));
    } else {
      return booksRepository.findAll();
    }
  }

  public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
    if (sortByYear) {
      return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year")))
          .getContent();
    } else {
      return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
  }

  @Transactional
  public void save(Book book) {
    booksRepository.save(book);
  }

  @Transactional
  public void update(int id, Book updatedBook) {
    Book bookToBeUpdated = booksRepository.findById(id).get();

    // добавляем по сути новую книгу (которая не находится в Persistence context Hibernate'а),
    // поэтому нужен save()
    updatedBook.setId(id);
    // Связь двусторонняя, поэтому чтобы не терялась связь при обновлении:
    updatedBook.setOwner(bookToBeUpdated.getOwner());

    booksRepository.save(updatedBook);
  }

  @Transactional
  public void delete(int id) {
    booksRepository.deleteById(id);
  }

  @Transactional
  public void release(int id) {
    booksRepository.findById(id).ifPresent(
        book -> {
          book.setOwner(null);
          book.setDateOfReceipt(null);
        });
  }

  @Transactional
  public void assign(int id, Person selectedPerson) {
    booksRepository.findById(id).ifPresent(
        book -> {
          book.setOwner(selectedPerson);
          // текущее время
          book.setDateOfReceipt(new Date());
        }
    );
  }

  public Book findOne(int id) {
    Optional<Book> foundBook = booksRepository.findById(id);
    return foundBook.orElse(null);
  }

  public List<Book> searchByTitle(String query) {
    return booksRepository.findByTitleStartingWith(query);
  }

  // Вернет null, если книга не занята
  public Person getBookOwner(int id) {
    // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
    return booksRepository.findById(id).map(Book::getOwner).orElse(null);
  }
}
