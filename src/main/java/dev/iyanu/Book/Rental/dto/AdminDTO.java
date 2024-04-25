package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminDTO {
    private int accountId;
    private String accountNumber;
    private String companyName;
    private int accountBalance;
    private String bankName;
    private HttpStatus status;
    private String message;

    public AdminDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
