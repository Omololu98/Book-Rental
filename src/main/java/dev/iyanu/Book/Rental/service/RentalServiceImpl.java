package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.booksAPI.OpenLibraryApiService;
import dev.iyanu.Book.Rental.dto.APIResponse;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import dev.iyanu.Book.Rental.entity.Books;
import dev.iyanu.Book.Rental.entity.RentedBooks;
import dev.iyanu.Book.Rental.repository.BooksRepository;
import dev.iyanu.Book.Rental.repository.RentedBooksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService{

    private final BooksRepository booksRepository;
    private final RentedBooksRepository rentedBooksRepository;
    private final OpenLibraryApiService openLibraryApiService;

    @Override
    public ResponseEntity<APIResponse> rentBooks(BooksDTo booksDTo) {
        RentedBooks rentedBooks = rentedBooksRepository.findByTitle(booksDTo.getTitle().trim());
        if(Objects.nonNull(rentedBooks)){
            BooksDTo booksDTo1 = new BooksDTo();
            booksDTo1.setTitle(rentedBooks.getTitle());
            booksDTo1.setRentPrice(rentedBooks.getRentPrice());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new APIResponse(HttpStatus.FOUND,booksDTo1,"Proceed to checkout to pay $"+booksDTo1.getRentPrice()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(HttpStatus.NOT_FOUND,"We do not have this books available for rent at the moment"));

    }

    @Override
    @Cacheable(cacheNames = "cache1",key = "{#title, #author}")
    public APIResponse searchBooks(String title, String author) {
        if((Objects.isNull(title) || title.isEmpty())  & (Objects.isNull(author) || author.isEmpty())){
            return new APIResponse( HttpStatus.BAD_REQUEST,"A parameter to search by (either author or title or both) must be passed");
        }
        List<Books> books = new ArrayList<>();
        List<BooksDTo> booksDToList = new ArrayList<>();
        if(Objects.isNull(title)){
//            log.info("A");
             books = booksRepository.findBooksByAuthorNameContainingIgnoreCase(author.trim());
        } else if (Objects.isNull(author)) {
//            log.info("B");
            books = booksRepository.findBooksByTitleContainingIgnoreCase(title.trim());
        }
        else{
//            log.info("C");
            books = booksRepository.findBooksByAuthorNameContainingIgnoreCaseOrTitleContainingIgnoreCase(author,title);
        }
        for(Books book : books){
            BooksDTo booksDTo = new BooksDTo();
            booksDTo.setTitle(book.getTitle());
            booksDTo.setAuthor(book.getAuthorName());
            booksDTo.setRatings(book.getRatings());
            booksDToList.add(booksDTo);
        }
        if(booksDToList.isEmpty()){
            openLibraryApiService.loadDataFromAPI(title,author);
            return new APIResponse(HttpStatus.NOT_FOUND, "No book found!! Check back in an hour");
        }
        //this has to be done because you are turning a response entity

        return new APIResponse(HttpStatus.FOUND,booksDToList,"Provide book title to rent book");
    }
}
