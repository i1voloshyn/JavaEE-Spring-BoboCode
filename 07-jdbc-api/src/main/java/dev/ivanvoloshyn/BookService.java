package dev.ivanvoloshyn;

import dev.ivanvoloshyn.dto.BookRequest;
import dev.ivanvoloshyn.model.Book;
import dev.ivanvoloshyn.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    void addNewBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setPageCount(request.pageCount());
        book.setPublishDate(request.publishDate());
        book.setIsbn(request.isbn());

        repository.saveBook(book);
    }
}
