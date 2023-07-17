package com.example.Project2Boot.services;

import com.example.Project2Boot.models.Book;
import com.example.Project2Boot.models.Person;
import com.example.Project2Boot.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll(Integer page, Integer itemsPerPage, Boolean sort){
        if(sort){
            if (page == null & itemsPerPage == null)
                return booksRepository.findAll(Sort.by("year"));
            else if (page != null){
                if (itemsPerPage == null)
                    itemsPerPage = 4;
                return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
            }
        }else if (page != null){
            if (itemsPerPage == null)
                itemsPerPage = 4;
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        }
        return booksRepository.findAll();
    }

//    public List<Book> findAll(String sort){
//        System.out.println("Inside findall, sort="+sort);
//        if (sort == "true")
//            return booksRepository.findAll(Sort.by("year"));
//        return booksRepository.findAll();
//    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        if (foundBook.isPresent()){
            Hibernate.initialize(foundBook.get().getOwner());
            return foundBook.get();
        }
        else return null;
    }

    public Book findByTitleStartingWith(String message){
        Book book = booksRepository.findByTitleStartingWith(message);
        if (book != null)
            Hibernate.initialize(book.getOwner());
        return book;
    }

    public List<Book> getBooks(int personId){
        Person owner = peopleService.findOne(personId);
        List<Book> books = owner.getBooks();
        for(Book book: books){
            book.setOverDated(book.getCreatedAt().isBefore(LocalDate.now().minusDays(10)));
            System.out.println(book.getOverDated());
        }
        return books;
    }

    public void save(Book book){
        booksRepository.save(book);
    }

    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    public void updatePerson(int personId, int bookId){
        Book book = findOne(bookId);
        book.setOwner(peopleService.findOne(personId));
        book.setCreatedAt(LocalDate.now());
        booksRepository.save(book);
    }

    public void deletePerson(int bookId){
        Book book = findOne(bookId);
        book.setOwner(null);
        booksRepository.save(book);
    }

    public void delete(int id){
        booksRepository.deleteById(id);
    }
}
