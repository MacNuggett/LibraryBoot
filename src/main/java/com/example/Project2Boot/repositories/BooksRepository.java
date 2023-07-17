package com.example.Project2Boot.repositories;

import com.example.Project2Boot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Book findByTitleStartingWith(String title);
}
