package dev.iyanu.Book.Rental.controller;

import dev.iyanu.Book.Rental.dto.CustomerDTO;
import dev.iyanu.Book.Rental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book-rental")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create_customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createCustomer(customerDTO);
    }
    @PatchMapping("/update_customer")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/delete_customer")
    public ResponseEntity<CustomerDTO> deleteCustomer(@RequestParam(value = "customerId") Integer customerId){
        return customerService.deleteCustomer(customerId);
    }


}
