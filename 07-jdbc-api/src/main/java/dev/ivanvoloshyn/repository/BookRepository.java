package dev.ivanvoloshyn.repository;

import dev.ivanvoloshyn.model.Book;

import java.util.List;

public interface BookRepository {
    void saveBook(Book book);

    List<Book> findAll();
}
