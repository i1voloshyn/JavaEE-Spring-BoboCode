package dev.ivanvoloshyn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
}
