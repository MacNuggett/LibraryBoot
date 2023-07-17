package com.example.Project2Boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personid", referencedColumnName = "id")
    private Person owner;

    @NotEmpty(message = "Название пустое")
    @Pattern(regexp = "[А-Я].+", message = "Неверный формат")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Автор не указан")
    @Pattern(regexp = "[А-Я]\\.[А-Я]\\. [А-Я][а-я]+", message = "Введите автора в следующем формате: \"И.О. Фамилия\"")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Введите год")
    @Max(value = 2023, message = "Неверная дата")
    @Min(value = 1, message = "Неверная дата")
    @Column(name = "year")
    private int year;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Transient
   private Boolean isOverDated;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {}

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getOverDated() {
        return isOverDated;
    }

    public void setOverDated(Boolean overDated) {
        isOverDated = overDated;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
