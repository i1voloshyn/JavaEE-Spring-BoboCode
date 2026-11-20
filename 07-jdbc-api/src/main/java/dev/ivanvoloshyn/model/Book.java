package dev.ivanvoloshyn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class Book {
    private int id;
    private String title;
    private LocalDate publishDate;
    private short pageCount;
    private String isbn;
}
