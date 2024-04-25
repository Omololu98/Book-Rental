package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.AdminDTO;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import org.springframework.http.ResponseEntity;

public interface CompanyAccountService {
    ResponseEntity<AdminDTO> createAccount(AdminDTO adminDTO);

    ResponseEntity<AdminDTO> deleteAccount(int accountId);

    ResponseEntity<BooksDTo> addBookToRentList(BooksDTo booksDTo);
}
