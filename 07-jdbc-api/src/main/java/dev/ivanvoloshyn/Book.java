package dev.ivanvoloshyn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Book {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
}
