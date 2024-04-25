package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.AdminDTO;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import dev.iyanu.Book.Rental.dto.PaymentDTO;
import dev.iyanu.Book.Rental.entity.Books;
import dev.iyanu.Book.Rental.entity.CompanyAccount;
import dev.iyanu.Book.Rental.entity.RentedBooks;
import dev.iyanu.Book.Rental.repository.BooksRepository;
import dev.iyanu.Book.Rental.repository.CompanyAccountRepository;
import dev.iyanu.Book.Rental.repository.RentedBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyAccountServiceImpl implements CompanyAccountService {

    private final CompanyAccountRepository companyAccountRepository;
    private final BooksRepository booksRepository;
    private final RentedBooksRepository rentedBooksRepository;

    @Override
    public ResponseEntity<AdminDTO> createAccount(AdminDTO adminDTO) {
        Optional<CompanyAccount> account = companyAccountRepository.findByAccountNumber(adminDTO.getAccountNumber());
        if(account.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AdminDTO(HttpStatus.BAD_REQUEST, "Account number already exist in our record"));
        }
        CompanyAccount companyAccount = new CompanyAccount();
        companyAccount.setAccountNumber(adminDTO.getAccountNumber());
        companyAccount.setCompanyName(adminDTO.getCompanyName());
        companyAccount.setBankName(adminDTO.getBankName());
        companyAccountRepository.save(companyAccount);
        adminDTO.setAccountId(companyAccount.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDTO);
    }

    @Override
    public ResponseEntity<AdminDTO> deleteAccount(int accountId) {
        Optional<CompanyAccount> account = companyAccountRepository.findById(accountId);
        if(account.isPresent()){
            companyAccountRepository.delete(account.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new AdminDTO(HttpStatus.ACCEPTED, "Account ID: " +accountId+" has been deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AdminDTO(HttpStatus.NOT_FOUND, "Account ID provided not found"));
    }

    @Override
    public ResponseEntity<BooksDTo> addBookToRentList(BooksDTo booksDTo) {
        List<Books> books = booksRepository.findBooksByTitle(booksDTo.getTitle());
        if(books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BooksDTo("We do not have this book title currently", HttpStatus.NOT_FOUND));
        }

        List<RentedBooks> rentedBooksList = new ArrayList<>();
        for(Books book : books){
            RentedBooks rentedBooks = new RentedBooks();
            rentedBooks.setRentPrice(booksDTo.getRentPrice());
            rentedBooks.setTitle(booksDTo.getTitle());
            rentedBooksList.add(rentedBooks);
        }
        rentedBooksRepository.saveAll(rentedBooksList);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new BooksDTo(booksDTo.getTitle()+" will now rent for $"+booksDTo.getRentPrice(),HttpStatus.ACCEPTED));
    }


}
