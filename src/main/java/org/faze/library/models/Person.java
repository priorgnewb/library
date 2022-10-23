package org.faze.library.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "full_name")
  @NotEmpty(message = "Имя не указано")
  @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
  private String fullName;

  @Column(name = "year_of_birth")
  @Min(value = 1900, message = "Год рождения должен быть не менее 1900")
  private int yearOfBirth;

  @OneToMany(mappedBy = "owner")
  private List<Book> books;

  public Person() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public Person(String fullName, int yearOfBirth) {
    this.fullName = fullName;
    this.yearOfBirth = yearOfBirth;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", fullName='" + fullName + '\'' +
        ", yearOfBirth=" + yearOfBirth +
        '}';
  }
}
