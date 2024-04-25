package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.iyanu.Book.Rental.entity.Books;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenLibraryAPIResponse {
    private int numFound;
    private int start;
    private boolean numFoundExact;
    private List<BooksDTo> docs;
}
