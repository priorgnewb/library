package org.faze.library.controllers;

import javax.validation.Valid;
import org.faze.library.models.Book;
import org.faze.library.models.Person;
import org.faze.library.services.BooksService;
import org.faze.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BooksController {

  private final BooksService booksService;
  private final PeopleService peopleService;

  @Autowired
  public BooksController(BooksService booksService,
      PeopleService peopleService) {
    this.booksService = booksService;
    this.peopleService = peopleService;
  }

  @GetMapping()
  public String index(Model model,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
      @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

    if (page == null || booksPerPage == null) {
      model.addAttribute("books", booksService.findAll(sortByYear));
    } else {
      model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));
    }

    return "books/index";
  }


  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model,
      @ModelAttribute("person") Person person) {

    model.addAttribute("book", booksService.findOne(id));
    Person bookOwner = booksService.getBookOwner(id);

    if (bookOwner != null) {
      model.addAttribute("owner", bookOwner);
    } else {
      model.addAttribute("people", peopleService.findAll());
    }

    return "books/show";
  }

  @GetMapping("/new")
  public String newBook(@ModelAttribute("book") Book book) {
    return "books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "books/new";
    }

    booksService.save(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") int id) {
    model.addAttribute("book", booksService.findOne(id));
    return "books/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
      @PathVariable("id") int id) {
    if (bindingResult.hasErrors()) {
      return "books/edit";
    }

    booksService.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    booksService.delete(id);
    return "redirect:/books";
  }

  // метод, освобождающий книгу от читателя, книгу сможет взять другой читатель
  @PatchMapping("/{id}/release")
  public String release(@PathVariable("id") int id) {
    booksService.release(id);
    return "redirect:/books/" + id;
  }

  // метод, выписывающий книгу на конкретного читателя, книга станет занята
  @PatchMapping("/{id}/assign")
  public String assign(@PathVariable("id") int id,
      @ModelAttribute("person") Person selectedPerson) {
    booksService.assign(id, selectedPerson);
    return "redirect:/books/" + id;
  }

  @GetMapping("/search")
  public String searchPage() {
    return "books/search";
  }

  @PostMapping("/search")
  public String makeSearch(Model model, @RequestParam("query") String query) {
    model.addAttribute("books", booksService.searchByTitle(query));
    return "books/search";
  }
}


