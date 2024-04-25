package dev.iyanu.Book.Rental.service;

import dev.iyanu.Book.Rental.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerDTO> createCustomer(CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> updateCustomer(CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> deleteCustomer(Integer customerId);
}
