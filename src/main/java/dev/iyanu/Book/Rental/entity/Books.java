package dev.iyanu.Book.Rental.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "BooksList")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Books {
    @Id
    private  String bookId;
    @Indexed(unique = true)
    private String title;
    private List<String> authorName;
    private List<String> availableLanguages;
    private float ratings;
    private int pages;
}
