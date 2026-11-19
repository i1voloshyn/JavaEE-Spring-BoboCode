package dev.ivanvoloshyn.repository;

import dev.ivanvoloshyn.model.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private static final String INSERT_BOOK_SQL = """
            INSERT INTO books (title, publish_date,page_count, isbn)
            VALUES (?,?,?,?)
            """;

    private final DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveBook(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setDate(2, Date.valueOf(book.getPublishDate()));
            statement.setShort(3, book.getPageCount());
            statement.setString(4, book.getIsbn());
            statement.executeUpdate();
        } catch (SQLException e) {
            //todo
        }
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }
}
