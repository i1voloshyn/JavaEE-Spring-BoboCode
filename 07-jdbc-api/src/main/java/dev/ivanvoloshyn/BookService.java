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

    private static final String INSERT_BOOK_SQL = """
            INSERT INTO books (title, author, publication_year, isbn, publisher_id)
            VALUES (?,?,?,?,?)
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
            System.out.println("Exception occurred: " + e);
        }
        return List.of();
    }

    @Override
    public void saveDefaultBook() {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL);
            statement.setString(1, "Java Spring");
            statement.setString(2, "Some Author");
            statement.setInt(3, 2026);
            statement.setString(4, "correct-isbn");
            statement.setInt(5, 5);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception occurred: " + e);
        }
    }

    private void saveDefaultBook1(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL)) {
            statement.setString(1, "Java Spring 1");
            statement.setString(2, "Some Author 1");
            statement.setInt(3, 2026);
            statement.setString(4, "correct-isbn 9");
            statement.setInt(5, 5);
            statement.executeUpdate();
        }
    }

    private void saveDefaultBook2(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL)) {
            // THIS IS BAD->  
            connection.setAutoCommit(true);
            // With autoCommit = true, the INSERT is automatically committed before your manually thrown SQLException executes, assuming executeUpdate() succeeds.
            // JDBC automatically commits the SQL statement.
            /*
              According to JDBC transaction semantics, changing auto-commit mode from false to true commits the current transaction.
              Therefore
              saveDefaultBook1() executes its INSERT without committing.
              saveDefaultBook2() calls setAutoCommit(true), committing Book 1.
              saveDefaultBook2() executes its INSERT, automatically committing Book 2.
              The manually thrown exception cannot roll back either committed INSERT.
             */

            statement.setString(1, "Java Spring 2");
            statement.setString(2, "Some Author 2");
            statement.setInt(3, 2024);
            statement.setString(4, "correct-isbn 10");
            statement.setInt(5, 6);

            statement.executeUpdate();     // INSERT + automatic COMMIT

            //Exception is thrown after the INSERT has committed. It cannot undo that committed change.
            throw new SQLException();      // Exception after COMMIT
        }
    }

    public void someNotRealWorldExample() {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setAutoCommit(false);       // The main method which should control transactions 

                saveDefaultBook1(connection);
                saveDefaultBook2(connection);

                connection.commit();
            } catch (SQLException e) {
                System.out.println("Rollback because of exception: " + e);
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Exception in realWorldMethod: " + e);
        }
    }

}
