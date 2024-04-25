package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BooksDTo implements Serializable {
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "author_name")
    private List<String> author;
    @JsonProperty(value = "ratings_average")
    private float ratings;
    @JsonProperty(value = "number_of_pages_median")
    private int pages;
    @JsonProperty(value = "language")
    private List<String> availableLanguages;
    private float rentPrice;
    private String message;
    private HttpStatus status;

    public BooksDTo(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
