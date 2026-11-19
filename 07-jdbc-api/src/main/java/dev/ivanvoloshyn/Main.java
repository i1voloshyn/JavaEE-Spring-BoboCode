package dev.ivanvoloshyn;

import dev.ivanvoloshyn.dto.BookRequest;
import dev.ivanvoloshyn.repository.BookRepository;
import dev.ivanvoloshyn.repository.BookRepositoryImpl;

import javax.sql.DataSource;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = PgDataSource.createDataSource();
        BookRepository repository = new BookRepositoryImpl(dataSource);
        BookService service = new BookService(repository);

        BookRequest request = new BookRequest(
                "Learning SQL",
                LocalDate.of(2020, 10, 21),
                (short) 345,
                "978-1492057611"
        );

        service.addNewBook(request);
    }
}
