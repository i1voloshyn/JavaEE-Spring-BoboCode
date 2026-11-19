package dev.ivanvoloshyn.dto;

import java.time.LocalDate;

public record BookRequest(
        String title,
        LocalDate publishDate,
        short pageCount,
        String isbn
) {
}
