package dev.ivanvoloshyn;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = PgDataSource.createDataSource();
        BookService bookService = new BookService(dataSource);
        bookService.someNotRealWorldExample();

        bookService.findAll()
                .forEach(System.out::println);
    }
}
