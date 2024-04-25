package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.iyanu.Book.Rental.entity.RentedBooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<RentedBooks> booksRented;

    private String message;
    private HttpStatus status;

    public CustomerDTO( HttpStatus status,String message) {
        this.status = status;
        this.message = message;

    }
}
