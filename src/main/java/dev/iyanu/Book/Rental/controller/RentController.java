package dev.iyanu.Book.Rental.controller;

import dev.iyanu.Book.Rental.dto.APIResponse;
import dev.iyanu.Book.Rental.dto.BooksDTo;
import dev.iyanu.Book.Rental.dto.PaymentDTO;
import dev.iyanu.Book.Rental.service.PayStackPaymentService;
import dev.iyanu.Book.Rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RequestMapping("/api/v1/book-rental")
@RestController
public class RentController {

    private final RentalService rentalService;
    private final PayStackPaymentService paymentService;

    @GetMapping("/search")
    public APIResponse searchBooks(@RequestParam(required = false,value = "title") String title,
                                                     @RequestParam(required = false, value = "author") String author){
        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
                .cachePrivate().mustRevalidate();
        APIResponse response = rentalService.searchBooks(title,author);
        return ResponseEntity.status(response.getStatus())
                .cacheControl(cacheControl).body(response).getBody();
    }
    @PostMapping("/rent")
    public ResponseEntity<APIResponse> rentBook(@RequestBody BooksDTo booksDTo){
        return rentalService.rentBooks(booksDTo);
    }

    @PostMapping("/make_payment")
    public ResponseEntity<APIResponse> payForBook(@RequestBody PaymentDTO paymentDTO){
        return paymentService.initialiseRentPayment(paymentDTO);
    }

    @GetMapping("/successful_transaction")
    public String transactionSuccessful(){
        return "You have successfully paid";
    }

    @GetMapping("/verify_payment/{reference}")
    public ResponseEntity<APIResponse> verifyPayment(@PathVariable("reference") String referenceCode){
        return paymentService.verifyPayment(referenceCode);
    }


}
