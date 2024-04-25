package dev.iyanu.Book.Rental.controller;

import dev.iyanu.Book.Rental.dto.AdminDTO;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import dev.iyanu.Book.Rental.service.CompanyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book-rental")
@RequiredArgsConstructor
public class AdminController {

    private final CompanyAccountService companyAccountService;

    @PostMapping("/create_company_account")
    public ResponseEntity<AdminDTO> createAccountNumber(@RequestBody AdminDTO adminDTO){
     return companyAccountService.createAccount(adminDTO);
    }

    @DeleteMapping("/delete_company_account")
    public ResponseEntity<AdminDTO> deleteAccountNumber(@RequestParam(value = "accountId") int accountId){
        return companyAccountService.deleteAccount(accountId);
    }

    @PostMapping("/add_books_to_rent")
    public ResponseEntity<BooksDTo> addBookToRent(@RequestBody BooksDTo booksDTo){
        return companyAccountService.addBookToRentList(booksDTo);
    }
}
