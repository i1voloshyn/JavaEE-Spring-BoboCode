package dev.ivanvoloshyn;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class PgDataSource {

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/library_test");
        dataSource.setUser("postgres");
        dataSource.setPassword("root");
        return dataSource;
    }
}
