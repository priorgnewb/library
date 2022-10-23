package org.faze.library.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  @NotEmpty(message = "Название книги не указано")
  @Size(min = 2, max = 100, message = "Название книги должно быть от 1 до 100 символов")
  private String title;

  @Column(name = "author")
  @NotEmpty(message = "Автор книги не указан")
  @Size(min = 2, max = 100, message = "Автор должен быть от 1 до 100 символов")
  private String author;

  @Column(name = "year")
  @Min(value = 1500, message = "Год издания книги должен быть не менее 1500")
  private int year;

  // дата выдачи книги читателю
  @Column(name = "date_of_receipt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateOfReceipt;

  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person owner;

  @Transient
  private boolean expired;

  public Book() {
  }

  public Book(String title, String author, int year) {
    this.title = title;
    this.author = author;
    this.year = year;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Date getDateOfReceipt() {
    return dateOfReceipt;
  }

  public void setDateOfReceipt(Date dateOfReceipt) {
    this.dateOfReceipt = dateOfReceipt;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", year=" + year +
        ", dateOfReceipt=" + dateOfReceipt +
        '}';
  }
}
