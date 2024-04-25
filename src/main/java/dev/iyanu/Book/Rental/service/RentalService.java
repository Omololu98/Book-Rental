package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.APIResponse;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentalService {
    APIResponse searchBooks(String title, String author);

    ResponseEntity<APIResponse> rentBooks(BooksDTo booksDTo);
}
