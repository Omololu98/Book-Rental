package dev.iyanu.Book.Rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse implements Serializable {
    private HttpStatus status;
    private Object data;
    private String message;


    public APIResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
