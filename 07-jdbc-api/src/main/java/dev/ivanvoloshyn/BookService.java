package dev.ivanvoloshyn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService implements BookRepository {
    private static final String FIND_ALL_BOOKS_SQL = """
            SELECT id,title,author,publication_year,isbn FROM books
            """;

    private final DataSource dataSource;

    public BookService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {

        try (Connection connection = dataSource.getConnection()) {
            connection.setReadOnly(true);
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS_SQL);
            ResultSet rs = statement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                int publicationYear = rs.getInt(4);
                String isbn = rs.getString(5);
                Book book = new Book(id, title, author, publicationYear, isbn);
                books.add(book);
            }
            return books;

        } catch (SQLException e) {
            //todo add logger
        }
        return List.of();
    }
}
