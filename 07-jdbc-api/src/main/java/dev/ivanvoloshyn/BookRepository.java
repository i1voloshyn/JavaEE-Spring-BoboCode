package dev.ivanvoloshyn;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
}
