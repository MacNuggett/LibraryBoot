package com.example.Project2Boot.controllers;

import jakarta.validation.Valid;
import com.example.Project2Boot.models.Book;
import com.example.Project2Boot.models.Person;
import com.example.Project2Boot.services.BooksService;
import com.example.Project2Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear){
        if (sortByYear == null)
            sortByYear = false;

        model.addAttribute("books", booksService.findAll(page, itemsPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@ModelAttribute("person")Person person, @PathVariable("id") int id, Model model){
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("oneperson", book.getOwner());
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @DeleteMapping("/{id}/delete_person")
    public String deletePerson(@PathVariable("id") int id){
        booksService.deletePerson(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping
    public String setPerson(@ModelAttribute("person") Person person){

        return"redirect:/books";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String setPerson(@ModelAttribute("person") Person person, @PathVariable("id") int bookId){
        booksService.updatePerson(person.getId(), bookId);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("message") String message){
        if(message != null) {
            Book book = booksService.findByTitleStartingWith(message);
            model.addAttribute("book", book);
            if (book != null)
                model.addAttribute("owner", book.getOwner());
        }
        return "books/search";
    }
}
